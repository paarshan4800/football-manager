package com.models;

import com.models.*;

import java.io.Serializable;
import java.math.BigInteger;

public class Transfer implements Serializable {
    public final static int REQUEST = 1, ACCEPTED = 2, REJECTED = 3;
    private Player player;
    private Team fromTeam;
    private Team toTeam;
    private BigInteger transferID;
    private int status;

    public Transfer(Player player, Team fromTeam, Team toTeam, BigInteger transferID, int status) {
        this.player = player;
        this.fromTeam = fromTeam;
        this.toTeam = toTeam;
        this.transferID = transferID;
        this.status = status;
    }

    public Transfer(Player player, Team fromTeam, Team toTeam, int status) {
        this.player = player;
        this.fromTeam = fromTeam;
        this.toTeam = toTeam;
        this.status = status;
    }

    @Override
    public String toString() {
        return getTransferID() + " - " + getPlayer() + " - " + getFromTeam() + " - " + getToTeam() + " - " + getStatus();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getFromTeam() {
        return fromTeam;
    }

    public void setFromTeam(Team fromTeam) {
        this.fromTeam = fromTeam;
    }

    public Team getToTeam() {
        return toTeam;
    }

    public void setToTeam(Team toTeam) {
        this.toTeam = toTeam;
    }

    public BigInteger getTransferID() {
        return transferID;
    }

    public void setTransferID(BigInteger transferID) {
        this.transferID = transferID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

