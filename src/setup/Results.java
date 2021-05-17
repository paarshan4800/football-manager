package setup;

import com.api.API;
import com.sql.SQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import static com.api.UpdateAPI.updateLastUpdatedDateTime;

public class Results {

    public Results() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String toDate = LocalDateTime.now().minusMinutes(90).format(dateTimeFormatter);
        String fromDate = LocalDateTime.now().minusMonths(2).format(dateTimeFormatter);

        String url = String.format("http://api.football-data.org/v2/competitions/2021/matches?dateFrom=%s&dateTo=%s", fromDate, toDate);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).setHeader("X-Auth-Token", API.getApiFootballDataOrgApiKey()).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(Results::parseResultsJSON).join();

    }

    private static String parseResultsJSON(String response) {
        System.out.println(response);

        ArrayList<com.models.Results> results = new ArrayList<>();

        JSONObject object = new JSONObject(response);
        JSONArray matches = object.getJSONArray("matches");
        System.out.println(matches.length() + "Size");
        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);
            String utcDate = match.getString("utcDate");
            utcDate = utcDate.substring(0, utcDate.length() - 1);
            String date = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(LocalDateTime.parse(utcDate));
            String time = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(LocalDateTime.parse(utcDate));

            JSONObject homeTeamObject = match.getJSONObject("homeTeam");
            String homeTeam = homeTeamObject.getString("name");

            JSONObject awayTeamObject = match.getJSONObject("awayTeam");
            String awayTeam = awayTeamObject.getString("name");

            JSONObject scoreObject = match.getJSONObject("score").getJSONObject("fullTime");
            long homeTeamScore = scoreObject.getLong("homeTeam");
            long awayTeamScore = scoreObject.getLong("awayTeam");

            results.add(new com.models.Results(
                    date,
                    time,
                    homeTeam,
                    awayTeam,
                    (int) homeTeamScore,
                    (int) awayTeamScore
            ));
        }

        storeResults(results);
        updateLastUpdatedDateTime("results");

        return null;
    }

    public static void storeResults(ArrayList<com.models.Results> results) {
        for (com.models.Results result : results) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = SQL.getDBConnection();

                PreparedStatement pst = con.prepareStatement("insert into finishedMatches (date,time,homeTeam,awayTeam,homeTeamScore,awayTeamScore) values (?,?,?,?,?,?);");
                pst.setString(1, result.getDate());
                pst.setString(2, result.getTime());
                pst.setString(3, result.getHomeTeam());
                pst.setString(4, result.getAwayTeam());
                pst.setInt(5, result.getHomeTeamScore());
                pst.setInt(6, result.getAwayTeamScore());

                int rowsAffected = pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
