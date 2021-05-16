package com.football_manager;

import java.math.BigInteger;

public class TopScorers {
    private  BigInteger player_id;
    private  int goals_scored;
    private BigInteger team_id;
    public TopScorers(BigInteger player_id, int goals_scored,BigInteger team_id) {
        this.player_id = player_id;
        this.goals_scored = goals_scored;
        this.team_id=team_id;
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
}
