package com.transfer_chat.view.view_requests_frame;

import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.MyLoader;
import com.components.menu.MyMenuBar;
import com.models.LoanTransfer;
import com.models.PermanentTransfer;
import com.models.PlayerExchangeTransfer;
import com.models.Transfer;
import com.sql.SQL;
import com.transfer_chat.view.view_request.ViewLoanTransferRequestDialog;
import com.transfer_chat.view.view_request.ViewPermanentTransferRequestDialog;
import com.transfer_chat.view.view_request.ViewPlayerExchangeTransferRequestDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class IncomingTransfersFrame extends ViewTransferRequestsFrame {

    public HashMap<Class, String> transferTypeMapping = new HashMap<Class, String>();

    public IncomingTransfersFrame() {

        super("Incoming Transfer Requests");
        transferTypeMapping.put(PermanentTransfer.class, "Permanent Transfer");
        transferTypeMapping.put(LoanTransfer.class, "Loan Transfer");
        transferTypeMapping.put(PlayerExchangeTransfer.class, "Player Exchange");
//        JLabel jLabel = new JLabel("testign loader");
        MyLoader myLoader = new MyLoader();
        dataPanel.add(myLoader);

        ArrayList<Transfer> incomingTransfers = sql.getAllTransfersGivenTeamIDOfManagerAndType(BigInteger.valueOf(2627), "incoming");

        dataPanel.remove(myLoader);

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
                transferPanel.addMouseListener(new TransferListener(this, transfer, transferTypeMapping));
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

    public TransferListener(JFrame owner, Transfer transfer, HashMap<Class, String> transferTypeMapping) {
        this.owner = owner;
        this.transfer = transfer;
        this.transferTypeMapping = transferTypeMapping;
//        transferTypeMapping.put(PermanentTransfer.class, "Permanent Transfer");
//        transferTypeMapping.put(LoanTransfer.class, "Loan Transfer");
//        transferTypeMapping.put(PlayerExchangeTransfer.class, "Player Exchange");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        owner.setEnabled(false);
        if (transfer.getClass() == PermanentTransfer.class) {
            new ViewPermanentTransferRequestDialog(owner, transferTypeMapping.get(transfer.getClass()), transfer);
        } else if (transfer.getClass() == LoanTransfer.class) {
            new ViewLoanTransferRequestDialog(owner, transferTypeMapping.get(transfer.getClass()), transfer);
        } else if (transfer.getClass() == PlayerExchangeTransfer.class) {
            new ViewPlayerExchangeTransferRequestDialog(owner, transferTypeMapping.get(transfer.getClass()), transfer);
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
