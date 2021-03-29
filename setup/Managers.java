import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Managers {
    public static void main(String[] args) {

        String url = "https://apiv2.apifootball.com/?action=get_teams&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
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

            System.out.println(
                    name + " - " + country + " - " + age + " - " + manager_id + " - " + username + " - " + hash_pwd);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                        "PaarShanDB0408");

                PreparedStatement pst = con.prepareStatement(
                        "insert into managers (manager_id,name,country,age,username,password) values (?,?,?,?,?,?)");

                pst.setInt(1, manager_id);
                pst.setString(2, name);
                pst.setString(3, country);
                pst.setInt(4, age);
                pst.setString(5, username);
                pst.setString(6, hash_pwd);

                pst.executeUpdate();

                // Statement stmt = con.createStatement();
                // stmt.executeUpdate("insert into managers values (102,'Akash')");

            }

            catch (Exception e) {
                System.out.println(e);
            }
        }

        // Sql

        return null;
    }
}