package com.wavemaker.chatclient.client;

/**
 * Created by sainihala on 23/6/16.
 */


import com.wavemaker.chatclient.exceptions.AppInterruptedException;
import com.wavemaker.chatclient.exceptions.RegistrataionFailed;
import com.wavemaker.chatclient.inpututil.InputReader;
import com.wavemaker.chatclient.model.Context;
import com.wavemaker.chatclient.reader.ServerReaderWorker;
import com.wavemaker.chatclient.writer.ServerWriterWorker;
import com.wavemaker.utils.exceptions.AppClassNotFoundException;
import com.wavemaker.utils.exceptions.AppIOException;
import com.wavemaker.utils.messages.Message;
import com.wavemaker.utils.messages.RegisterMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatClient {
    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String name;
    private Context close = new Context();
    private InputReader inputReader;

    public ChatClient(String host, int port, String name) {
        try {
            socket = new Socket(host, port);
        }catch (IOException ioe) {
            throw new AppIOException("In creating socket",ioe);
        }
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }catch (IOException ioe){
            closeStreams();
            throw new AppIOException(" In initializing streams",ioe);
        }
        this.name = name;
        registerClient();
    }

    public void start() {
        inputReader = (inputReader == null) ? new InputReader() : inputReader;
        Thread serverWriterWorker = new Thread(new ServerWriterWorker(name, objectOutputStream, close, inputReader));
        Thread serverReaderWorker = new Thread(new ServerReaderWorker(socket, objectInputStream, close));
        serverReaderWorker.start();
        serverWriterWorker.start();
        try {
            serverWriterWorker.join();
            serverReaderWorker.join();
        } catch (InterruptedException interruptedException) {
            throw new AppInterruptedException("In client",interruptedException);
        }finally {
            closeStreams();
        }
    }

    public void start(String fileName){
        this.inputReader = new InputReader(fileName);
        start();
    }

    private void registerClient() {
        RegisterMessage registerMessage;
        Message acknowledgementMessage;

        try {
            logger.log(Level.INFO,"Client Registering...");
            registerMessage = new RegisterMessage(name);
            objectOutputStream.writeObject(registerMessage);
            acknowledgementMessage = (Message) objectInputStream.readObject();
            if (acknowledgementMessage.getType() == Message.MessageType.REGISTRATION_SUCCESS) {
                logger.log(Level.INFO,"Registration Success "+acknowledgementMessage.toString());
            } else if (acknowledgementMessage.getType() == Message.MessageType.REGISTRATION_FAILED) {
                logger.log(Level.INFO,"Registration Failed "+acknowledgementMessage.toString());
                closeStreams();
                throw new RegistrataionFailed(acknowledgementMessage.toString());
            }
        } catch (IOException ioe) {
            closeStreams();
            throw new AppIOException("In client init ", ioe);
        } catch (ClassNotFoundException classNotFound) {
            closeStreams();
            throw new AppClassNotFoundException("In  client init", classNotFound);
        }
    }

    private void closeStreams() {
        logger.log(Level.INFO, "Closing Object Streams..");
        try {
            if(objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "In closing objectInputStream", e);
        }
        try {
            if(objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "In closing objectOutputStream ", e);

        }
    }
}
