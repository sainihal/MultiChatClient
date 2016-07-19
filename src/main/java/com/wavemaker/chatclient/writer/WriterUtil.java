package com.wavemaker.chatclient.writer;

import com.wavemaker.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatclient.inpututil.InputReader;
import com.wavemaker.chatclient.model.Context;
import com.wavemaker.utils.messages.ChatMessage;
import com.wavemaker.utils.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 14/7/16.
 */
public class WriterUtil {

    private static final Logger logger = Logger.getLogger(WriterUtil.class.getName());
    public static void writeToServer(Message message, ObjectOutputStream oos)
            throws IOException {
        oos.writeObject(message);
        logger.log(Level.INFO, "Client:  data written...");
    }

    public static Message readInput(String clientName, Context context, InputReader inputReader)
            throws IOException, ClientClosedException {
        Message message;

        message = inputReader.getMessage(clientName, context);

        if (message.getType() == Message.MessageType.QUIT) {
            context.setClosed(true);
            logger.log(Level.INFO, "closing the client");
            return message;
        } else {
            logger.log(Level.INFO, "destination is " + ((ChatMessage) message).getDestination());
            logger.log(Level.INFO, "sender is " + clientName);
        }
        return message;
    }
}
