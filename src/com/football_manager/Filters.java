package com.football_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Filters {

    // Position
    HashMap<String, Boolean> position;

    // Age
    HashMap<String, Integer> age = new HashMap<>();

    // Teams
    HashMap<String, Boolean> team;

    // Filters
    HashMap<String, Boolean> country;

    public Filters() {

        // Initialization for positions
        position = getFilterData("select distinct position from players order by position asc", "position");
//        for (int i = 0; i < positions.length; i++) {
//            position.put(positions[i], true);
//        }

        // Initialization for age
        age.put("minimumAge", 18);
        age.put("maximumAge", 35);

        // Initialization for teams
        team = getFilterData("select name from teams order by name asc;", "name");

        // Initialization for country
        country = getFilterData("select distinct country from players order by country asc;", "country");

    }

    private HashMap<String, Boolean> getFilterData(String sql, String columnName) {
        HashMap<String, Boolean> hash = new HashMap<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                System.out.println("EMPTY,Hello");
            } else {// If resultset is not empty
//                boolean firstTeam = true;
                while (rs.next()) {
                    String team = rs.getString(columnName);

//                    if(firstTeam) {
//                        hash.put(team,true);
//                        firstTeam = false;
//                    }
//                    else {
                    hash.put(team, false);
//                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            return hash;
        }
    }

}
