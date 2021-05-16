package setup;

import com.api.API;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.sql.SQL.getDBConnection;

public class Players {

    public Players() {
        String url = String.format("https://apiv2.apifootball.com/?action=get_teams&league_id=148&APIkey=%s", API.getApiFootballComApiKey());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(Players::parseJSON).join();
    }

    public static String Password_Hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static String parseJSON(String response) {

        JSONArray teams = new JSONArray(response);

        int count = 0;

        for (int i = 0; i < teams.length(); i++) {
            JSONObject team = teams.getJSONObject(i);

            JSONArray players = team.getJSONArray("players");

            for (int j = 0; j < players.length(); j++) {
                count++;
                JSONObject player = players.getJSONObject(j);

                BigInteger player_id = player.getBigInteger("player_key");
                BigInteger team_id = team.getBigInteger("team_key");
                String name = player.getString("player_name");
                int shirt_number = Integer.parseInt(player.getString("player_number"));
                String country = player.getString("player_country");
                String position = player.getString("player_type");
                int age = player.getInt("player_age");
                int matches_played = player.getInt("player_match_played");
                int goals_scored = player.getInt("player_goals");
                int yellow_cards = player.getInt("player_yellow_cards");
                int red_cards = player.getInt("player_red_cards");
                String username = name.toLowerCase().replaceAll("\\s+", "");
                String password = "samplepwd";
                String hash_pwd = Password_Hash(password);

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = getDBConnection();

                    PreparedStatement pst = con.prepareStatement(
                            "insert into players (player_id,team_id,name,shirt_number,country,position,age,matches_played,goals_scored,yellow_cards,red_cards,username,password) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    pst.setLong(1, player_id.longValue());
                    pst.setLong(2, team_id.longValue());
                    pst.setString(3, name);
                    pst.setInt(4, shirt_number);
                    pst.setString(5, country);
                    pst.setString(6, position);
                    pst.setInt(7, age);
                    pst.setInt(8, matches_played);
                    pst.setInt(9, goals_scored);
                    pst.setInt(10, yellow_cards);
                    pst.setInt(11, red_cards);
                    pst.setString(12, username);
                    pst.setString(13, hash_pwd);

                    pst.executeUpdate();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        return null;
    }
}
