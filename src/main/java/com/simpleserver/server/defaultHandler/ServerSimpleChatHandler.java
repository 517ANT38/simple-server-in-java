package com.simpleserver.server.defaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class ServerSimpleChatHandler implements ServerHandler {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    


    @Override
    public void run() {
        
        String inputLine;
        if(socket == null)
            throw new RuntimeException("Not bind socket");
        try {
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                out.println(inputLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            shutdown();
        }
            
    }





    @Override
    @SneakyThrows
    public void bindSocket(Socket socket) {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }





    @Override
    @SneakyThrows
    public void shutdown() {
        out.close();
        in.close();
        socket.close();
    }
    
}
