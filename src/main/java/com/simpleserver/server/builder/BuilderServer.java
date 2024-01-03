package com.simpleserver.server.builder;

import com.simpleserver.server.Server;
import com.simpleserver.server.defaultHandler.ServerHandler;

public interface BuilderServer {
    BuilderServer port(int port);
    BuilderServer host(String host);
    BuilderServer requestHandlerClass(Class<? extends ServerHandler> clazz);
    Server build();
}
