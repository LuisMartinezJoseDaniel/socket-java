package socket.simple;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
    public static void main( String[] args )  {
        try(Socket socket = new Socket("localhost", 8090)) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("Hello from client");
            out.writeUTF("exit");
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
    }
}
