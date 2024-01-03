package com.simpleserver;

import com.simpleserver.server.SimpleServer;
import com.simpleserver.server.defaultHandler.ServerStaticHandler;
import com.simpleserver.server.defaultHandler.ServerSimpleChatHandler;



public class App 
{
    public static void main( String[] args )
    {
        SimpleServer.builder()
            .host("localhost")
            .port(8989)
            .requestHandlerClass(ServerStaticHandler.class)
            .build()
            .run();
    }
}
