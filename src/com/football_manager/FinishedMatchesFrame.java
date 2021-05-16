package com.football_manager;

import com.components.*;
import com.models.LeagueStandings;
import com.models.Results;

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
        Object[] rowdata=new Object[100];

        ArrayList<Results> results=getFinishedMatches();
        wrapper.setLayout(new GridLayout((results.size()/2)+1,2,20,20));

        for (int i=0;i<results.size();i++) {
            rowdata[0] = results.get(i).getDate();
            rowdata[1] = results.get(i).getTime();
            rowdata[2] = results.get(i).getHomeTeam();
            rowdata[3] = results.get(i).getAwayTeam();
            rowdata[4] = results.get(i).getHomeTeamScore();
            rowdata[5] = results.get(i).getAwayTeamScore();
            wrapper.add(createPanel(results.get(i)));
        }

        this.add(wrapper);
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);
        this.setVisible(true);

    }
    public static ArrayList<Results> getFinishedMatches() {
        ArrayList<Results> finishedmatches = new ArrayList<Results>();
        Results ls;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "14valentine");

            PreparedStatement pst = con.prepareStatement("select date,time,homeTeam,awayTeam,hometeamScore,awayTeamscore from finishedmatches;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ls = new Results(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6)
                );
                finishedmatches.add(ls);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return finishedmatches;
    }

    public JPanel createPanel(Results results){

        JPanel matches_panel=new JPanel();

        //Date label
        JLabel date = new JLabel();
        date.setText(results.getDate());
        date.setFont(myFont.getFontPrimary());
        date.setForeground(myColor.getTextColor());
        //time label
        JLabel time=new JLabel();
        time.setText(results.getTime());
        time.setFont(myFont.getFontPrimary());
        time.setForeground(myColor.getTextColor());
        //Home team label
        JLabel home_team_label = new JLabel();
        home_team_label.setText(results.getHomeTeam());
        /*if(home_team_label.getText().contains("Manchester City")) {
            home_team_label.setIcon(new ImageIcon(myImage.getImageFromURL("https://apiv2.apifootball.com/badges/2626_manchester-city.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        }*/
        home_team_label.setFont(myFont.getFontPrimary());
        home_team_label.setForeground(myColor.getTextColor());
        //Away team label
        JLabel away_team_label = new JLabel();
        away_team_label.setText(results.getAwayTeam());
        away_team_label.setFont(myFont.getFontPrimary());
        away_team_label.setForeground(myColor.getTextColor());
        //Home team score
        JLabel home_team_score=new JLabel();
        home_team_score.setText(String.valueOf(results.getHomeTeamScore()));
        home_team_score.setFont(myFont.getFontPrimary());
        home_team_score.setForeground(myColor.getTextColor());
        //Away team score
        JLabel away_team_score=new JLabel();
        away_team_score.setText(String.valueOf(results.getAwayTeamScore()));
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
        gbc.anchor=GridBagConstraints.CENTER;

        gbc.gridx=2;
        gbc.gridy=1;
        matches_panel.add(time,gbc);
        matches_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        matches_panel.setBackground(myColor.getBoxColor());


        return  matches_panel;
    }


}

