package com.sql;

import com.football_manager.LeagueStandingsFrame;
import com.football_manager.TopScorers;
import com.models.LeagueStandings;
import com.models.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class API {

    public static final StoreAPI storeAPI = new StoreAPI();

    public void getCurrentLeagueStanding() {

        String url = "https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(API::parseLeagueStandingsJSON).join();

    }
    public void getTopScorers(){
        String url = "https://apiv2.apifootball.com/?action=get_topscorers&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(API::parseTopScorersJSON).join();
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
        storeAPI.storeLeagueStandings(leagueStandings);

        return null;
    }
        private static String parseTopScorersJSON(String response) {
            ArrayList<TopScorers> topScorers = new ArrayList<>();

            JSONArray playerStandings = new JSONArray(response);

            for (int i = 0; i < playerStandings.length(); i++) {
                JSONObject playerStanding = playerStandings.getJSONObject(i);

                BigInteger player_id = playerStanding.getBigInteger("player_key");
                String goals_scored = playerStanding.getString("goals");
                String team_id=playerStanding.getString("team_key");


                topScorers.add(new TopScorers(
                        player_id,
                        Integer.parseInt(goals_scored),
                        new BigInteger(team_id)

                ));
            }
            storeAPI.storeTopScorers(topScorers);
            return null;
    }
}
