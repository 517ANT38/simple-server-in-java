package com.simpleserver.client;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class EchoFileClient {
    private Socket clientSocket;
    private PrintWriter out;
    private DataInputStream in;

    @SneakyThrows
    public void startConnection(String ip, int port) {
        
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new DataInputStream(clientSocket.getInputStream());
        

    }

    @SneakyThrows
    public void getFile(String serverFile,String outFile){
        out.println("GET " + serverFile + "HTTP/1.1");
        try(FileOutputStream o = new FileOutputStream(outFile)){
            in.transferTo(o);
        }
    }


    @SneakyThrows
    public void stopConnection() {
        
        in.close();
        out.close();
        clientSocket.close();
        

    }
}
