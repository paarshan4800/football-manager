package com.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Fixtures {
    private String date;
    private String time;
    private String homeTeam;
    private String awayTeam;

    public Fixtures(String date, String time, String homeTeam, String awayTeam) {
        this.date = date;
        this.time = time;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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

    @Override
    public String toString() {
        return "Fixtures{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                '}';
    }

    public static ArrayList<Fixtures> getUpcomingMatches() {
        ArrayList<Fixtures> upcomingmatches = new ArrayList<Fixtures>();
        Fixtures ls;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement("select date,time,homeTeam,awayTeam from upcomingmatches;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ls = new Fixtures(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)

                );
                upcomingmatches.add(ls);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return upcomingmatches;
    }
}
