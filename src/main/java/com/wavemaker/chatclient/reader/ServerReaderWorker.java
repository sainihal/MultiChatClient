package com.wavemaker.chatclient.reader;


import com.wavemaker.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatclient.exceptions.FailedToReadException;
import com.wavemaker.chatclient.model.Context;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 27/6/16.
 */
public class ServerReaderWorker implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerReaderWorker.class.getName());
    private ObjectInputStream objectInputStream;
    private Socket socket;
    private Context context;

    public ServerReaderWorker(Socket socket, ObjectInputStream ois, Context close) {
        this.objectInputStream = ois;
        this.socket = socket;
        this.context = close;
    }

    public void run() {
        try {
            while (!context.isClosed()) {
                ReaderUtil.readFromServer(socket, context, objectInputStream);
            }
        }catch (ClientClosedException clientClosed){
            logger.log(Level.INFO,"Client closed");
        }
        catch (Exception e) {
            throw new FailedToReadException("failed to read from server", e);
        }finally {
            context.setClosed(true);
        }
        logger.log(Level.INFO, "closing client reader....");
    }
}



