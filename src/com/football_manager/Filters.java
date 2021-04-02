package com.football_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Filters {

    // Position
    HashMap<String, Boolean> position = new HashMap<>();
    final String[] positions = {"Forwards", "Midfielders", "Defenders", "Goalkeepers"};

    // Age
    HashMap<String, Integer> age = new HashMap<>();

    // Teams
    HashMap<String, Boolean> team = new HashMap<>();

    public Filters() {

        // Initialization for positions
        for (int i = 0; i < positions.length; i++) {
            position.put(positions[i], true);
        }

        // Initialization for age
        age.put("minimumAge", 18);
        age.put("maximumAge", 35);

        // Initialization for teams
        team = getAllTeams();
    }

    private HashMap<String, Boolean> getAllTeams() {

        HashMap<String, Boolean> teamHash = new HashMap<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");


            String sql = "select name from teams order by name asc;";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                System.out.println("EMPTY");
            } else {// If resultset is not empty
                boolean firstTeam = true;
                while (rs.next()) {
                    String team = rs.getString("name");

                    if(firstTeam) {
                        teamHash.put(team,true);
                        firstTeam = false;
                    }
                    else {
                        teamHash.put(team,false);
                    }


                }
            }
        } catch (Exception ex) {
            System.out.println("Hello");
            System.out.println(ex);
        } finally {
            return teamHash;
        }
    }


}