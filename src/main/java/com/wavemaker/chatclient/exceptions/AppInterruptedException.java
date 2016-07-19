package com.wavemaker.chatclient.exceptions;

/**
 * Created by sainihala on 13/7/16.
 */
public class AppInterruptedException extends RuntimeException {

    public AppInterruptedException(String interruptedException, Throwable cause){
        super(interruptedException,cause);
    }

}

