package com.simpleserver.client;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import lombok.SneakyThrows;

public class EchoFileClient {
    
    private HttpURLConnection connection;
    private  String host;
    private int port;

   
    public EchoFileClient(String ip, int port) {
        this.host = ip;
        this.port = port;
        
        

    }

    @SneakyThrows
    public void getFile(String serverFile,String outFile){
        URI uri = new URI("http://" + host + ":" + port + "/" + serverFile);
        System.out.println(uri);
        connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        try(var in = new DataInputStream(connection.getInputStream())){
            try(FileOutputStream o = new FileOutputStream(outFile)){
                byte[] bytes = new byte[5*1024];
                int count;                    
                while ((count = in.read(bytes)) > -1) {
                    o.write(bytes, 0, count);
                }
            }
        }
    }


    @SneakyThrows
    public void stopConnection() {
        
       connection.disconnect();
        

    }
}
