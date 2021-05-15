package com.transfer_chat.process;

public class ServerMain {
    public static void main(String[] args) {
// Start Server
        Server server = new Server(6500); // create server object
        server.startServer();
    }
}
