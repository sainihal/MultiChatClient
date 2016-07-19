package com.wavemaker.chatclient.writer;


import com.wavemaker.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatclient.exceptions.FailedToWriteException;
import com.wavemaker.chatclient.inpututil.InputReader;
import com.wavemaker.chatclient.model.Context;
import com.wavemaker.utils.messages.Message;
import com.wavemaker.utils.messages.QuitMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 27/6/16.
 */
public class ServerWriterWorker implements Runnable {

    private String clientName;
    private ObjectOutputStream oos;
    private Context context;
    private static final Logger logger = Logger.getLogger(ServerWriterWorker.class.getName());
    private InputReader inputReader;
    private boolean quitted;


    public ServerWriterWorker(String clientName, ObjectOutputStream oos, Context close, InputReader inputReader) {
        this.clientName = clientName;
        this.oos = oos;
        this.context = close;
        this.inputReader = inputReader;
    }

    public void run() {
        registerShutdownHook();
        Message message;
        try {
            while (!context.isClosed()) {
                message = WriterUtil.readInput(clientName, context, inputReader);
                if(message.getType() == Message.MessageType.QUIT)
                    quitted = true;
                WriterUtil.writeToServer(message, oos);
            }
        } catch (ClientClosedException clientClosed) {
            logger.log(Level.INFO, "Client Closed ");
        } catch (IOException e) {
            throw new FailedToWriteException("Failed to write to server", e);
        } finally {
            context.setClosed(true);
        }
        logger.log(Level.INFO, "closing client writer.....");
    }

    private void registerShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                context.setClosed(true);
                if(!quitted)
                    try {
                        logger.log(Level.INFO,"quitting client");
                        WriterUtil.writeToServer(new QuitMessage(clientName), oos);
                        inputReader.close();
                    }catch (IOException ioe){
                        logger.log(Level.SEVERE,"quit message not sent",ioe);
                    }
            }
        });
    }
}

