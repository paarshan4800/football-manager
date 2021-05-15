package com.transfer_chat.view.view_requests_frame;

import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyMenuBar;
import com.football_manager.DashboardFrame;
import com.models.LoanTransfer;
import com.models.PermanentTransfer;
import com.models.PlayerExchangeTransfer;
import com.sql.SQL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

public class ViewTransferRequestsFrame extends JFrame {

    MyMenuBar menuBar = new MyMenuBar(this);
    public MyColor myColor = new MyColor();
    public MyFont myFont = new MyFont();
    public MyImage myImage = new MyImage();

    public SQL sql = new SQL();
    public JPanel dataPanel;
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public HashMap<Class, String> transferTypeMapping = new HashMap<Class, String>();

    public ViewTransferRequestsFrame(String title)  {

        transferTypeMapping.put(PermanentTransfer.class, "Permanent Transfer");
        transferTypeMapping.put(LoanTransfer.class, "Loan Transfer");
        transferTypeMapping.put(PlayerExchangeTransfer.class, "Player Exchange");

        // Header Label
        JLabel headerLabel = new JLabel();
        headerLabel.setText(title);
        headerLabel.setForeground(myColor.getTextColor());
        headerLabel.setFont(myFont.getFontMedium());

//        // Refresh Label
//        JLabel refreshLabel = new JLabel();
//        refreshLabel.setIcon(myImage.getImage());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(myColor.getBackgroundColor());
        headerPanel.add(headerLabel);

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
