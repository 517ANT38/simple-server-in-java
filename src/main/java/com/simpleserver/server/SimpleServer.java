package com.simpleserver.server;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.simpleserver.server.builder.BuilderServer;

import lombok.SneakyThrows;


public class SimpleServer implements Server {
    
    private final ServerSocket serverSocket;
    private final ExecutorService ex;
    private final Class<? extends Runnable> clazz;
    private boolean stop = false;


    @SneakyThrows
    private SimpleServer(String host,int port,Class<? extends Runnable> clazz) {
        
        this.serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(host, port));
        var nt = Runtime.getRuntime().availableProcessors() - 1;
        this.ex = Executors.newFixedThreadPool(nt);
        this.clazz = clazz;
        
    }

    public static BuilderServer builder(){
        return new BuilderServerIml();
    }

    @SneakyThrows
    public void start()  {
        try {
            while (!stop) {
                var socket = this.serverSocket.accept();
                ex.execute(clazz.getConstructor(Socket.class).newInstance(socket));
            }
        } finally {
            stop();
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

    static private class BuilderServerIml implements BuilderServer{
        private int port;
        private String host;
        private Class<? extends Runnable> clazz;

        @Override
        public BuilderServer port(int port) {
           this.port = port;
           return this;
        }

        @Override
        public BuilderServer host(String host) {
            this.host = host;
            return this;
        }

        @Override
        public BuilderServer requestHandlerClass(Class<? extends Runnable> clazz) {
            this.clazz = clazz;
            return this;
        }

        @Override
        public Server build() {
            return new SimpleServer(host, port, clazz);
        }

    }
}
