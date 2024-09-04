package com.gestopago;

import socket.service.SocketService;

import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException {
        
        SocketService.initSocketServer();
        
    }
}