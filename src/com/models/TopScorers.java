package com.models;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static com.sql.SQL.getDBConnection;

public class TopScorers {

    private BigInteger player_id;
    private int goals_scored;
    private BigInteger team_id;
    private String name;


    public TopScorers(String name, int goals_scored) {
        this.goals_scored = goals_scored;
        this.name = name;
    }

    public TopScorers(BigInteger player_id, int goals_scored, BigInteger team_id) {
        this.player_id = player_id;
        this.goals_scored = goals_scored;
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public BigInteger getPlayer_id() {
        return player_id;
    }

    public BigInteger getTeam_id() {
        return team_id;
    }

    public int getGoals_scored() {
        return goals_scored;
    }

    public static ArrayList<TopScorers> getTopScorers() {
        ArrayList<TopScorers> data = new ArrayList<TopScorers>();
        TopScorers ts;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

            PreparedStatement pst = con.prepareStatement("select players.name,topScorers.goals_scored from players inner join topScorers on players.player_id=topScorers.player_id order by goals_scored desc limit 20;");
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
}
