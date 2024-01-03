package com.simpleserver.server;

public interface Server extends Runnable {
    void stop();
    void start();
}
