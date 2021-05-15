package com.sql;

import com.models.LeagueStandings;
import com.models.Manager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StoreAPI {

    public void storeLeagueStandings(ArrayList<LeagueStandings> leagueStandings) {
        for (LeagueStandings leagueStanding : leagueStandings) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                        "PaarShanDB0408");

                PreparedStatement pst = con.prepareStatement("insert into standings (team_id,position,matches_played,matches_won,matches_drawn,matches_lost,goals_for,goals_against,points) values (?,?,?,?,?,?,?,?,?)");
                pst.setBigDecimal(1, new BigDecimal(leagueStanding.getTeam_id()));
                pst.setInt(2, leagueStanding.getPosition());
                pst.setInt(3, leagueStanding.getMatches_played());
                pst.setInt(4, leagueStanding.getMatches_won());
                pst.setInt(5, leagueStanding.getMatches_drawn());
                pst.setInt(6, leagueStanding.getMatches_lost());
                pst.setInt(7, leagueStanding.getGoals_for());
                pst.setInt(8, leagueStanding.getGoals_against());
                pst.setInt(9, leagueStanding.getPosition());

                int rowsAffected = pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

    }

}