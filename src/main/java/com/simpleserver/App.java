package com.simpleserver;

import com.simpleserver.server.SimpleServer;
import com.simpleserver.server.defaultHandler.ServerSimpleChatSomthing;
import com.simpleserver.server.defaultHandler.ServerSomthing;



public class App 
{
    public static void main( String[] args )
    {
        SimpleServer.builder()
            .host("localhost")
            .port(8989)
            .requestHandlerClass(ServerSomthing.class)
            .build()
            .run();
    }
}
