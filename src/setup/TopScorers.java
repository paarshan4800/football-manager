package setup;

import com.sql.API;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class TopScorers {
    public TopScorers() {
        String url = "https://apiv2.apifootball.com/?action=get_topscorers&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(TopScorers::parseTopScorersJSON).join();
    }

    private static String parseTopScorersJSON(String response) {
        ArrayList<com.models.TopScorers> topScorers = new ArrayList<>();

        JSONArray playerStandings = new JSONArray(response);

        for (int i = 0; i < playerStandings.length(); i++) {
            JSONObject playerStanding = playerStandings.getJSONObject(i);

            BigInteger player_id = playerStanding.getBigInteger("player_key");
            String goals_scored = playerStanding.getString("goals");
            String team_id = playerStanding.getString("team_key");


            topScorers.add(new com.models.TopScorers(
                    player_id,
                    Integer.parseInt(goals_scored),
                    new BigInteger(team_id)

            ));
        }
        storeTopScorers(topScorers);
        return null;
    }


    public static void storeTopScorers(ArrayList<com.models.TopScorers> topScorers) {
        for (com.models.TopScorers topScorer : topScorers) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                        "PaarShanDB0408");

                PreparedStatement pst = con.prepareStatement("insert into topScorers (player_id,goals_scored,team_id) values (?,?,?)");
                pst.setBigDecimal(1, new BigDecimal(topScorer.getPlayer_id()));
                pst.setInt(2, topScorer.getGoals_scored());
                pst.setBigDecimal(3, new BigDecimal(topScorer.getTeam_id()));


                int rowsAffected = pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }


}