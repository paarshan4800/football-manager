package com.transfer_chat.view;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyMenuBar;
import com.models.*;
import com.sql.SQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

public class TransferRequestsFrame extends JFrame implements ActionListener {

    MyMenuBar menuBar;
    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    SQL sql = new SQL();

    MyButton incomingRequestsButton;
    MyButton outgoingRequestsButton;
    MyButton refreshButton;

    public TransferRequestsFrame() {

        menuBar = new MyMenuBar(this, new Manager());

        JLabel testLabel = new JLabel();
        testLabel.setText("12345");
        testLabel.setForeground(myColor.getTextColor());
        testLabel.setFont(myFont.getFontMedium());


        // Data panel
        JPanel dataPanel = new JPanel();
        dataPanel.add(testLabel);


        // Buttons
        incomingRequestsButton = new MyButton("Incoming");
        incomingRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        outgoingRequestsButton = new MyButton("Outgoing");
//        outgoingRequestsButton.addActionListener(new ButtonListener(this,outgoingRequestsButton,dataPanel));
        refreshButton = new MyButton("Refresh");
        refreshButton.addActionListener(this);


        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 25, 0));

        buttonPanel.add(incomingRequestsButton);
        buttonPanel.add(outgoingRequestsButton);
        buttonPanel.add(refreshButton);


        ArrayList<Transfer> transfers = sql.getAllTransfersGivenTeamIDOfManager(BigInteger.valueOf(2627));
        ArrayList<Transfer> permanentTransfers = new ArrayList<>();
        ArrayList<Transfer> loanTransfers = new ArrayList<>();
        ArrayList<Transfer> playerExchangeTransfers = new ArrayList<>();

        for (Transfer transfer : transfers) {
            if (transfer.getClass() == PermanentTransfer.class) {
                permanentTransfers.add((PermanentTransfer) transfer);
            } else if (transfer.getClass() == LoanTransfer.class) {
                loanTransfers.add((LoanTransfer) transfer);
            } else if (transfer.getClass() == PlayerExchangeTransfer.class) {
                playerExchangeTransfers.add((PlayerExchangeTransfer) transfer);
            }
        }


        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        wrapperPanel.setBackground(new MyColor().getBackgroundColor());// set background color for wrapper
        wrapperPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        wrapperPanel.add(buttonPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        wrapperPanel.add(dataPanel, gbc);


        // Frame Config
        setJMenuBar(menuBar);
        setTitle("Transfer Requests");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        getContentPane().setBackground(myColor.getBackgroundColor());
        add(wrapperPanel);
        setIconImage(new MyImage().getLogo());
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}

class ButtonListener implements ActionListener {

    JFrame currentFrame;
    MyButton myButton;

    public ButtonListener(JFrame currentFrame, MyButton myButton) {
        this.currentFrame = currentFrame;
        this.myButton = myButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyButton sourceButton = (MyButton) e.getSource();
        if (Objects.equals(sourceButton.getText(), "Incoming")) {
            System.out.println("incoming");
//            currentFrame.getContentPane().remove();
        } else if (Objects.equals(sourceButton.getText(), "Outgoing")) {
            System.out.println("outgoing");
        }
    }
}
