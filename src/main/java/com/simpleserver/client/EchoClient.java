package com.simpleserver.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class EchoClient {
    
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @SneakyThrows
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } finally {
            stopConnection();
        }

    }

    @SneakyThrows
    public String sendMessage(String msg) {
        
        out.println(msg);
        return in.readLine();
        
    }

    @SneakyThrows
    public void stopConnection() {
        
        in.close();
        out.close();
        clientSocket.close();
        

    }
}
