package setup;

import com.sql.SQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class LeagueStandings {
    public LeagueStandings() {
        String url = "https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(LeagueStandings::parseLeagueStandingsJSON).join();
    }

    private static String parseLeagueStandingsJSON(String response) {
        ArrayList<com.models.LeagueStandings> leagueStandings = new ArrayList<>();

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
            leagueStandings.add(new com.models.LeagueStandings(
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
        storeLeagueStandings(leagueStandings);

        return null;
    }

    public static void storeLeagueStandings(ArrayList<com.models.LeagueStandings> leagueStandings) {
        for (com.models.LeagueStandings leagueStanding : leagueStandings) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = SQL.getDBConnection();

                PreparedStatement pst = con.prepareStatement("insert into standings (team_id,position,matches_played,matches_won,matches_drawn,matches_lost,goals_for,goals_against,points) values (?,?,?,?,?,?,?,?,?)");
                pst.setBigDecimal(1, new BigDecimal(leagueStanding.getTeam_id()));
                pst.setInt(2, leagueStanding.getPosition());
                pst.setInt(3, leagueStanding.getMatches_played());
                pst.setInt(4, leagueStanding.getMatches_won());
                pst.setInt(5, leagueStanding.getMatches_drawn());
                pst.setInt(6, leagueStanding.getMatches_lost());
                pst.setInt(7, leagueStanding.getGoals_for());
                pst.setInt(8, leagueStanding.getGoals_against());
                pst.setInt(9, leagueStanding.getPoints());

                int rowsAffected = pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
