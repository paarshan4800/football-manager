package com.football_manager;

import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {


//        Manager Details
        ArrayList<JLabel> managerDetails = getManagerDetails("hello");


        // Left Panel -> Details

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new MyColor().getBackgroundColor());
        leftPanel.setLayout(new GridBagLayout());
//        leftPanel.setBackground(Color.BLUE);
        GridBagConstraints leftGBC = new GridBagConstraints();

        for (int i = 0; i < managerDetails.size(); i++) {
            leftGBC.gridx = 0;
            leftGBC.gridy = i;
            leftPanel.add(managerDetails.get(i), leftGBC);
        }

        // Right Panel -> Standings and Top Scorers

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new MyColor().getBackgroundColor());
        rightPanel.setLayout(new GridBagLayout());

        //        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridLayout(1, 2));
        wrapper.add(leftPanel);
        wrapper.add(rightPanel);
        wrapper.setBorder(new EmptyBorder(100, 100, 100, 100));
        wrapper.setBackground(new MyColor().getBackgroundColor());

        //        Scrollable Wrapper
        JScrollPane scrollable = new JScrollPane(wrapper);


//        Frame
        this.setTitle("Dashboard");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.add(scrollable);
        this.setIconImage(new MyImage().getLogo());
        this.setVisible(true);

    }

    public JLabel getLabel(String key, String tooltipText, String iconPath, ResultSet rs) {
        JLabel label = new JLabel();
        try {
            label.setText(rs.getString(key));
            label.setFont(new MyFont().getFontPrimary().deriveFont(25f));
            label.setForeground(new MyColor().getTextColor());
            label.setToolTipText(tooltipText);
            BufferedImage image = null;
            try {
                image = ImageIO.read(getClass().getResource("/resources/images/" + iconPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            label.setIcon(new ImageIcon(image.getScaledInstance(30, 30,
                    Image.SCALE_SMOOTH)));
            Border margin = new EmptyBorder(10,0,10,0);
            label.setBorder(new CompoundBorder(label.getBorder(),margin));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return label;
    }

    public ArrayList<JLabel> getManagerDetails(String username) {

        ArrayList<JLabel> managerDetails = new ArrayList<JLabel>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement("select * from managers where username=?");
            pst.setString(1, "kloppjurgen");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
//                Manager ID
                JLabel labelMangerID = getLabel("manager_id","Manager ID","icon_id.png",rs);
                managerDetails.add(labelMangerID);

//                Manager Name
                JLabel labelName = getLabel("name","Manager Name","icon_name.png",rs);
                managerDetails.add(labelName);

//                Country
                JLabel labelCountry = getLabel("country","Country","icon_country.png",rs);
                managerDetails.add(labelCountry);

//                Age
                JLabel labelAge = getLabel("age","Age","icon_age.png",rs);
                managerDetails.add(labelAge);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return managerDetails;
    }

}
