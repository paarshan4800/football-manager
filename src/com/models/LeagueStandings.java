package com.models;

import java.math.BigInteger;

public class LeagueStandings {

    private BigInteger team_id;
    private String team_name;
    private String promotion_status;
    private int position;
    private int matches_played;
    private int matches_won;
    private int matches_drawn;
    private int matches_lost;
    private int goals_for;
    private int goals_against;
    private int points;

    public LeagueStandings(BigInteger team_id, int position, String team_name, int matches_played, int matches_won, int matches_drawn, int matches_lost, int goals_for, int goals_against, int points) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.position = position;
        this.matches_played = matches_played;
        this.matches_won = matches_won;
        this.matches_drawn = matches_drawn;
        this.matches_lost = matches_lost;
        this.goals_for = goals_for;
        this.goals_against = goals_against;
        this.points = points;
    }

    public BigInteger getTeam_id() {
        return team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public int getPosition() {
        return position;
    }

    public int getMatches_played() {
        return matches_played;
    }

    public int getMatches_won() {
        return matches_won;
    }

    public int getMatches_drawn() {
        return matches_drawn;
    }

    public int getMatches_lost() {
        return matches_lost;
    }

    public int getGoals_for() {
        return goals_for;
    }

    public int getGoals_against() {
        return goals_against;
    }

    public int getPoints() {
        return points;
    }

}
