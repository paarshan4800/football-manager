package com.models;

import java.math.BigInteger;

public class Team {
    private BigInteger teamID;
    private Manager manager;
    private String name;
    private String badge;

    public Team(BigInteger teamID, Manager manager, String name, String badge) {
        this.teamID = teamID;
        this.manager = manager;
        this.name = name;
        this.badge = badge;
    }

    public BigInteger getTeamID() {
        return teamID;
    }

    public void setTeamID(BigInteger teamID) {
        this.teamID = teamID;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    @Override
    public String toString() {
        return getName();
    }
}
