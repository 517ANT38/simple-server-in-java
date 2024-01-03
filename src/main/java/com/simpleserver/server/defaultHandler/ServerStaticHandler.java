package com.simpleserver.server.defaultHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class ServerStaticHandler implements ServerHandler {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out; 
    private static final String PATH_WEB_APP = "webapps";

    

    @Override
    public void run() {
        if(socket == null)
            throw new RuntimeException("Not bind socket");
        try {
            
            while (!in.ready());                
            var path = in.readLine().split(" ")[1];            

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: */*; charset=utf-8");
            out.println();
            
            if (path.equals("/")){

                out.println("<p>Выбирете объект получения!</p>");
                
            }
            else {
                try(DataOutputStream outF = new DataOutputStream(socket.getOutputStream())){
                    File file = new File(PATH_WEB_APP + path);
                    try (var inF = new FileInputStream(file) ) {
                        
                        byte[] bytes = new byte[5*1024];
                        int count;
                        
                        while ((count = inF.read(bytes)) > -1) {
                            outF.write(bytes, 0, count);
                        }
                        outF.flush();
                    } 
                }
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        } finally {
            shutdown();
        }
        
        
    }



    @Override
    @SneakyThrows
    public void bindSocket(Socket socket) {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(),true);
    }



    @Override
    @SneakyThrows
    public void shutdown() {
        out.close();
        in.close();
        socket.close();
    }
    
}
