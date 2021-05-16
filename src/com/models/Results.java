package com.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Results {
    private String date;
    private String time;
    private String homeTeam;
    private String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    public Results(String date, String time, String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
        this.date = date;
        this.time = time;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    @Override
    public String toString() {
        return "Results{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                '}';
    }

    public static ArrayList<Results> getFinishedMatches() {
        ArrayList<Results> finishedmatches = new ArrayList<Results>();
        Results ls;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement("select date,time,homeTeam,awayTeam,hometeamScore,awayTeamscore from finishedmatches;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ls = new Results(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6)
                );
                finishedmatches.add(ls);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return finishedmatches;
    }
}
