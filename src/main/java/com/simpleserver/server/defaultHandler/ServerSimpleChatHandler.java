package com.simpleserver.server.defaultHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class ServerSimpleChatHandler implements Runnable {

    private Socket socket;
    

    public ServerSimpleChatHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    @SneakyThrows
    public void run() {
        try (var out = new PrintWriter(socket.getOutputStream(),true)) {
            try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);
                }
            } 
            socket.close();
        }
    }
    
}
