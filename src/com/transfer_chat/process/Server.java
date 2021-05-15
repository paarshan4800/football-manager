package com.transfer_chat.process;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server {
    private ArrayList<ManagerClientThread> managerClientThreads; // list of clients
    private int portNo; // port number
    private boolean isServerRunning; // server status
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); // date time formatter

    public Server(int portNo) {
        // Initialization
        this.portNo = portNo;
        managerClientThreads = new ArrayList<ManagerClientThread>();
    }


    public boolean isServerRunning() {
        return isServerRunning;
    }

    public void setServerRunning(boolean serverRunning) {
        isServerRunning = serverRunning;
    }

    public ArrayList<ManagerClientThread> getManagerClientThreads() {
        return managerClientThreads;
    }

    public void startServer() {
        isServerRunning = true;
        try {
            // create server socket
            ServerSocket serverSocket = new ServerSocket(portNo);

            // Till server is up and running
            while (isServerRunning) {
                display("Waiting for clients on port - " + portNo);

                // Accept connection if client requests
                Socket socket = serverSocket.accept();

                // If server isn't running
                if (!isServerRunning) break;

                // Create a thread for client
                ManagerClientThread managerClientThread = new ManagerClientThread(socket,this);
                managerClientThreads.add(managerClientThread); // add client to client list
                managerClientThread.start(); // start client thread
            }

            // Close ServerSocket and Client Sockets
            serverSocket.close();
            for (ManagerClientThread managerClientThread : managerClientThreads) {
                managerClientThread.objectInputStream.close();
                managerClientThread.objectOutputStream.close();
                managerClientThread.socket.close();
            }

        } catch (IOException ioException) {
            LocalDateTime now = LocalDateTime.now();
            System.out.println("Exception on new ServerSocket() - " + ioException + " at " + now.format(dateTimeFormatter));
        }

    }

    // Display message with current time
    public void display(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + " - " + message);
    }

}

