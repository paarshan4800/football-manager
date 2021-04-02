package com.football_manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import com.components.*;
import org.springframework
        .security
        .crypto
        .bcrypt
        .BCrypt;

public class TopScorers extends JFrame {
    public ArrayList<String> name = new ArrayList<String>();
    public ArrayList<Integer> goals_scored = new ArrayList<Integer>();
    public Object[][] data = new Object[][]{};

    public TopScorers() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "14valentine");

            PreparedStatement pst = con.prepareStatement("select name,goals_scored from players order by goals_scored desc;");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                name.add(rs.getString(1));
                goals_scored.add(rs.getInt(2));

            }

            System.out.println(name);


            //Object[] data={name,goals_scored};
            //JTable jTable=new JTable(rs.next(),column_names);

            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

//create table model with data


        // table.setModel(model);
        JTable table = new JTable(getTableModel());
        this.setTitle("TopScorers");
        this.setSize(500, 500);
        this.setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel(0, 0) {


            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }


        };
        String[] column_names = {"name", "goals_scored"};
        tableModel.setColumnIdentifiers(column_names);


        return tableModel;
    }
}