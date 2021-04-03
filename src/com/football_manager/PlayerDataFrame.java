package com.football_manager;

import com.components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayerDataFrame extends JDialog implements ActionListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    JButton transferBtn;
    JButton closeBtn;
    ArrayList<JLabel> playerDataLabels;

    public PlayerDataFrame(JFrame owner, BigInteger playerID) {

        Player player = getPlayerData(playerID
        );

        // Team Logo
        JLabel teamLabel = new JLabel();
        teamLabel.setText(player.team);
        teamLabel.setHorizontalTextPosition(JLabel.CENTER);
        teamLabel.setVerticalTextPosition(JLabel.BOTTOM);
        teamLabel.setForeground(myColor.getTextColor());
        teamLabel.setFont(myFont.getFontPrimary().deriveFont(20f));
        teamLabel.setIcon(new ImageIcon(new MyImage().getImageFromURL(player.badge)));
        teamLabel.setIconTextGap(25);

        // Button
        transferBtn = new MyButton("Transfer");
        transferBtn.addActionListener(this);
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == transferBtn) {
                    owner.setEnabled(true);
                    dispose();
                }
            }
        });
        closeBtn = new MyButton("Close");
        closeBtn.addActionListener(this);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == closeBtn) {
                    owner.setEnabled(true);
                    dispose();
                }
            }
        });

        // Data
        playerDataLabels = new ArrayList<>();
        playerDataLabels.add(new MyDataLabel(player.player_id.toString(), "/resources/images/icon_id.png", "Player ID"));
        playerDataLabels.add(new MyDataLabel(player.name, "/resources/images/icon_name.png", "Player Name"));
        playerDataLabels.add(new MyDataLabel(player.country, "/resources/images/icon_country.png", "Country"));
        playerDataLabels.add(new MyDataLabel(player.position, "/resources/images/icon_position.png", "Position"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.shirt_number), "/resources/images/icon_shirt_number.png", "Shirt Number"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.age), "/resources/images/icon_age.png", "Age"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.matches_played), "/resources/images/icon_matches_played.png", "Matches Played"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.goals_scored), "/resources/images/icon_goals_scored.png", "Goals Scored"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.yellow_cards), "/resources/images/icon_yellow_card.png", "Yellow Cards"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.red_cards), "/resources/images/icon_red_card.png", "Red Cards"));


        //  Team Logo Panel
        JPanel teamLogoPanel = new JPanel();
        teamLogoPanel.setBackground(myColor.getBackgroundColor());
        teamLogoPanel.add(teamLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(myColor.getBackgroundColor());
        GridLayout buttonPanelLayout = new GridLayout(2,1);
        buttonPanelLayout.setVgap(20);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanel.add(transferBtn);
        buttonPanel.add(closeBtn);

        // Team and Button Panel
        JPanel teamButtonPanel = new JPanel();
        teamButtonPanel.setBackground(myColor.getBackgroundColor());
        GridLayout teamButtonPanelLayout = new GridLayout(2,1);
        teamButtonPanelLayout.setVgap(50);
        teamButtonPanel.setLayout(teamButtonPanelLayout);
        teamButtonPanel.add(teamLogoPanel);
        teamButtonPanel.add(buttonPanel);

        // Data Panel
        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(myColor.getBackgroundColor());
        dataPanel.setLayout(new GridLayout(10,1));

        for(int i=0;i<playerDataLabels.size();i++) {
            dataPanel.add(playerDataLabels.get(i));
        }


        //        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setBackground(myColor.getBackgroundColor());
        wrapper.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Wrapper Layout
        wrapper.setLayout(new GridBagLayout());
        GridBagConstraints wrapperGBC = new GridBagConstraints();

        wrapperGBC.insets = new Insets(20, 50, 20, 50);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(dataPanel, wrapperGBC);

        wrapperGBC.gridx = 1;
        wrapperGBC.gridy = 0;
        wrapper.add(teamButtonPanel, wrapperGBC);


        // Dialog
        this.setTitle(player.name);
        this.getContentPane().setBackground(myColor.getBackgroundColor());
        this.add(wrapper);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setIconImage(new MyImage().getLogo());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    protected class Player {
        BigInteger player_id;
        String name;
        int shirt_number;
        String country;
        String position;
        int age;
        int matches_played;
        int goals_scored;
        int yellow_cards;
        int red_cards;
        String team;
        String badge;

        Player(
                BigInteger _player_id,
                String _name,
                int _shirt_number,
                String _country,
                String _position,
                int _age,
                int _matches_played,
                int _goals_scored,
                int _yellow_cards,
                int _red_cards,
                String _team,
                String _badge
        ) {
            player_id = _player_id;
            name = _name;
            shirt_number = _shirt_number;
            country = _country;
            position = _position;
            age = _age;
            matches_played = _matches_played;
            goals_scored = _goals_scored;
            yellow_cards = _yellow_cards;
            red_cards = _red_cards;
            team = _team;
            badge = _badge;
        }

        @Override
        public String toString() {
            return player_id + " - " + name + " - " + shirt_number + " - " + country + " - " + position + " - " + age + " - " + matches_played + " - " + goals_scored + " - " + yellow_cards + " - " + red_cards + " - " + team + " - " + badge;
        }
    }

    public JLabel getPlayerDataLabel(String text, String iconPath, String tooltipText) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setIcon(new ImageIcon(new MyImage().getImage(iconPath).getScaledInstance(30, 30, Image.SCALE_FAST)));
        label.setFont(myFont.getFontPrimary().deriveFont(18f));
        label.setForeground(myColor.getTextColor());
        label.setToolTipText(tooltipText);
        return label;
    }

    public Player getPlayerData(BigInteger playerID) {

        Player player = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            String sql = "select p.player_id,p.name,t.name as team,t.badge as badge,p.country,p.position,p.shirt_number,p.age,p.matches_played,p.goals_scored,p.red_cards,p.yellow_cards from players as p inner join teams as t on p.team_id=t.team_id and p.player_id=?;";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setLong(1, playerID.longValue());

            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                JOptionPane.showMessageDialog(this, "No player found!", "No player found", JOptionPane.WARNING_MESSAGE);
            } else {// If resultset is not empty
                while (rs.next()) {
                    BigInteger player_id = BigInteger.valueOf(rs.getLong("player_id"));
                    String name = rs.getString("name");
                    String team = rs.getString("team");
                    String badge = rs.getString("badge");
                    String country = rs.getString("country");
                    String position = rs.getString("position");
                    int shirt_number = rs.getInt("shirt_number");
                    int age = rs.getInt("age");
                    int matches_played = rs.getInt("matches_played");
                    int goals_scored = rs.getInt("goals_scored");
                    int red_cards = rs.getInt("red_cards");
                    int yellow_cards = rs.getInt("yellow_cards");

                    player = new Player(player_id, name, shirt_number, country, position, age, matches_played, goals_scored, yellow_cards, red_cards, team, badge);

                    System.out.println(player);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            return player;
        }
    }

}
