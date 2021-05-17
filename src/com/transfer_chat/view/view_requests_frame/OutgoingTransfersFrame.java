package com.transfer_chat.view.view_requests_frame;

import com.components.MyLoader;
import com.models.Manager;
import com.models.Transfer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sql.TeamSQL.getTeamGivenManagerID;
import static com.sql.TransferSQL.getAllTransfersGivenTeamIDOfManagerAndType;

public class OutgoingTransfersFrame extends ViewTransferRequestsFrame {

    public HashMap<Integer, String> transferStatusMapping = new HashMap<>();

    public OutgoingTransfersFrame(Manager manager) {
        super("Outgoing Transfer Requests",manager);

        transferStatusMapping.put(1, "OPEN");
        transferStatusMapping.put(2, "ACCEPTED");
        transferStatusMapping.put(3, "REJECTED");

        MyLoader myLoader = new MyLoader();
        dataPanel.add(myLoader);

        ArrayList<Transfer> outgoingTransfers = getAllTransfersGivenTeamIDOfManagerAndType(getTeamGivenManagerID(manager.getManagerID()).getTeamID(), "outgoing");

        dataPanel.remove(myLoader);

        if (outgoingTransfers.size() == 0) {
            JLabel noTransfersLabel = new JLabel();
            noTransfersLabel.setText("No outgoing transfers.");
            noTransfersLabel.setFont(myFont.getFontPrimary().deriveFont(16f));
            noTransfersLabel.setForeground(myColor.getTextColor());
            dataPanel.add(noTransfersLabel);
        } else {
            dataPanel.setLayout(new GridLayout(outgoingTransfers.size() / 2, 3, 25, 25));
            for (Transfer transfer : outgoingTransfers) {
                JPanel transferPanel = getTransferPanel(transfer);
                dataPanel.add(transferPanel);
            }
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

        JLabel transferStatusLabel = new JLabel();
        transferStatusLabel.setText(transferStatusMapping.get(transfer.getStatus()));
        transferStatusLabel.setFont(myFont.getFontPrimary().deriveFont(16f));
        transferStatusLabel.setForeground(myColor.getTextColor());

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

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        transferPanel.add(transferStatusLabel, gbc);

        return transferPanel;
    }
}
