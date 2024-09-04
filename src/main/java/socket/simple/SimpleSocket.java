package socket.simple;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSocket {
    
    public static void main( String[] args ) {
        try( ServerSocket server = new ServerSocket( 8090 ) ) {
            System.out.println("Server started on port 8090");
                try( Socket client = server.accept() ) {
                    System.out.printf("Client %s connected \n", client.getInetAddress().getHostAddress());
                    try( DataInputStream in = new DataInputStream( client.getInputStream() ) ) {
                        String message = null;
                        
                        do {
                            message = in.readUTF();
                            System.out.println("Message received: " + message);
                        } while( !"exit".equals(message) );
                    }
                }
        
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }
}
