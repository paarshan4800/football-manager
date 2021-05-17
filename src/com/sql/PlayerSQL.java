package com.sql;

import com.models.Player;
import com.models.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

import static com.sql.SQL.getDBConnection;

public class PlayerSQL {
    public PlayerSQL() {
    }

    // Get a player object given his player id
    public static Player getPlayerGivenPlayerID(BigInteger playerID) {
        Player player = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = "select player_id,team_id,name,shirt_number,country,position,age,matches_played,goals_scored,yellow_cards,red_cards from players where player_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, new BigDecimal(playerID));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return player;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Team team = TeamSQL.getTeamGivenTeamID(rs.getBigDecimal("team_id").toBigInteger());
                    player = new Player(
                            rs.getBigDecimal("player_id").toBigInteger(),
                            team,
                            rs.getString("name"),
                            rs.getInt("shirt_number"),
                            rs.getString("country"),
                            rs.getString("position"),
                            rs.getInt("age"),
                            rs.getInt("matches_played"),
                            rs.getInt("goals_scored"),
                            rs.getInt("yellow_cards"),
                            rs.getInt("red_cards")
                    );
                    return player;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return player;
    }

    // Get all players given team id
    public static ArrayList<Player> getAllPlayersGivenTeamID(BigInteger teamID) {
        ArrayList<Player> players = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = "select player_id from players where team_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, new BigDecimal(teamID));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return players;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Player player = getPlayerGivenPlayerID(rs.getBigDecimal("player_id").toBigInteger());
                    players.add(player);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return players;
    }


    public static DefaultTableModel getAllPlayersTableModel(
            StringBuilder positionIN,
            StringBuilder teamIN,
            StringBuilder countryIN,
            int minAge,
            int maxAge,
            JFrame parentComponent
    ) {
        DefaultTableModel tableModel = new DefaultTableModel(0, 0) { //create default table model
            // Override default editable cell to uneditable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columnHeaders = {"Player ID", "Name", "Team", "Country", "Position", "Age"}; //column names
        tableModel.setColumnIdentifiers(columnHeaders);// set column names to table model

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

            String sql = "select p.player_id,p.name,t.name as team,p.country,p.position,p.shirt_number,p.age,p.matches_played,p.goals_scored,p.red_cards,p.yellow_cards from players as p inner join teams as t on p.team_id=t.team_id and p.position in (position) and t.name in (team) and p.country in (country) and p.age >= ? and p.age <= ? order by p.name asc;";
            sql = sql.replace("(position)", "(" + positionIN + ")"); // replace (position) with formatted string for SQL IN
            sql = sql.replace("(team)", "(" + teamIN + ")"); // replace (team) with formatted string for SQL IN
            sql = sql.replace("(country)", "(" + countryIN + ")"); // replace (country) with formatted string for SQL IN

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, minAge);
            pst.setInt(2, maxAge);

            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                JOptionPane.showMessageDialog(parentComponent, "No players found!", "No players found", JOptionPane.WARNING_MESSAGE);
            } else {// If resultset is not empty
                while (rs.next()) {
                    BigInteger player_id = BigInteger.valueOf(rs.getLong("player_id"));
                    String name = rs.getString("name");
                    String team = rs.getString("team");
                    String country = rs.getString("country");
                    String position = rs.getString("position");
                    int age = rs.getInt("age");

                    tableModel.addRow(new Object[]{player_id, name, team, country, position, age});// add row to table model
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tableModel;

    }

}
