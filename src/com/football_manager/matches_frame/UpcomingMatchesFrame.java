package com.football_manager.matches_frame;

import com.models.Fixtures;
import com.models.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UpcomingMatchesFrame extends MatchesDataFrame {

    public UpcomingMatchesFrame(Manager manager) {

        super("Fixtures", manager,"fixtures");
        ArrayList<Fixtures> fixtures = Fixtures.getUpcomingMatches();

        matchesPanel.setLayout(new GridLayout((fixtures.size() / 2) + 1, 2, 20, 20));
        for (int i = 0; i < fixtures.size(); i++) {
            matchesPanel.add(createPanel(fixtures.get(i)));
        }

        setVisible(true);
        setLocationRelativeTo(null);
    }


    public JPanel createPanel(Fixtures fixtures) {

        JPanel matches_panel = new JPanel();

        //Date label
        JLabel date = new JLabel();
        date.setText(fixtures.getDate());
        date.setFont(myFont.getFontPrimary());
        date.setForeground(myColor.getTextColor());
        //time label
        JLabel time = new JLabel();
        time.setText(fixtures.getTime());
        time.setFont(myFont.getFontPrimary());
        time.setForeground(myColor.getTextColor());
        //Home team label
        JLabel home_team_label = new JLabel();
        home_team_label.setText(fixtures.getHomeTeam());
        if (teamBadgeMapping.containsKey(fixtures.getHomeTeam())) {
            home_team_label.setIcon(new ImageIcon(myImage.getImage(teamBadgeMapping.get(fixtures.getHomeTeam())).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            home_team_label.setIconTextGap(10);
        }
        home_team_label.setFont(myFont.getFontPrimary());
        home_team_label.setForeground(myColor.getTextColor());
        //Away team label
        JLabel away_team_label = new JLabel();
        away_team_label.setText(fixtures.getAwayTeam());
        if (teamBadgeMapping.containsKey(fixtures.getAwayTeam())) {
            away_team_label.setIcon(new ImageIcon(myImage.getImage(teamBadgeMapping.get(fixtures.getAwayTeam())).getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            away_team_label.setIconTextGap(10);
        }
        away_team_label.setFont(myFont.getFontPrimary());
        away_team_label.setForeground(myColor.getTextColor());

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
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        matches_panel.add(date, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        matches_panel.add(time, gbc);
        matches_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        matches_panel.setBackground(myColor.getBoxColor());


        return matches_panel;
    }


}
