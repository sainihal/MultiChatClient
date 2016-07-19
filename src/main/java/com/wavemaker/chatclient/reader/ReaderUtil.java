package com.wavemaker.chatclient.reader;

import com.wavemaker.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatclient.model.Context;
import com.wavemaker.utils.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 14/7/16.
 */
public class ReaderUtil
{
    private static final Logger logger = Logger.getLogger(ReaderUtil.class.getName());

    public static void readFromServer(Socket socket, Context context, ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException,ClientClosedException {
        Message readObject;

        while (true) {
            if (socket.getInputStream().available() != 0)
                break;
            if (context.isClosed())
                throw new ClientClosedException("client closed");
        }

        readObject = (Message) (objectInputStream.readObject());
        logger.log(Level.INFO, "Client:   data read in client is........"+readObject.toString());
        if (readObject.getType() == Message.MessageType.SERVER_EXITING)
            context.setClosed(true);
    }
}
