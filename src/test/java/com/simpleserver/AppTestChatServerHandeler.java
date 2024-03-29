package com.simpleserver;



import static org.junit.Assert.assertEquals;


import org.junit.Test;

import com.simpleserver.client.EchoClient;


public class AppTestChatServerHandeler 
{
    @Test
    public void givenClient1_whenServerResponds_thenCorrect() {
        EchoClient client1 = new EchoClient();
        client1.startConnection("localhost", 8989);
        String msg1 = client1.sendMessage("hello");
        String msg2 = client1.sendMessage("world");
        String terminate = client1.sendMessage(".");
        client1.stopConnection();

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() {
        EchoClient client2 = new EchoClient();
        client2.startConnection("localhost", 8989);
        String msg1 = client2.sendMessage("hello");
        String msg2 = client2.sendMessage("world");
        String terminate = client2.sendMessage(".");
        client2.stopConnection();

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
    }
}

