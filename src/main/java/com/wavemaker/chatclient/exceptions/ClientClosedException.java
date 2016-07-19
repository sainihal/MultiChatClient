package com.wavemaker.chatclient.exceptions;

/**
 * Created by sainihala on 14/7/16.
 */
public class ClientClosedException extends Exception {
    public ClientClosedException(String clientClosed) {
        super(clientClosed);
    }
}
