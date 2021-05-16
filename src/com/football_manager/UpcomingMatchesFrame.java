package com.football_manager;

import com.components.*;
import com.models.Fixtures;
import com.models.Results;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class UpcomingMatchesFrame extends JFrame {
    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    MyImage myImage=new MyImage();

    public UpcomingMatchesFrame() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setTitle("Upcoming Matches");
        this.setIconImage(new MyImage().getLogo());
        JPanel wrapper=new JPanel();
        wrapper.setBackground(myColor.getBackgroundColor());
        wrapper.setBorder(new EmptyBorder(50,50,50,50));
        wrapper.setOpaque(true);
        wrapper.setLayout(new GridLayout(8,2,20,20));
        Object[] rowdata=new Object[100];

        ArrayList<Fixtures> fixtures=getUpcomingMatches();
        wrapper.setLayout(new GridLayout((fixtures.size()/2)+1,2,20,20));

        for (int i=0;i<fixtures.size();i++) {
            rowdata[0] = fixtures.get(i).getDate();
            rowdata[1] = fixtures.get(i).getTime();
            rowdata[2] = fixtures.get(i).getHomeTeam();
            rowdata[3] = fixtures.get(i).getAwayTeam();

            wrapper.add(createPanel(fixtures.get(i)));
        }

        this.add(wrapper);
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);
        this.setVisible(true);

    }
    public static ArrayList<Fixtures> getUpcomingMatches() {
        ArrayList<Fixtures> upcomingmatches = new ArrayList<Fixtures>();
        Fixtures ls;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "14valentine");

            PreparedStatement pst = con.prepareStatement("select date,time,homeTeam,awayTeam from upcomingmatches;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ls = new Fixtures(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)

                );
                upcomingmatches.add(ls);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return upcomingmatches;
    }
    public JPanel createPanel(Fixtures fixtures){

        JPanel matches_panel=new JPanel();

        //Date label
        JLabel date = new JLabel();
        date.setText(fixtures.getDate());
        date.setFont(myFont.getFontPrimary());
        date.setForeground(myColor.getTextColor());
        //time label
        JLabel time=new JLabel();
        time.setText(fixtures.getTime());
        time.setFont(myFont.getFontPrimary());
        time.setForeground(myColor.getTextColor());
        //Home team label
        JLabel home_team_label = new JLabel();
        home_team_label.setText(fixtures.getHomeTeam());
        //home_team_label.setIcon(new ImageIcon(myImage.getImageFromURL("https://apiv2.apifootball.com/badges/2626_manchester-city.png").getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        home_team_label.setFont(myFont.getFontPrimary());
        home_team_label.setForeground(myColor.getTextColor());
        //Away team label
        JLabel away_team_label = new JLabel();
        away_team_label.setText(fixtures.getAwayTeam());
        away_team_label.setFont(myFont.getFontPrimary());
        away_team_label.setForeground(myColor.getTextColor());

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
        gbc.anchor=GridBagConstraints.EAST;
        gbc.gridx=1;
        gbc.gridy=0;
        matches_panel.add(date,gbc);
        gbc.gridx=1;
        gbc.gridy=1;
        matches_panel.add(time,gbc);
        matches_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        matches_panel.setBackground(myColor.getBoxColor());


        return  matches_panel;
    }



}
