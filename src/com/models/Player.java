package com.models;

import java.math.BigInteger;

public class Player {
    private BigInteger playerID;
    private Team team;
    private String name;
    private int shirtNumber;
    private String country;
    private String position;
    private int age;
    private int matchesPlayed;
    private int goalsScored;
    private int yellowCards;
    private int redCards;


    public Player(BigInteger playerID, Team team, String name, String position) {
        this.playerID = playerID;
        this.team = team;
        this.name = name;
        this.position = position;
    }

    public Player(BigInteger playerID, Team team, String name, int shirtNumber, String country, String position, int age, int matchesPlayed, int goalsScored, int yellowCards, int redCards) {
        this.playerID = playerID;
        this.team = team;
        this.name = name;
        this.shirtNumber = shirtNumber;
        this.country = country;
        this.position = position;
        this.age = age;
        this.matchesPlayed = matchesPlayed;
        this.goalsScored = goalsScored;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    public BigInteger getPlayerID() {
        return playerID;
    }

    public void setPlayerID(BigInteger playerID) {
        this.playerID = playerID;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    @Override
    public String toString() {
        return getName();
    }
}

