package com.simpleserver.server.defaultHandler;

import java.net.Socket;

public interface ServerHandler extends Runnable {
    void bindSocket(Socket socket);
    void shutdown();
}
