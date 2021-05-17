package com.football_manager.matches_frame;

import com.components.*;
import com.components.menu.MyMenuBar;
import com.models.LeagueStandings;
import com.models.Manager;
import com.models.Results;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class FinishedMatchesFrame extends MatchesDataFrame {
    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    MyImage myImage = new MyImage();

    public FinishedMatchesFrame(Manager manager) {

        super("Results", manager, "results");

        ArrayList<Results> results = Results.getFinishedMatches();
        matchesPanel.setLayout(new GridLayout((results.size() / 2) + 1, 2, 20, 20));

        for (int i = 0; i < results.size(); i++) {
            matchesPanel.add(createPanel(results.get(i)));
        }

        this.setVisible(true);
        setLocationRelativeTo(null);

    }

    public JPanel createPanel(Results results) {

        JPanel matches_panel = new JPanel();

        //Date label
        JLabel date = new JLabel();
        date.setText(results.getDate());
        date.setFont(myFont.getFontPrimary());
        date.setForeground(myColor.getTextColor());
        //time label
        JLabel time = new JLabel();
        time.setText(results.getTime());
        time.setFont(myFont.getFontPrimary());
        time.setForeground(myColor.getTextColor());
        //Home team label
        JLabel home_team_label = new JLabel();
        home_team_label.setText(results.getHomeTeam());
        if (teamBadgeMapping.containsKey(results.getHomeTeam())) {
            home_team_label.setIcon(new ImageIcon(myImage.getImage(teamBadgeMapping.get(results.getHomeTeam())).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            home_team_label.setIconTextGap(10);
        }
        home_team_label.setFont(myFont.getFontPrimary());
        home_team_label.setForeground(myColor.getTextColor());
        //Away team label
        JLabel away_team_label = new JLabel();
        away_team_label.setText(results.getAwayTeam());
        if (teamBadgeMapping.containsKey(results.getAwayTeam())) {
            away_team_label.setIcon(new ImageIcon(myImage.getImage(teamBadgeMapping.get(results.getAwayTeam())).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            away_team_label.setIconTextGap(10);
        }
        away_team_label.setFont(myFont.getFontPrimary());
        away_team_label.setForeground(myColor.getTextColor());
        //Home team score
        JLabel home_team_score = new JLabel();
        home_team_score.setText(String.valueOf(results.getHomeTeamScore()));
        home_team_score.setFont(myFont.getFontPrimary());
        home_team_score.setForeground(myColor.getTextColor());
        //Away team score
        JLabel away_team_score = new JLabel();
        away_team_score.setText(String.valueOf(results.getAwayTeamScore()));
        away_team_score.setFont(myFont.getFontPrimary());
        away_team_score.setForeground(myColor.getTextColor());

        matches_panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(20, 0, 20, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        matches_panel.add(home_team_label, gbc);
        gbc.insets = new Insets(20, 0, 20, 50);
        gbc.gridx = 0;
        gbc.gridy = 1;
        matches_panel.add(away_team_label, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        matches_panel.add(home_team_score, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        matches_panel.add(away_team_score, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.gridy = 0;
        matches_panel.add(date, gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 2;
        gbc.gridy = 1;
        matches_panel.add(time, gbc);
        matches_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        matches_panel.setBackground(myColor.getBoxColor());


        return matches_panel;
    }


}

