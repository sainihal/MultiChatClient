package com.wavemaker.chatclient.inpututil;

import com.wavemaker.chatclient.exceptions.AppFileNotFoundException;

import java.io.*;

/**
 * Created by sainihala on 14/7/16.
 */
public class InputStream {


    public  BufferedReader getStandardReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public BufferedReader getFileReader(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException fileNotFound) {
            throw new AppFileNotFoundException("File NotFound");
        }
        return br;
    }
}