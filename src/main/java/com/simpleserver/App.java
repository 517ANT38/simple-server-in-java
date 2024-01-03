package com.simpleserver;

import com.simpleserver.server.SimpleServer;
import com.simpleserver.server.defaultHandler.ServerSimpleChatSomthing;



public class App 
{
    public static void main( String[] args )
    {
        SimpleServer.builder()
            .host("localhost")
            .port(8989)
            .requestHandlerClass(ServerSimpleChatSomthing.class)
            .build()
            .run();
    }
}
