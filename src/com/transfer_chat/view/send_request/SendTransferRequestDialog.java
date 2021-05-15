package com.transfer_chat.view.send_request;

import com.components.*;
import com.models.Manager;
import com.models.Player;
import com.models.Team;
import com.sql.SQL;
import com.transfer_chat.view.SendTransferTypeDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class SendTransferRequestDialog extends JDialog implements ActionListener, WindowListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    public MyButton submitTransferButton;
    public JPanel formPanel;
    public JPanel buttonPanel;

    public Player player;
    public Manager manager;
    public Team managersTeam;
    public SQL sql = new SQL();

    public JDialog owner;

    public SendTransferRequestDialog(String dialogTitle, Player player, Manager manager) {
        this.manager = manager; // current manager
        this.managersTeam = sql.getTeamGivenManagerID(manager.getManagerID()); // current manager's team
        this.player = player; // player to buy

        // Header Label
        JLabel headerLabel = new JLabel();
        headerLabel.setText(dialogTitle);
        headerLabel.setFont(myFont.getFontMedium().deriveFont(25f));
        headerLabel.setForeground(myColor.getPrimaryColor());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(myColor.getBackgroundColor());
        headerPanel.add(headerLabel);

        // Form Panel
        formPanel = new JPanel();
        formPanel.setBackground(myColor.getBackgroundColor());

        // Submit transfer button
        submitTransferButton = new MyButton("Submit");
        submitTransferButton.addActionListener(this);

        // Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setBackground(myColor.getBackgroundColor());
        buttonPanel.add(submitTransferButton);


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
        wrapperPanel.add(formPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
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
        addWindowListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    public void openTransferTypeDialog() {
        new SendTransferTypeDialog(player,manager);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        openTransferTypeDialog();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

