import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class Base {
    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:3000/players")).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(Base::parseJSON).join();

    }

    public static String parseJSON(String response) {

        JSONArray players = new JSONArray(response);

        /*
         * for(int i=0;i<players.length();i++) { JSONObject player =
         * players.getJSONObject(i);
         * 
         * int id = player.getInt("id"); String name = player.getString("name");
         * 
         * System.out.println(id + " - " + name); }
         */

        JSONObject player = players.getJSONObject(0);

        // Sql

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaTest", "root",
                    "PaarShanDB0408");

            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into player values (102,'Akash')");

        }

        catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}