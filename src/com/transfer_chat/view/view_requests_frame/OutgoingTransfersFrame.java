package com.transfer_chat.view.view_requests_frame;

import com.models.Transfer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class OutgoingTransfersFrame extends ViewTransferRequestsFrame {

    public OutgoingTransfersFrame() {
        super("Outgoing Transfer Requests");
        JLabel jLabel = new JLabel("testign loader");
        dataPanel.add(jLabel);

        ArrayList<Transfer> incomingTransfers = sql.getAllTransfersGivenTeamIDOfManagerAndType(BigInteger.valueOf(2612), "outgoing");

        dataPanel.setLayout(new GridLayout(incomingTransfers.size() / 2, 3, 25, 25));

        dataPanel.remove(jLabel);

        for (Transfer transfer : incomingTransfers) {
            JPanel transferPanel = getTransferPanel(transfer);
            dataPanel.add(transferPanel);
            transferPanel.addMouseListener(new TransferListener(this,transfer, transferTypeMapping));
        }

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel getTransferPanel(Transfer transfer) {

        JLabel playersTeamLabel = new JLabel();
        playersTeamLabel.setIcon(new ImageIcon(myImage.getImageFromURL(transfer.getFromTeam().getBadge()).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        playersTeamLabel.setToolTipText(transfer.getFromTeam().getName());

        JLabel playerLabel = new JLabel();
        playerLabel.setText(transfer.getPlayer().getName());
        playerLabel.setFont(myFont.getFontPrimary());
        playerLabel.setForeground(myColor.getPrimaryColor());

        JLabel transferTypeLabel = new JLabel();
        transferTypeLabel.setText(transferTypeMapping.get(transfer.getClass()));
        transferTypeLabel.setFont(myFont.getFontPrimary().deriveFont(16f));
        transferTypeLabel.setForeground(myColor.getTextColor());

        JPanel transferPanel = new JPanel();
        transferPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        transferPanel.setBackground(myColor.getBoxColor());
        transferPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        transferPanel.add(playerLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        transferPanel.add(transferTypeLabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        transferPanel.add(playersTeamLabel, gbc);

        return transferPanel;
    }
}
