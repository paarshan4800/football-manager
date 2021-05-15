package com.football_manager;

import com.components.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class FinishedMatchesFrame extends JFrame {
    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    MyImage myImage=new MyImage();

    public FinishedMatchesFrame() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setTitle("Finished Match Results");
        this.setIconImage(new MyImage().getLogo());
        JPanel wrapper=new JPanel();
        wrapper.setBackground(myColor.getBackgroundColor());
        wrapper.setBorder(new EmptyBorder(50,50,50,50));
        wrapper.setOpaque(true);
        wrapper.setLayout(new GridLayout(8,2,20,20));
        for (int i=0;i<15;i++) {
            wrapper.add(createPanel("City", "Everton"));
        }
        this.add(wrapper);
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);
        this.setVisible(true);

    }
    public JPanel createPanel(String hometeam,String awayteam){

        JPanel matches_panel=new JPanel();

        //Date label
        JLabel date = new JLabel("2021-03-01T20:00:00Z");
        String date_string=date.getText();
        date_string=ConverttoDateString(date_string);
        date.setText(date_string);
        date.setFont(myFont.getFontPrimary());
        date.setForeground(myColor.getTextColor());
        //time label
        JLabel time=new JLabel("2021-03-01T20:00:00Z");
        String time_string=time.getText();
        time_string=ConverttoTimeString(time_string);
        time.setText(time_string);
        time.setFont(myFont.getFontPrimary());
        time.setForeground(myColor.getTextColor());
        //Home team label
        JLabel home_team_label = new JLabel("Manchester CIty");
        home_team_label.setIcon(new ImageIcon(myImage.getImageFromURL("https://apiv2.apifootball.com/badges/2626_manchester-city.png").getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        home_team_label.setFont(myFont.getFontPrimary());
        home_team_label.setForeground(myColor.getTextColor());
        //Away team label
        JLabel away_team_label = new JLabel("Everton");
        away_team_label.setFont(myFont.getFontPrimary());
        away_team_label.setForeground(myColor.getTextColor());
        //Home team score
        JLabel home_team_score=new JLabel("1");
        home_team_score.setFont(myFont.getFontPrimary());
        home_team_score.setForeground(myColor.getTextColor());
        //Away team score
        JLabel away_team_score=new JLabel("0");
        away_team_score.setFont(myFont.getFontPrimary());
        away_team_score.setForeground(myColor.getTextColor());

        matches_panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor=GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(20, 0, 20, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        matches_panel.add(home_team_label,gbc);
        gbc.insets = new Insets(20, 0, 20, 50);
        gbc.gridx = 0;
        gbc.gridy = 1;
        matches_panel.add(away_team_label,gbc);
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.gridx=1;
        gbc.gridy=0;
        matches_panel.add(home_team_score,gbc);
        gbc.gridx=1;
        gbc.gridy=1;
        matches_panel.add(away_team_score,gbc);
        gbc.anchor=GridBagConstraints.EAST;
        gbc.gridx=2;
        gbc.gridy=0;
        matches_panel.add(date,gbc);
        gbc.gridx=2;
        gbc.gridy=1;
        matches_panel.add(time,gbc);
        matches_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        matches_panel.setBackground(myColor.getBoxColor());
        //matches_panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //matches_panel.addMouseListener();

        return  matches_panel;
    }
    public String ConverttoDateString(String string){
        string=string.substring(0,10);
        return string;
    }
    public String ConverttoTimeString(String string){
        string=string.substring(11,19);
        return string;
    }

}

