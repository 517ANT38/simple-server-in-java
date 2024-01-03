package com.simpleserver.server.defaultHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.SneakyThrows;

public class ServerStaticHandler implements Runnable {

    private Socket socket; 
    private static final String PATH_WEB_APP = "webapps";

    public ServerStaticHandler(Socket socket)  {
        this.socket = socket;
        
    }

    @Override
    @SneakyThrows
    public void run() {
        
        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            while (!in.ready());                
            var path = in.readLine().split(" ")[1];
            
            var out = new PrintWriter(socket.getOutputStream(),true);

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: */*; charset=utf-8");
            out.println();
            
            if (path.equals("/")){

                out.println("<p>Выбирете объект получения!</p>");
                
            }
            else {
                DataOutputStream outF = new DataOutputStream(socket.getOutputStream());
                File file = new File(PATH_WEB_APP + path);
                try (var inF = new FileInputStream(file) ) {
                    
                    byte[] bytes = new byte[5*1024];
                    int count;
                    
                    while ((count = inF.read(bytes)) > -1) {
                        outF.write(bytes, 0, count);
                    }
                    
                } 
                outF.close();
            }
            out.close();
        }
        socket.close();
    }
    
}
