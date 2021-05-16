package com.transfer_chat.view;

import com.components.*;
import com.football_manager.PlayersFrame;
import com.models.Manager;
import com.models.Player;
import com.sql.SQL;
import com.transfer_chat.process.Client;
import com.transfer_chat.view.send_request.SendLoanTransferRequestDialog;
import com.transfer_chat.view.send_request.SendPermanentTransferRequestDialog;
import com.transfer_chat.view.send_request.SendPlayerExchangeTransferRequestDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SendTransferTypeDialog extends JDialog implements ActionListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    SQL sql = new SQL();

    MyButton permanentTransferButton;
    MyButton loanTransferButton;
    MyButton playerExchangeButton;
    ArrayList<MyDataLabel> playerDataLabels;

    public Player player;
    public Manager manager;
    Client client;

    public SendTransferTypeDialog(Player player, Manager manager) {

//        client = new Client(6500, "localhost", 1002);
//        if (!client.startClient()) {
//            return;
//        }
        this.manager = manager;
        this.player = player;

        // Player Data Labels
        playerDataLabels = new ArrayList<>();
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getPlayerID()), "/icons/color/icon_id.png", "Player ID"));
        playerDataLabels.add(new MyDataLabel(player.getName(), "/icons/color/icon_name.png", "Player Name"));
        playerDataLabels.add(new MyDataLabel(player.getTeam().getName(), "/icons/color/icon_team.png", "Team"));
        playerDataLabels.add(new MyDataLabel(player.getPosition(), "/icons/color/icon_position.png", "Position"));

        // Top Panel with Player Data
        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(myColor.getBackgroundColor());
        GridLayout dataGridLayout = new GridLayout(2, 2);
        dataGridLayout.setHgap(25);
        dataPanel.setLayout(dataGridLayout);
        dataPanel.setBorder(new EmptyBorder(50, 50, 25, 50));

        for (MyDataLabel dataLabel : playerDataLabels) {
            dataPanel.add(dataLabel);
        }


        // Transfer Buttons
        permanentTransferButton = new MyButton("Permanent Transfer");
        permanentTransferButton.addActionListener(this);

        loanTransferButton = new MyButton("Loan Transfer");
        loanTransferButton.addActionListener(this);

        playerExchangeButton = new MyButton("Player Exchange");
        playerExchangeButton.addActionListener(this);


        // Bottom Panel with Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(myColor.getBackgroundColor());
        GridLayout buttonGridLayout = new GridLayout(1, 3);
        buttonGridLayout.setHgap(25);
        buttonPanel.setLayout(buttonGridLayout);
        buttonPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        buttonPanel.add(permanentTransferButton);
        buttonPanel.add(loanTransferButton);
        buttonPanel.add(playerExchangeButton);

        // Dialog Config
        setTitle("Transfer Type");
        getContentPane().setBackground(myColor.getBackgroundColor());
        GridLayout dialogGridLayout = new GridLayout(2, 1);
        setLayout(dialogGridLayout);
        add(dataPanel);
        add(buttonPanel);
//        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setIconImage(new MyImage().getLogo());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new PlayersFrame(manager);
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //dispose this frame after clicking anyone of the options
        // then open
        dispose();
        if (e.getSource() == permanentTransferButton) {
            new SendPermanentTransferRequestDialog(player,manager);
        } else if (e.getSource() == loanTransferButton) {
            new SendLoanTransferRequestDialog(player,manager);
        } else if (e.getSource() == playerExchangeButton) {
            new SendPlayerExchangeTransferRequestDialog(player,manager);
        }
    }
}
