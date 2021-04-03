package com.football_manager;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;

import javax.swing.*;
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

public class PlayerDataFrame extends JDialog implements ActionListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    JButton transferBtn;
    JButton closeBtn;

    public PlayerDataFrame(String playerID) {

        Player player = getPlayerData(BigInteger.valueOf(46307885));

        // Team Logo
        JLabel teamLabel = new JLabel();
        teamLabel.setText("Manchester United");
        teamLabel.setHorizontalTextPosition(JLabel.CENTER);
        teamLabel.setVerticalAlignment(JLabel.BOTTOM);
        teamLabel.setForeground(myColor.getTextColor());
        teamLabel.setFont(myFont.getFontPrimary().deriveFont(20f));

        // Button
        transferBtn = new MyButton("Transfer");
        transferBtn.addActionListener(this);
        closeBtn = new MyButton("Close");
        closeBtn.addActionListener(this);

        // Data
        JLabel playerIDLabel = getPlayerDataLabel(playerID.toString(),"/resources/images/icon_id.png","Player ID");

        teamLabel.setIcon(new ImageIcon(new MyImage().getImageFromURL("https://apiv2.apifootball.com/badges/2627_manchester-united.png")));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        buttonPanel.add(transferBtn);
        buttonPanel.add(closeBtn);

        // Data Panel
        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(myColor.getBackgroundColor());
        dataPanel.add(playerIDLabel);

        //        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setBackground(myColor.getBackgroundColor());
        wrapper.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Wrapper Layout
        wrapper.setLayout(new GridBagLayout());
        GridBagConstraints wrapperGBC = new GridBagConstraints();

        wrapperGBC.insets = new Insets(20, 0, 20, 0);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(dataPanel, wrapperGBC);

        wrapperGBC.gridx = 1;
        wrapperGBC.gridy = 0;
        wrapper.add(teamLabel, wrapperGBC);

        wrapperGBC.gridx = 1;
        wrapperGBC.gridy = 1;
        wrapper.add(buttonPanel, wrapperGBC);

        // Dialog
        this.setTitle("Player");
        this.getContentPane().setBackground(myColor.getBackgroundColor());
        this.add(wrapper);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setIconImage(new MyImage().getLogo());
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class Player {
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
