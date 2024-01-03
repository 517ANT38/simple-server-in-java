package com.simpleserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class ServerSomthing implements Runnable {

    private Socket socket; 
    

    public ServerSomthing(Socket socket) throws IOException {
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

            } catch (IOException e) {
                System.out.println(e);
            }        
        } catch (IOException e) {
             System.out.println(e);
        }
        socket.close();
    }
    
}
