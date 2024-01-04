package com.simpleserver;

import static org.junit.Assert.assertTrue;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import javax.imageio.ImageIO;

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

        assertTrue(compareImage(new File(DIR_PATH + serverFile),new File(checkFile)) >= 0.5);

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

    private float compareImage(File fileA, File fileB) {

        float percentage = 0;
        try {
            // take buffer data from both image files //
            BufferedImage biA = ImageIO.read(fileA);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(fileB);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            int count = 0;
            // compare data-buffer objects //
            if (Math.abs(sizeA - sizeB) < 50) {
                var n = Math.min(sizeA, sizeB);
                for (int i = 0; i < n; i++) {
    
                    if (dbA.getElem(i) == dbB.getElem(i)) {
                        count = count + 1;
                    }
    
                }
                percentage = (count * 100) / n;
            } else {
                System.out.println("Both the images are not of same size");
            }
    
        } catch (Exception e) {
            System.out.println("Failed to compare image files ...");
        }
        return percentage;
    }
}
