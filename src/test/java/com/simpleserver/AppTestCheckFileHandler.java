package com.simpleserver;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import com.simpleserver.client.EchoFileClient;

import lombok.SneakyThrows;

public class AppTestCheckFileHandler {
    
    private final static String DIR_PATH = "webapps";

    @Test
    public void givenClient2_responceFileTextCorrect(){
        EchoFileClient eFileClient = new EchoFileClient();
        eFileClient.startConnection("localhost",8989);

        var serverFile = "/pages/index.html";
        var checkFile = DIR_PATH + "/pages/test.html";
        
        eFileClient.getFile(serverFile,checkFile);

        assertTrue(checkFiles(DIR_PATH + serverFile,checkFile));

    }

    @Test
    public void givenClient2_responceFileImgCorrect(){
        EchoFileClient eFileClient = new EchoFileClient();
        eFileClient.startConnection("localhost",8989);

        var serverFile = "/images/ddd.jpg";
        var checkFile = DIR_PATH + "/images/test.jpg";
        
        eFileClient.getFile(serverFile,checkFile);

        assertTrue(checkFiles(DIR_PATH + serverFile,checkFile));

    }

    @After
    public void clearFiles(){
        new File(DIR_PATH + "/images/test.jpg").delete();
        new File(DIR_PATH + "/pages/test.html").delete();
    }

    @SneakyThrows
    private boolean checkFiles(String path1, String path2) {
        
        Reader reader1 = new BufferedReader(new FileReader(path1));
        Reader reader2 = new BufferedReader(new FileReader(path2));        
        
        return IOUtils.contentEqualsIgnoreEOL(reader1,reader2);
    } 
}
