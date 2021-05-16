package setup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.sql.SQL.getDBConnection;

public class Teams {

    public Teams() {
        String url = "https://apiv2.apifootball.com/?action=get_teams&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(Teams::parseJSON).join();
    }

    public static String parseJSON(String response) {

        JSONArray teams = new JSONArray(response);

        for (int i = 0; i < teams.length(); i++) {
            JSONObject team = teams.getJSONObject(i);

            BigInteger team_id = BigInteger.valueOf(Integer.parseInt(team.getString("team_key")));
            int manager_id = i + 1001;
            String name = team.getString("team_name");
            String badge = team.getString("team_badge");

            System.out.println(team_id + " - " + manager_id + " - " + name + " - " + badge);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = getDBConnection();

                PreparedStatement pst = con
                        .prepareStatement("insert into teams (team_id,manager_id,name,badge) values (?,?,?,?)");

                pst.setLong(1, team_id.longValue());
                pst.setInt(2, manager_id);
                pst.setString(3, name);
                pst.setString(4, badge);

                pst.executeUpdate();

            } catch (Exception e) {
                System.out.println(e);
            }

        }
        return null;
    }
}
