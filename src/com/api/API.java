package com.api;

import setup.Fixtures;
import setup.LeagueStandings;
import setup.Results;
import setup.TopScorers;

public class API {

    private final static String API_FOOTBALL_DATA_ORG_API_KEY = "";
    private final static String API_FOOTBALL_COM_API_KEY = "";



    public static String getApiFootballDataOrgApiKey() {
        return API_FOOTBALL_DATA_ORG_API_KEY;
    }

    public static String getApiFootballComApiKey() {
        return API_FOOTBALL_COM_API_KEY;
    }

    public void getCurrentLeagueStanding() {
        // Truncate
        UpdateAPI.truncateLeagueStandings();
        // Insert
        new LeagueStandings();

    }

    public void getTopScorers() {

        // Truncate
        UpdateAPI.truncateTopScorers();
        // Insert
        new TopScorers();

    }

    public void getResults() {

        // Truncate
        UpdateAPI.truncateResults();
        // Insert
        new Results();
    }

    public void getFixtures() {
        // Truncate
        UpdateAPI.truncateFixtures();
        // Insert
        new Fixtures();
    }


}
