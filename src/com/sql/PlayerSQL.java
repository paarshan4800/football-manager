package com.sql;

import com.models.Player;
import com.models.Team;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
