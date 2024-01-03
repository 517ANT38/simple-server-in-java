package com.simpleserver.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class EchoFileClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @SneakyThrows
    public void startConnection(String ip, int port) {
        
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        

    }

    @SneakyThrows
    public void getFile(String serverFile,String outFile){
        out.println("GET " + serverFile + " HTTP/1.1");
        while(in.readLine().equals("\n"));        
        try(var inF = new DataInputStream(clientSocket.getInputStream())){
            try(FileOutputStream o = new FileOutputStream(outFile)){
                byte[] bytes = new byte[5*1024];
                int count;                    
                while ((count = inF.read(bytes)) > -1) {
                    o.write(bytes, 0, count);
                }
            }
        }
    }


    @SneakyThrows
    public void stopConnection() {
        
        in.close();
        out.close();
        clientSocket.close();
        

    }
}
