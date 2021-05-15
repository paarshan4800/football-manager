package com.transfer_chat.view.send_request;

import com.components.MyFormField;
import com.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class SendPlayerExchangeTransferRequestDialog extends SendTransferRequestDialog implements ActionListener, ItemListener {

    Player playerToExchange;

    JComboBox comboBox;
    MyFormField transferFeeField;

    public SendPlayerExchangeTransferRequestDialog(Player player, Manager manager) {
        super("Player Exchange Transfer", player, manager);

        // Player Exchange Label
        JLabel playerExchangeLabel = new JLabel();
        playerExchangeLabel.setText("Select Player to Exchange");
        playerExchangeLabel.setFont(myFont.getFontPrimary().deriveFont(20f));
        playerExchangeLabel.setForeground(myColor.getTextColor());
        playerExchangeLabel.setHorizontalAlignment(JLabel.CENTER);

        // Player to Exchange Dropdown
        // Get all current manager's player
        ArrayList<Player> managersPlayers = sql.getAllPlayersGivenTeamID(managersTeam.getTeamID());

        comboBox = new JComboBox();
        comboBox.addItemListener(this);
        for (Player managersPlayer : managersPlayers) {
            comboBox.addItem(managersPlayer);
        }
        comboBox.setEditable(false);
        comboBox.setFont(myFont.getFontPrimary());
        comboBox.setBackground(myColor.getBoxColor());
        comboBox.setForeground(myColor.getTextColor());

        // Transfer Fee Field
        transferFeeField = new MyFormField("Transfer Fee");

        // Transfer Fee Panel
        JPanel transferFeePanel = new JPanel();
        transferFeePanel.setBackground(myColor.getBackgroundColor());
        transferFeePanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        transferFeePanel.setLayout(new GridLayout(1, 2, 10, 0));
        transferFeePanel.add(transferFeeField.getInputLabel());
        transferFeePanel.add(transferFeeField.getInputField());

        // Form Panel
        formPanel.setLayout(new GridLayout(3, 1, 0, 10));
        formPanel.add(playerExchangeLabel);
        formPanel.add(comboBox);
        formPanel.add(transferFeePanel);

        pack();
        setLocationRelativeTo(null);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == comboBox) {
            playerToExchange = (Player) comboBox.getSelectedItem();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitTransferButton) {
            Double transferFee = null;
            try {
                transferFee = Double.parseDouble(transferFeeField.getInputField().getText());
                PlayerExchangeTransfer playerExchangeTransfer = new PlayerExchangeTransfer(
                        player,
                        player.getTeam(),
                        managersTeam,
                        1,
                        transferFee,
                        playerToExchange,
                        playerToExchange.getTeam()
                );

                if (sql.createTransfer(playerExchangeTransfer)) {
                    dispose();
                    JOptionPane.showMessageDialog(this, "Sent Player Exchange Transfer request to " + player.getTeam().getManager().getName() + " for " + player.getName(), "Sent Request", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't send request. Try again later", "Failure", JOptionPane.WARNING_MESSAGE);
                }
                openTransferTypeDialog(); // open Transfer Type Dialog after sending transfer request
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Invalid Transfer Fee", "Enter valid Transfer fee", JOptionPane.WARNING_MESSAGE);
            }

            System.out.println(transferFee);
            System.out.println("SUB" + comboBox.getSelectedItem());
        }
    }
}
