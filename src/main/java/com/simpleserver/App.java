package com.simpleserver;

import java.io.IOException;

import com.simpleserver.server.SimpleServer;
import com.simpleserver.server.defaultHandler.ServerSomthing;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        SimpleServer.builder()
            .host("localhost")
            .port(8989)
            .requestHandlerClass(ServerSomthing.class)
            .build()
            .run();
    }
}
