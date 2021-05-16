package com.sql;

import com.models.TopScorers;
import com.models.LeagueStandings;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class UpdateAPI {

    public void storeLeagueStandings(ArrayList<LeagueStandings> leagueStandings) {
        for (LeagueStandings leagueStanding : leagueStandings) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                        "PaarShanDB0408");

                PreparedStatement pst = con.prepareStatement("update standings set position=?,matches_played=?,matches_won=?,matches_drawn=?,matches_lost=?,goals_for=?,goals_against=?,points=? where team_id=?");
                pst.setInt(1, leagueStanding.getPosition());
                pst.setInt(2, leagueStanding.getMatches_played());
                pst.setInt(3, leagueStanding.getMatches_won());
                pst.setInt(4, leagueStanding.getMatches_drawn());
                pst.setInt(5, leagueStanding.getMatches_lost());
                pst.setInt(6, leagueStanding.getGoals_for());
                pst.setInt(7, leagueStanding.getGoals_against());
                pst.setInt(8, leagueStanding.getPoints());
                pst.setBigDecimal(9, new BigDecimal(leagueStanding.getTeam_id()));

                int rowsAffected = pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

    }

    public void storeTopScorers(ArrayList<TopScorers> topScorers) {
        for (TopScorers topScorer : topScorers) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                        "PaarShanDB0408");

                PreparedStatement pst = con.prepareStatement("update topscorers set team_id=?,goals_scored=? where player_id=?");
                pst.setBigDecimal(1, new BigDecimal(topScorer.getTeam_id()));
                pst.setInt(2, topScorer.getGoals_scored());
                pst.setBigDecimal(3, new BigDecimal(topScorer.getPlayer_id()));


                int rowsAffected = pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

}
