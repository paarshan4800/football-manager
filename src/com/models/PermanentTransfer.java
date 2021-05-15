package com.models;

import com.models.Player;
import com.models.Team;

import java.math.BigInteger;

public class PermanentTransfer extends Transfer {
    private double transferFee;

    public PermanentTransfer(Player player, Team fromTeam, Team toTeam, int status,double transferFee) {
        super(player, fromTeam, toTeam, status);
        this.transferFee = transferFee;
    }

    public PermanentTransfer(Player player, Team fromTeam, Team toTeam, BigInteger transferID, int status) {
        super(player, fromTeam, toTeam, transferID, status);
    }

    public PermanentTransfer(Player player, Team fromTeam, Team toTeam, BigInteger transferID, int status, double transferFee) {
        super(player, fromTeam, toTeam, transferID, status);
        this.transferFee = transferFee;
    }

    public double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(double transferFee) {
        this.transferFee = transferFee;
    }
}
