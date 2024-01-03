package com.simpleserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;


public class SimpleServer implements Runnable {
    
    private final ServerSocket serverSocket;
    private final ExecutorService ex;
    private boolean stop = false;


    private SimpleServer(int port) throws IOException {
        
        this.serverSocket = new ServerSocket(port);
        var nt = Runtime.getRuntime().availableProcessors() - 1;
        this.ex = Executors.newFixedThreadPool(nt);
    }

    public static SimpleServer create(int port) throws IOException {
        return new SimpleServer(port);
    }

   
    private void start() throws IOException {
        try {
            while (!stop) {
                var socket = this.serverSocket.accept();
                ex.execute(new ServerSomthing(socket));
            }
        } finally {
            serverSocket.close();
        }   

    }

    @Override
    @SneakyThrows
    public void run() {
        start();
    }

    @SneakyThrows
    public void stop(){
        
        ex.shutdown();
        stop = true;
        serverSocket.close();

        
    }

}
