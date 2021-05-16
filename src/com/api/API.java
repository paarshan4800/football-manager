package com.api;

import com.models.LeagueStandings;
import org.json.JSONArray;
import org.json.JSONObject;
import setup.Fixtures;
import setup.Results;
import setup.TopScorers;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class API {

    public static final UpdateAPI storeAPI = new UpdateAPI();

    public void getCurrentLeagueStanding() {

        String url = "https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(API::parseLeagueStandingsJSON).join();

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

    private static String parseLeagueStandingsJSON(String response) {
        ArrayList<LeagueStandings> leagueStandings = new ArrayList<>();

        JSONArray teamStandings = new JSONArray(response);

        for (int i = 0; i < teamStandings.length(); i++) {
            JSONObject teamStanding = teamStandings.getJSONObject(i);

            String team_id = teamStanding.getString("team_id");
            String team_name = teamStanding.getString("team_name");
            String position = teamStanding.getString("overall_league_position");
            String matches_played = teamStanding.getString("overall_league_payed");
            String matches_won = teamStanding.getString("overall_league_W");
            String matches_drawn = teamStanding.getString("overall_league_D");
            String matches_lost = teamStanding.getString("overall_league_L");
            String goals_for = teamStanding.getString("overall_league_GF");
            String goals_against = teamStanding.getString("overall_league_GA");
            String points = teamStanding.getString("overall_league_PTS");
            leagueStandings.add(new LeagueStandings(
                    new BigInteger(team_id),
                    Integer.parseInt(position),
                    team_name,
                    Integer.parseInt(matches_played),
                    Integer.parseInt(matches_won),
                    Integer.parseInt(matches_drawn),
                    Integer.parseInt(matches_lost),
                    Integer.parseInt(goals_for),
                    Integer.parseInt(goals_against),
                    Integer.parseInt(points)
            ));

        }
        storeAPI.updateLeagueStandings(leagueStandings);

        return null;
    }


}
