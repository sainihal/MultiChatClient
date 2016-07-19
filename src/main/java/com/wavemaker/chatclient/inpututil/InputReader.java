package com.wavemaker.chatclient.inpututil;

import com.wavemaker.chatclient.exceptions.ClientClosedException;
import com.wavemaker.chatclient.model.Context;
import com.wavemaker.utils.messages.ChatMessage;
import com.wavemaker.utils.messages.Message;
import com.wavemaker.utils.messages.QuitMessage;
import com.wavemaker.utils.properties.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 14/7/16.
 */
public class InputReader {
    private  static Logger logger = Logger.getLogger(InputReader.class.getName());
    private BufferedReader br;

    public InputReader(){
        br = new InputStream().getStandardReader();
    }
    public InputReader(String fileName){
        br = new InputStream().getFileReader(fileName);
    }

    public  Message getMessage(String sender, Context context)
            throws IOException, ClientClosedException {

        String destination;
        String data;

        logger.log(Level.INFO,"enter destination/ enter "+Constants.EXIT_KEY+" to exit");
        while(!br.ready()) {
            if (context.isClosed()) {
                throw new ClientClosedException("client closed");
            }
        }
        destination = br.readLine();
        if (destination.equals(Constants.EXIT_KEY)) {
            return new QuitMessage(sender);
        }
        logger.log(Level.INFO,"enter data");
        while(!br.ready()) {
            if (context.isClosed()) {
                throw new ClientClosedException("client closed");
            }
        }
        data = br.readLine();

        return new ChatMessage(sender, destination, data);
    }
    public  void close(){
        try {
            br.close();
            logger.log(Level.INFO,"closed input reader");
        }catch (IOException ioe)
        {
            logger.log(Level.INFO,"exception in  closing input reader");
        }
    }
}