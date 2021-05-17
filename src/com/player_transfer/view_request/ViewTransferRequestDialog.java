package com.player_transfer.view_request;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyPlayerDetailsPanel;
import com.models.*;
import com.sql.SQL;
import com.player_transfer.view_requests_frame.IncomingTransfersFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static com.sql.TransferSQL.transferRequestAction;

public class ViewTransferRequestDialog extends JDialog implements ActionListener {

    public HashMap<Class, String> transferTypeMapping = new HashMap<Class, String>();


    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    MyImage myImage = new MyImage();

    public MyButton acceptTransferButton;
    public MyButton rejectTransferButton;
    public JPanel dataPanel;
    public JPanel buttonPanel;

    public Player player;
    public Transfer transfer;

    public SQL sql = new SQL();
    JFrame owner;

    Manager manager;

    public ViewTransferRequestDialog(JFrame owner, String dialogTitle, Transfer transfer, Manager manager) {
        this.owner = owner;
        this.transfer = transfer;
        this.player = transfer.getPlayer();
        this.manager = manager;

        transferTypeMapping.put(PermanentTransfer.class, "Permanent Transfer");
        transferTypeMapping.put(LoanTransfer.class, "Loan Transfer");
        transferTypeMapping.put(PlayerExchangeTransfer.class, "Player Exchange");

        // Header Label
        JLabel headerLabel = new JLabel();
        headerLabel.setText(transferTypeMapping.get(transfer.getClass()));
        headerLabel.setFont(myFont.getFontMedium().deriveFont(25f));
        headerLabel.setForeground(myColor.getPrimaryColor());
        headerLabel.setIconTextGap(50);
        headerLabel.setHorizontalTextPosition(JLabel.LEFT);
        headerLabel.setIcon(new ImageIcon(myImage.getImageFromURL(transfer.getToTeam().getBadge()).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(myColor.getBackgroundColor());
        headerPanel.add(headerLabel);

        // Player Data panel
        JPanel playerDataPanel = new MyPlayerDetailsPanel(player);

        // Requesting Team Details

        // Data Panel
        dataPanel = new JPanel();
        dataPanel.setBackground(myColor.getBackgroundColor());

        // Accept transfer button
        acceptTransferButton = new MyButton("Accept");
        acceptTransferButton.addActionListener(this);

        // Reject transfer button
        rejectTransferButton = new MyButton("Reject");
        rejectTransferButton.addActionListener(this);

        // Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 25, 0));
        buttonPanel.setBackground(myColor.getBackgroundColor());
        buttonPanel.add(acceptTransferButton);
        buttonPanel.add(rejectTransferButton);

        // Wrapper
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        wrapperPanel.setLayout(new GridBagLayout());
        wrapperPanel.setBackground(myColor.getBackgroundColor());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        wrapperPanel.add(headerPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        wrapperPanel.add(playerDataPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        wrapperPanel.add(dataPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        wrapperPanel.add(buttonPanel, gbc);

        // Dialog Config
        setTitle(dialogTitle);
        getContentPane().setBackground(myColor.getBackgroundColor());
        add(wrapperPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setIconImage(new MyImage().getLogo());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String dialogMessage = null;
        if (e.getSource() == acceptTransferButton) {
            transfer.setStatus(Transfer.ACCEPTED);
            dialogMessage = transfer.getPlayer().getName() + " has moved to " + transfer.getToTeam().getName() + " successfully";
        } else if (e.getSource() == rejectTransferButton) {
            transfer.setStatus(Transfer.REJECTED);
            dialogMessage = "Transfer of " + transfer.getPlayer().getName() + " to " + transfer.getToTeam().getName() + " rejected";
        }

        if (transferRequestAction(transfer)) {
            dispose();
            JOptionPane.showMessageDialog(this, dialogMessage, "Transfer Successful", JOptionPane.INFORMATION_MESSAGE);
            owner.dispose();
            new IncomingTransfersFrame(manager);
        } else {
            JOptionPane.showMessageDialog(this, "Problem Occurred. Try again later!", "Transfer Successful", JOptionPane.WARNING_MESSAGE);
        }

    }
}
