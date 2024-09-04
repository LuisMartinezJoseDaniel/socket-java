package socket.multiclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

class WorkerSocket extends Thread{
    private Socket client;

    public WorkerSocket( Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try ( DataInputStream in = new DataInputStream(client.getInputStream()) ) {
            String message;
            
            do {
                message = in.readUTF();
                System.out.printf(" %s says: %s \n", client.getInetAddress(), message);
            } while (!"exit".equals(message));
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}

public class ServerSocketMultiClient {
    public static void main( String[] args ) {
        try ( java.net.ServerSocket server = new java.net.ServerSocket(8090) ) {
            while (true) {
                System.out.println("Waiting for clients...");
                Socket client = server.accept();
                System.out.println("Client connected: " + client.getInetAddress());
                WorkerSocket worker = new WorkerSocket(client);
                worker.start();
            }
        } catch ( IOException e ) {
            throw new RuntimeException(e);
        }
    }
}
