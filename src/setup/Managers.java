package setup;

import com.api.API;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.sql.SQL.getDBConnection;

public class Managers {

    public Managers() {

        String url = String.format("https://apiv2.apifootball.com/?action=get_teams&league_id=148&APIkey=%s", API.getApiFootballComApiKey());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(Managers::parseJSON).join();
    }

    public static String Password_Hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static String parseJSON(String response) {

        JSONArray teams = new JSONArray(response);

        for (int i = 0; i < teams.length(); i++) {
            JSONObject team = teams.getJSONObject(i);
            JSONArray coaches = team.getJSONArray("coaches");
            JSONObject coach = coaches.getJSONObject(0);

            int manager_id = i + 1001;
            String name = coach.getString("coach_name");
            String country = coach.getString("coach_country");
            int age = coach.getInt("coach_age");
            String username = name.toLowerCase().replaceAll("\\s+", "");

            String password = "samplepwd";
            String hash_pwd = Password_Hash(password);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = getDBConnection();

                PreparedStatement pst = con.prepareStatement(
                        "insert into managers (manager_id,name,country,age,username,password) values (?,?,?,?,?,?)");

                pst.setInt(1, manager_id);
                pst.setString(2, name);
                pst.setString(3, country);
                pst.setInt(4, age);
                pst.setString(5, username);
                pst.setString(6, hash_pwd);

                pst.executeUpdate();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }

}
