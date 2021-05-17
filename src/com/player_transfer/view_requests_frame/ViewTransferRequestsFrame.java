package com.player_transfer.view_requests_frame;

import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyMenuBar;
import com.components.menu.MyRefreshLabel;
import com.models.LoanTransfer;
import com.models.Manager;
import com.models.PermanentTransfer;
import com.models.PlayerExchangeTransfer;
import com.sql.SQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class ViewTransferRequestsFrame extends JFrame {

    MyMenuBar menuBar;
    public MyColor myColor = new MyColor();
    public MyFont myFont = new MyFont();
    public MyImage myImage = new MyImage();

    public SQL sql = new SQL();
    public JPanel dataPanel;
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public HashMap<Class, String> transferTypeMapping = new HashMap<Class, String>();

    public Manager manager;

    public ViewTransferRequestsFrame(String title, Manager manager) {
        this.manager = manager;
        menuBar = new MyMenuBar(this, manager);

        transferTypeMapping.put(PermanentTransfer.class, "Permanent Transfer");
        transferTypeMapping.put(LoanTransfer.class, "Loan Transfer");
        transferTypeMapping.put(PlayerExchangeTransfer.class, "Player Exchange");

        // Header Label
        JLabel headerLabel = new JLabel();
        headerLabel.setText(title);
        headerLabel.setForeground(myColor.getTextColor());
        headerLabel.setFont(myFont.getFontMedium());

        // Refresh Label
        MyRefreshLabel refreshLabel = new MyRefreshLabel();
        refreshLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                if (title == "Outgoing Transfer Requests") {
                    new OutgoingTransfersFrame(manager);
                } else {
                    new IncomingTransfersFrame(manager);
                }
            }
        });

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(myColor.getBackgroundColor());

        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints headerGBC = new GridBagConstraints();
        headerGBC.insets = new Insets(0, 10, 0, 0);

        headerGBC.gridx = 0;
        headerGBC.gridy = 0;
        headerPanel.add(headerLabel, headerGBC);

        headerGBC.gridx = 1;
        headerGBC.gridy = 0;
        headerPanel.add(refreshLabel, headerGBC);

        // Data Panel
        dataPanel = new JPanel();
        dataPanel.setBackground(myColor.getBackgroundColor());

        // wrapper panel
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        wrapperPanel.setBackground(myColor.getBackgroundColor());
        wrapperPanel.setLayout(new GridBagLayout());

        GridBagConstraints wrapperGBC = new GridBagConstraints();
        wrapperGBC.insets = new Insets(20, 20, 20, 20);
        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapperPanel.add(headerPanel, wrapperGBC);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 1;
        wrapperPanel.add(dataPanel, wrapperGBC);

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(wrapperPanel);

        // Frame Config
        setJMenuBar(menuBar);
        setTitle("Transfer Requests");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenSize.width, screenSize.height);
        getContentPane().setBackground(myColor.getBackgroundColor());
        add(scrollPane);
        setIconImage(new MyImage().getLogo());
        setVisible(true);

    }

}
