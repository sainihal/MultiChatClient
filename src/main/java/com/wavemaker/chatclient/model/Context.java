package com.wavemaker.chatclient.model;

/**
 * Created by sainihala on 12/7/16.
 */
public class Context {
    private boolean close;

    public boolean isClosed() {
        return close;
    }

    public void setClosed(boolean close) {
        this.close = close;
    }
}
