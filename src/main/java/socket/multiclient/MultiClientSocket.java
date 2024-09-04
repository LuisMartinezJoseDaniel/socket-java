package socket.multiclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class MultiClientSocket {

    public static String readMessage() throws IOException {
        System.out.printf("\n->");
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        return reader.readLine();
    }
    
    public static void main( String[] args ) throws IOException {
        try ( Socket socket = new Socket("localhost", 8090);
              java.io.DataOutputStream out = new java.io.DataOutputStream(socket.getOutputStream())
        ) {
                String message;
            do{
                message = readMessage();
                out.writeUTF(message);
            } while (!"exit".equals(message));
        
        }
    }
}
