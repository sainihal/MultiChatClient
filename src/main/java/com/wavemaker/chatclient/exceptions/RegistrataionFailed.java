package com.wavemaker.chatclient.exceptions;

/**
 * Created by sainihala on 13/7/16.
 */
public class RegistrataionFailed extends RuntimeException {

    public RegistrataionFailed(String invalidRequest){
        super(invalidRequest);
    }

}

