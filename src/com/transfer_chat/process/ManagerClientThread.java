package com.transfer_chat.process;

import com.models.Manager;
import com.models.Team;
import com.sql.TeamSQL;
import com.sql.SQL;
import com.transfer_chat.message.RequestMessage;
import com.transfer_chat.message.ResponseMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.sql.ManagerSQL.getManagerGivenManagerID;

public class ManagerClientThread extends Thread {

    public Socket socket; // client socket
    Server server;
    public ObjectInputStream objectInputStream; // input stream of socket
    public ObjectOutputStream objectOutputStream; // output stream of socket
    Manager manager; // manager related to this thread
    RequestMessage requestMessage; // request message
    SQL sql = new SQL();

    public ManagerClientThread(Socket socket, Server server) {
        // Initialization
        this.socket = socket;
        this.server = server;

        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            int managerID = (int) objectInputStream.readObject();
            this.manager = getManagerGivenManagerID(managerID);

            System.out.println("Username is - " + managerID);

        } catch (IOException | ClassNotFoundException ioException) {
            display("Exception at new DataOutputStream() - " + ioException);
        }
    }

    @Override
    public String toString() {
        return "ManagerClientThread{" +
                "manager=" + manager +
                '}';
    }

    @Override
    public void run() {
        boolean isClientRunning = true; // loop till logout

        while (isClientRunning) {
            // Read message from client
            try {
                requestMessage = (RequestMessage) objectInputStream.readObject();
            } catch (IOException ioException) {
                display("Exception at reading from client - " + ioException);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            int type = requestMessage.getType();
            String message = requestMessage.getMessage();

            // populate these things for optimization
            Team managersTeam = TeamSQL.getTeamGivenManagerID(manager.getManagerID());

            // who is online?
            if (type == 1) {
                sendMessage(new ResponseMessage(ResponseMessage.NORMAL, "--- Active Managers ---"));
                for (ManagerClientThread managerClientThread : server.getManagerClientThreads()) {
                    sendMessage(new ResponseMessage(ResponseMessage.NORMAL, String.valueOf(managerClientThread.manager.getName())));
                }
            }
            // logout
            else if (type == 2) {
                display(String.valueOf(manager.getManagerID()) + " logged off");
                isClientRunning = false;
            } else if (type == 3) {
                Manager playersManager = getManagerGivenManagerID(requestMessage.getPlayersManagerID()); // player's manager object
                int playersManagerIndex = isManagerActive(playersManager.getManagerID());

                // Player's Manager is active
                if (playersManagerIndex != -1) {
                    // get player's manager client and send request received notification
                    ManagerClientThread playersManagerClient = server.getManagerClientThreads().get(playersManagerIndex);
                    playersManagerClient.sendMessage(new ResponseMessage(
                            ResponseMessage.TRANSFER_REQUEST,
                            requestMessage.getMessage()
                    ));
                }

//                // send request sent ack message to requested manager
//                sendMessage(new ResponseMessage(
//                        ResponseMessage.NORMAL,
//                        "Sent transfer request to " + playersManager.getName()
//                ));
            }

        }
        remove(this);
        close();

    }

    public void close() {
        try {
            objectOutputStream.close();
        } catch (IOException ioException) {
            display("Exception at closing output stream - " + ioException);
        }
        try {
            objectInputStream.close();
        } catch (IOException ioException) {
            display("Exception at closing input stream - " + ioException);
        }
        try {
            socket.close();
        } catch (IOException ioException) {
            display("Exception at closing socket - " + ioException);
        }

    }

    public int isManagerActive(int managerID) {

        for (int i = 0; i < server.getManagerClientThreads().size(); i++) {
            if (Objects.equals(managerID, server.getManagerClientThreads().get(i).manager.getManagerID())) {
                return i;
            }
        }

        return -1;
    }

    public void sendMessage(ResponseMessage responseMessage) {
        try {
            objectOutputStream.writeObject(responseMessage);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // Display message with current time
    public void display(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now + " - " + message);
    }

    synchronized void remove(ManagerClientThread loggingOffManager) {
        // remove the logging off manager
        server.getManagerClientThreads().remove(loggingOffManager);

    }
}

