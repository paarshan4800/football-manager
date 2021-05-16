package com.transfer_chat.process;


import com.transfer_chat.message.RequestMessage;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Scanner;

public class ClientMain {

    public static void displayInstructions() {
        System.out.println("----- INSTRUCTIONS -----");
        System.out.println("whoisonline -> Who is online?");
        System.out.println("logout -> Logout");
        System.out.println("transfer -> Transfer");
        System.out.println("loan -> Loan");
        System.out.println("viewtransfers -> View Transfer Requests");
        System.out.println("instructions -> Instructions");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter manager ID - ");
        int managerID = scanner.nextInt();

        Client client = new Client(6500, "localhost", managerID);
        if (!client.startClient()) {
            return;
        }


        displayInstructions();
        // Choices
        while (true) {
            // print instructions

            System.out.print("==> ");
            String choice = scanner.next().toLowerCase(Locale.ROOT);

            // Who is online?
            if (choice.equals("whoisonline")) {
                RequestMessage requestMessage = new RequestMessage(RequestMessage.ONLINE, "");
                client.sendMessage(requestMessage);
            }
            // Logout
            else if (choice.equals("logout")) {
                RequestMessage requestMessage = new RequestMessage(RequestMessage.LOGOUT, "");
                client.sendMessage(requestMessage);
                break;
            }
            // Request Ping
            else if (choice.equals("request")) {
                RequestMessage requestMessage = new RequestMessage(RequestMessage.REQUEST_PING, "Test123", 1001);
                client.sendMessage(requestMessage);
            }

            else if (choice.equals("instructions")) {
                displayInstructions();
            }
        }

        client.close();

    }
}
