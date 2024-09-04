package socket.service;

import utils.DateUtils;
import utils.ValidateRequestEcho;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
    private Socket socket;
    private static ClientSocket instance;
    private static final String HOST = "localhost";
    private static final Integer PORT = 3000;
    private static final String STX = "\u0002"; // Código ASCII para STX
    private static final String ETX = "\u0003";
    private static final String accion98 = STX + "98DR604629" + DateUtils.getFormattedDateYYYYMMDD() + DateUtils.getFormattedDateHHMMSS() + ETX;
    private static final String accion97 = STX + "97TL000140" + DateUtils.getFormattedDateYYYYMMDD() + DateUtils.getFormattedDateHHMMSS() + ETX;
    
    private ClientSocket() {
        // Inicializa el socket y comienza la conexión
        connect();
    }
    
    public static synchronized void initSocketClient() {
        if (instance == null) {
            instance = new ClientSocket();
        }
    }
    
    private void connect() {
        while (true) {
            try {
                // Intentar conectar al servidor
                this.socket = new Socket(HOST, PORT);
                System.out.println("\nClient connected to " + HOST + ":" + PORT);
                handleServer();
            } catch (IOException e) {
                System.out.println("Connection failed: " + e.getMessage());
                try {
                    // Esperar antes de intentar reconectar
                    Thread.sleep(5000); // Espera 5 segundos antes de intentar reconectar
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    private void handleServer() {
        try (
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Sending: " + accion98 + "\n") ;
            writer.println(accion98);
            
            String data;
            while ((data = reader.readLine()) != null) {
                if (data.length() < 3) {
                    continue;
                }
                System.out.println("Received: " + data);
                String action = data.substring(1, 3);
                if ("99".equals(action)) {
                    System.out.println("Connection successful");
                } else if ("96".equals(action)) {
                    System.out.println("Replies: " + accion97 + "\n");
                    writer.println(accion97);
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
            System.out.println("Server disconnected");
        }
    }
    
    public static void main(String[] args) {
        initSocketClient();
//        System.out.println(ValidateRequestEcho.isValidRequest(accion97));
    }
    
}
