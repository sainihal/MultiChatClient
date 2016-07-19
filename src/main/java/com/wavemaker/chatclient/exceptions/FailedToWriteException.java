package com.wavemaker.chatclient.exceptions;

/**
 * Created by sainihala on 5/7/16.
 */
public class FailedToWriteException extends RuntimeException {
    public FailedToWriteException(String failedToWrite, Exception e) {
        super(failedToWrite, e);
    }
}
