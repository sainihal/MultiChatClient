package com.wavemaker.chatclient.client;

import com.wavemaker.utils.exceptions.AppIOException;
import com.wavemaker.utils.properties.PropertyLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sainihala on 23/6/16.
 */
public class ClientApp {
    private static final String DEFAULT_HOST = "localhost";
    private static final Logger logger = Logger.getLogger(ClientApp.class.getName());


    public static void main(String args[]) {

         String hostName  = DEFAULT_HOST;
         int port = PropertyLoader.getPort();
         String clientName;
         BufferedReader br;

        if(args.length > 0)
        {
            clientName = args[0];
        }
        else{
            logger.log(Level.INFO,"enter the name of client");
            try {
                br = new BufferedReader(new InputStreamReader(System.in));
                clientName = br.readLine();
            } catch (IOException e) {
                throw new AppIOException("In reading client name", e);
            }
        }
        if(args.length > 1){
            port = Integer.parseInt(args[1]);
        }
        else{
            port = PropertyLoader.getPort();
        }
        if(args.length > 2){
            hostName = args[3];
        }
/*


        Runnable runnable = new Runnable() {
            public void run() {
                ChatClient chatClient =  new ChatClient(DEFAULT_HOST , PropertyLoader.getPort() , "c1");
                chatClient.start("temp");
            }
        };
        Thread t = new Thread(runnable);
        t.start();

        Runnable runnable1 = new Runnable() {
            public void run() {
                ChatClient chatClient1 = new ChatClient(DEFAULT_HOST,PropertyLoader.getPort(),"c2");
                chatClient1.start();;
            }
        };
        Thread t1 = new Thread(runnable1);
        t1.start();

        Runnable runnable2 = new Runnable() {
            public void run() {
                ChatClient chatClient2 = new ChatClient(DEFAULT_HOST,PropertyLoader.getPort(),"c3");
                chatClient2.start("c3");
            }
        };
        Thread t2 = new Thread(runnable2);
        t2.start();*/

        ChatClient chatClient = new ChatClient(hostName,port,clientName);
        chatClient.start();
    }
}

