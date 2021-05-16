package com.api;

import com.models.LeagueStandings;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import static com.sql.SQL.getDBConnection;

public class UpdateAPI {

    public static void updateLeagueStandings(ArrayList<LeagueStandings> leagueStandings) {
        for (LeagueStandings leagueStanding : leagueStandings) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = getDBConnection();

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

    public static void truncateTopScorers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

            PreparedStatement pst = con.prepareStatement("truncate table topscorers;");
            int rowsAffected = pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void truncateResults() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

            PreparedStatement pst = con.prepareStatement("truncate table finishedmatches;");
            int rowsAffected = pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void truncateFixtures() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

            PreparedStatement pst = con.prepareStatement("truncate table upcomingmatches;");
            int rowsAffected = pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}
