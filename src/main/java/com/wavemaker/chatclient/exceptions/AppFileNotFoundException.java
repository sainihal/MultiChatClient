package com.wavemaker.chatclient.exceptions;

/**
 * Created by sainihala on 15/7/16.
 */
public class AppFileNotFoundException extends RuntimeException {
    public AppFileNotFoundException(String fileNotFound){
        super(fileNotFound);
    }
}
