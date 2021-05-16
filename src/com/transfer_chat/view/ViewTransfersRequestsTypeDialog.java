package com.transfer_chat.view;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.football_manager.DashboardFrame;
import com.models.Manager;
import com.transfer_chat.view.view_requests_frame.IncomingTransfersFrame;
import com.transfer_chat.view.view_requests_frame.OutgoingTransfersFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewTransfersRequestsTypeDialog extends JDialog implements ActionListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    MyButton incomingTransfersButton;
    MyButton outgoingTransfersButton;

    Manager manager;

    public ViewTransfersRequestsTypeDialog(Manager manager) {

        this.manager = manager;

        incomingTransfersButton = new MyButton("Incoming Transfers", "/icons/black/icon_incoming.png");
        incomingTransfersButton.addActionListener(this);

        outgoingTransfersButton = new MyButton("Outgoing Transfers", "/icons/black/icon_outgoing.png");
        outgoingTransfersButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(myColor.getBackgroundColor());
        buttonPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        buttonPanel.setLayout(new GridLayout(1, 2, 25, 0));
        buttonPanel.add(incomingTransfersButton);
        buttonPanel.add(outgoingTransfersButton);

        // Dialog Config
        setTitle("Transfer Type");
        getContentPane().setBackground(myColor.getBackgroundColor());

        add(buttonPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setIconImage(new MyImage().getLogo());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new DashboardFrame(new Manager());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        if (e.getSource() == incomingTransfersButton) {
            new IncomingTransfersFrame();
        } else if (e.getSource() == outgoingTransfersButton) {
            new OutgoingTransfersFrame();
        }
    }
}
