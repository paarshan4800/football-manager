package com.models;

import com.models.Player;
import com.models.Team;

import java.math.BigInteger;

public class LoanTransfer extends Transfer {
    private int wage_split;
    private int duration_inMonths;

    public LoanTransfer(Player player, Team fromTeam, Team toTeam, int status, int wage_split, int duration_inMonths) {
        super(player, fromTeam, toTeam, status);
        this.wage_split = wage_split;
        this.duration_inMonths = duration_inMonths;
    }

    public LoanTransfer(Player player, Team fromTeam, Team toTeam, BigInteger transferID, int status) {
        super(player, fromTeam, toTeam, transferID, status);
    }

    public LoanTransfer(Player player, Team fromTeam, Team toTeam, BigInteger transferID, int status, int wage_split, int duration_inMonths) {
        super(player, fromTeam, toTeam, transferID, status);
        this.wage_split = wage_split;
        this.duration_inMonths = duration_inMonths;
    }

    public int getWage_split() {
        return wage_split;
    }

    public void setWage_split(int wage_split) {
        this.wage_split = wage_split;
    }

    public int getDuration_inMonths() {
        return duration_inMonths;
    }

    public void setDuration_inMonths(int duration_inMonths) {
        this.duration_inMonths = duration_inMonths;
    }
}
