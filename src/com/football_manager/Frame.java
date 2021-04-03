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


class Frame extends JFrame {
    //create table model with data
    // table.setModel(model);
    public Frame() {

        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] column_names = {"name", "goals_scored"};
        tableModel.setColumnIdentifiers(column_names);
        Object[] rowData = new Object[2];

        ArrayList<TopScorers> topScorers = getTopScorers();

        for (int i = 0; i < topScorers.size(); i++) {
            rowData[0] = topScorers.get(i).getName();
            rowData[1] = topScorers.get(i).getGoals_scored();
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane);
        
        this.setTitle("TopScorers");
        this.setSize(500, 500);
        this.setVisible(true);
    }

    class TopScorers {
        private String name;
        private int goals_scored;

        public TopScorers(String _name, int _goals_scored) {
            this.name = _name;
            this.goals_scored = _goals_scored;
        }

        public String getName() {
            return this.name;
        }

        public int getGoals_scored() {
            return this.goals_scored;
        }

    }

    private ArrayList<TopScorers> getTopScorers() {
        ArrayList<TopScorers> data = new ArrayList<TopScorers>();
        TopScorers ts;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "14valentine");

            PreparedStatement pst = con.prepareStatement("select name,goals_scored from players order by goals_scored desc;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ts = new TopScorers(
                        rs.getString(1),
                        rs.getInt(2)
                );
                data.add(ts);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return data;
    }

//    public static void main(String args[]) {
//        JTable table = new JTable();
//        DefaultTableModel tableModel = new DefaultTableModel();
//        String[] column_names = {"name", "goals_scored"};
//        tableModel.setColumnIdentifiers(column_names);
//        Object[] rowData = new Object[2];
//
//        ArrayList<TopScorers> topScorers = getTopScorers();
//
//        for (int i = 0; i < getTopScorers().size(); i++) {
//            rowData[0] = getTopScorers().get(i).getName();
//            rowData[1] = getTopScorers().get(i).getGoals_scored();
//            tableModel.addRow(rowData);
//        }
//        table.setModel(tableModel);
//        Frame window = new Frame();
//        window.add(table);
//    }
}








