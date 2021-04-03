package com.football_manager;

import com.components.MyColor;
import com.components.MyDataLabel;
import com.components.MyFont;
import com.components.MyImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DashboardFrame extends JFrame {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    
    ArrayList<JLabel> managerDataLabels;
    
    public DashboardFrame() {
        
        Manager manager = getManagerDetails("hello");

        // Left Panel -> Details
        managerDataLabels = new ArrayList<>();
        managerDataLabels.add(new MyDataLabel(String.valueOf(manager.manager_id), "/resources/images/icon_id.png", "Manager ID"));
        managerDataLabels.add(new MyDataLabel(manager.name, "/resources/images/icon_name.png", "Name"));
        managerDataLabels.add(new MyDataLabel(manager.country, "/resources/images/icon_country.png", "Country"));
        managerDataLabels.add(new MyDataLabel(String.valueOf(manager.age), "/resources/images/icon_age.png", "Age"));



        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(myColor.getBackgroundColor());
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints leftGBC = new GridBagConstraints();

        for (int i = 0; i < managerDataLabels.size(); i++) {
            leftGBC.gridx = 0;
            leftGBC.gridy = i;
            leftPanel.add(managerDataLabels.get(i), leftGBC);
        }

        // Right Panel -> Standings and Top Scorers

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(myColor.getBackgroundColor());
        rightPanel.setLayout(new GridBagLayout());

        //        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridLayout(1, 2));
        wrapper.add(leftPanel);
        wrapper.add(rightPanel);
        wrapper.setBorder(new EmptyBorder(100, 100, 100, 100));
        wrapper.setBackground(myColor.getBackgroundColor());

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

    private class Manager {

        int manager_id;
        String name;
        String country;
        int age;

        Manager(int _manager_id, String _name, String _country, int _age) {
            manager_id = _manager_id;
            name = _name;
            country = _country;
            age = _age;
        }


    }


    public Manager getManagerDetails(String username) {
        Manager manager = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement("select * from managers where username=?");
            pst.setString(1, "kloppjurgen");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int manager_id = rs.getInt("manager_id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                int age = rs.getInt("age");

                manager = new Manager(manager_id, name, country, age);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return manager;
    }

}
