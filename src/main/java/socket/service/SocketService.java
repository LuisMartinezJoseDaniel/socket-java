package socket.service;

import utils.DateUtils;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketService {
    private static SocketService instance;
    private final ServerSocket serverSocket;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3000;
    private static final String STX = "\u0002";
    private static final String ETX = "\u0003";
    private final ExecutorService threadPool;
    private static final String accion96 = STX + "96DR604629" + DateUtils.getFormattedDateYYYYMMDD() + DateUtils.getFormattedDateHHMMSS() + ETX;
    
    
    private SocketService() throws IOException {
        this.serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(HOST));
        this.threadPool = Executors.newCachedThreadPool();
        start();
    }
    
    public static synchronized void initSocketServer() throws IOException {
        if (instance == null) {
            instance = new SocketService();
        }
    }
    
    public static synchronized SocketService getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("Socket Service not initialized");
        }
        return instance;
    }
    
    private void start() {
        System.out.println("Server listening on " + HOST + ":" + PORT);
        
        threadPool.execute(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    handleClient(socket);
                } catch (IOException e) {
                    System.out.println("Error accepting client connection: " + e.getMessage());
                }
            }
        });
    }
    
    private void handleClient(Socket socket) {
        threadPool.execute(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                
                System.out.println("Client connected: " + socket.getRemoteSocketAddress() + "\n");
                
                if(socket.isConnected()){
                    System.out.println("Connection to the client successful");
                    System.out.println("Sending: " + accion96);
                    System.out.println();
                    out.println(accion96);
                }
                
                String data;
                while ((data = in.readLine()) != null) {
                    System.out.println("Received: " + data);
                    String response = "";
                    String action = data.substring(1, 3);
                    if ("98".equals(action)) {
                        response = STX + "99" + data.substring(3, data.length() - 1) + ETX;
                    } else if("97".equals(action)){
                        System.out.println("Connection to the client successful");
                    }
                    
                    if(response.isEmpty()){
                        continue;
                    }
                    
                    System.out.println("Replies: "  + response);
                    out.println(response);
                    System.out.println();
                }
                
             
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
                System.out.println("Client disconnected");
            }
        });
    }
    public static void main(String[] args) {
        try {
            SocketService.initSocketServer();
        } catch (IOException e) {
            System.out.println("Error initializing Socket Service: " + e.getMessage());
        }
    }
}
