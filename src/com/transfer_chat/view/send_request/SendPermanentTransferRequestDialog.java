package com.transfer_chat.view.send_request;

import com.components.*;
import com.models.Manager;
import com.models.PermanentTransfer;
import com.models.Player;
import com.transfer_chat.message.RequestMessage;
import com.transfer_chat.process.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sql.TransferSQL.createTransfer;

public class SendPermanentTransferRequestDialog extends SendTransferRequestDialog implements ActionListener {

    MyFormField transferFeeField;
    Client client;

    public SendPermanentTransferRequestDialog(Player player, Manager manager) {

        super("Permanent Transfer", player, manager);

        client = new Client(6500, "localhost", manager.getManagerID());
        if (!client.startClient()) {
            return;
        }

        transferFeeField = new MyFormField("Transfer Fee");

        // Form Panel
        formPanel.setLayout(new GridLayout(1, 2, 10, 0));
        formPanel.add(transferFeeField.getInputLabel());
        formPanel.add(transferFeeField.getInputField());

        pack();
        setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitTransferButton) {
            Double transferFee = null;
            try {
                transferFee = Double.parseDouble(transferFeeField.getInputField().getText());
                PermanentTransfer permanentTransfer = new PermanentTransfer(
                        player,
                        player.getTeam(),
                        managersTeam,
                        1,
                        transferFee
                );

                if (createTransfer(permanentTransfer)) {
                    dispose();
                    JOptionPane.showMessageDialog(this, "Sent Permanent Transfer request to " + player.getTeam().getManager().getName() + " for " + player.getName(), "Sent Request", JOptionPane.INFORMATION_MESSAGE);

                    // Send message
                    RequestMessage requestMessage = new RequestMessage(RequestMessage.REQUEST_PING, "Test123", player.getTeam().getManager().getManagerID());
                    client.sendMessage(requestMessage);

                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't send request. Try again later", "Failure", JOptionPane.WARNING_MESSAGE);
                }
                openTransferTypeDialog(); // open Transfer Type Dialog after sending transfer request

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Invalid Transfer Fee", "Enter valid Transfer fee", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}
