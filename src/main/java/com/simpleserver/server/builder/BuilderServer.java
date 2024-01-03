package com.simpleserver.server.builder;

import com.simpleserver.server.Server;

public interface BuilderServer {
    BuilderServer port(int port);
    BuilderServer host(String host);
    BuilderServer requestHandlerClass(Class<? extends Runnable> clazz);
    Server build();
}
