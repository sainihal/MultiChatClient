package com.wavemaker.chatclient.exceptions;

/**
 * Created by sainihala on 5/7/16.
 */
public class FailedToReadException extends RuntimeException {
    public FailedToReadException(String failedToRead, Exception e) {
        super(failedToRead, e);
    }
}
