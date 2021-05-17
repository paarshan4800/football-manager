package com.player_transfer.view_requests_frame;

import com.models.*;
import com.player_transfer.view_request.ViewLoanTransferRequestDialog;
import com.player_transfer.view_request.ViewPermanentTransferRequestDialog;
import com.player_transfer.view_request.ViewPlayerExchangeTransferRequestDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sql.TeamSQL.getTeamGivenManagerID;
import static com.sql.TransferSQL.getAllTransfersGivenTeamIDOfManagerAndType;

public class IncomingTransfersFrame extends ViewTransferRequestsFrame {

    public HashMap<Class, String> transferTypeMapping = new HashMap<Class, String>();

    public IncomingTransfersFrame(Manager manager) {

        super("Incoming Transfer Requests", manager);
        transferTypeMapping.put(PermanentTransfer.class, "Permanent Transfer");
        transferTypeMapping.put(LoanTransfer.class, "Loan Transfer");
        transferTypeMapping.put(PlayerExchangeTransfer.class, "Player Exchange");

        ArrayList<Transfer> incomingTransfers = getAllTransfersGivenTeamIDOfManagerAndType(getTeamGivenManagerID(manager.getManagerID()).getTeamID(), "incoming");

        if (incomingTransfers.size() == 0) {
            JLabel noTransfersLabel = new JLabel();
            noTransfersLabel.setText("No incoming transfers.");
            noTransfersLabel.setFont(myFont.getFontPrimary().deriveFont(16f));
            noTransfersLabel.setForeground(myColor.getTextColor());
            dataPanel.add(noTransfersLabel);
        } else {
            dataPanel.setLayout(new GridLayout(incomingTransfers.size() / 2, 3, 25, 25));

            for (Transfer transfer : incomingTransfers) {
                JPanel transferPanel = getTransferPanel(transfer);
                transferPanel.addMouseListener(new TransferListener(this, transfer, transferTypeMapping, manager));
                dataPanel.add(transferPanel);
            }
        }

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel getTransferPanel(Transfer transfer) {

        JLabel requestingTeamLabel = new JLabel();
        requestingTeamLabel.setIcon(new ImageIcon(myImage.getImageFromURL(transfer.getToTeam().getBadge()).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        requestingTeamLabel.setToolTipText(transfer.getToTeam().getName());

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
        transferPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        transferPanel.add(requestingTeamLabel, gbc);

        return transferPanel;
    }
}

class TransferListener implements MouseListener {

    JFrame owner;
    Transfer transfer;
    public HashMap<Class, String> transferTypeMapping;
    Manager manager;

    public TransferListener(JFrame owner, Transfer transfer, HashMap<Class, String> transferTypeMapping, Manager manager) {
        this.owner = owner;
        this.transfer = transfer;
        this.transferTypeMapping = transferTypeMapping;
        this.manager = manager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        owner.setEnabled(false);
        if (transfer.getClass() == PermanentTransfer.class) {
            new ViewPermanentTransferRequestDialog(owner, transferTypeMapping.get(transfer.getClass()), transfer, manager);
        } else if (transfer.getClass() == LoanTransfer.class) {
            new ViewLoanTransferRequestDialog(owner, transferTypeMapping.get(transfer.getClass()), transfer, manager);
        } else if (transfer.getClass() == PlayerExchangeTransfer.class) {
            new ViewPlayerExchangeTransferRequestDialog(owner, transferTypeMapping.get(transfer.getClass()), transfer, manager);
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
