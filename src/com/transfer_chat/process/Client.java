package com.transfer_chat.process;

import com.transfer_chat.message.RequestMessage;
import com.transfer_chat.message.ResponseMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
    private Socket socket; // socket
    private final int portNo; // port number
    private final String server; // server
    private ObjectInputStream objectInputStream; // output stream
    private ObjectOutputStream objectOutputStream; // input stream
    private final int managerID; // manager ID

    public Client(int portNo, String server, int managerID) {
        // Initialization
        this.portNo = portNo;
        this.server = server;
        this.managerID = managerID;
    }

    public boolean startClient() {
        // create client socket
        try {
            socket = new Socket(server, portNo);
        } catch (IOException ioException) {
            display("Exception at new Socket() - " + ioException);
            return false;
        }

        display("Connection established with " + socket.getInetAddress() + " at " + socket.getPort());

        // create i/o streams
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ioException) {
            display("Exception at creating Data Streams - " + ioException);
            return false;
        }

        // Create and start listen thread
        new ListenThread().start();

        // send managerID to server
        try {
            objectOutputStream.writeObject(managerID);
        } catch (IOException ioException) {
            display("Exception at writing to server - " + ioException);
            close();
            return false;
        }

        return true;

    }

    public void sendMessage(RequestMessage requestMessage) {
        try {
            objectOutputStream.writeObject(requestMessage);
        } catch (IOException ioException) {
            display("Exception at writing to client_server.Server - " + ioException);
        }

    }

    // Display message with current time
    public void display(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + " - " + message);
    }

    public void close() {
        try {
            objectInputStream.close();
        } catch (IOException ioException) {
            display("Exception at closing input stream - " + ioException);
        }
        try {
            objectOutputStream.close();
        } catch (IOException ioException) {
            display("Exception at closing output stream - " + ioException);
        }
        try {
            socket.close();
        } catch (IOException ioException) {
            display("Exception at closing socket - " + ioException);
        }
    }

    class ListenThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    ResponseMessage responseMessage = (ResponseMessage) objectInputStream.readObject();
                    System.out.println(responseMessage.getMessage());
                    System.out.print("==> ");
                } catch (IOException ioException) {
                    display("Server has closed the connection - " + ioException);
                    break;
                } catch (ClassNotFoundException e) {
                }
            }

        }
    }
}

