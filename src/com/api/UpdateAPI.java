package com.api;

import com.models.LeagueStandings;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.sql.SQL.getDBConnection;

public class UpdateAPI {


    public static String getLastUpdatedDateTime(String tableName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();
            PreparedStatement pst = con.prepareStatement("select date_time from lastupdated where table_name =?");

            pst.setString(1, tableName);
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return String.valueOf(LocalDateTime.now());
            } else {// If resultset is not empty
                while (rs.next()) {
                    return rs.getString("date_time");
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public static void updateLastUpdatedDateTime(String tableName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();
            PreparedStatement pst = con.prepareStatement("update lastupdated set date_time=? where table_name =?");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
            String currDateTime = LocalDateTime.now().format(dateTimeFormatter);

            pst.setString(1, currDateTime);
            pst.setString(2, tableName);

            int rowsAffected = pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public static void truncateLeagueStandings() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

            PreparedStatement pst = con.prepareStatement("truncate table standings;");
            int rowsAffected = pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
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
