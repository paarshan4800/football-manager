package setup;

import com.api.API;
import com.models.Results;
import com.sql.SQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import static com.api.UpdateAPI.updateLastUpdatedDateTime;

public class Fixtures {

    public Fixtures() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String toDate = LocalDateTime.now().plusMonths(2).format(dateTimeFormatter);
        String fromDate = LocalDateTime.now().plusMinutes(90).format(dateTimeFormatter);

        String url = String.format("http://api.football-data.org/v2/competitions/2021/matches?dateFrom=%s&dateTo=%s", fromDate, toDate);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).setHeader("X-Auth-Token", API.getApiFootballDataOrgApiKey()).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(Fixtures::parseFixturesJSON).join();

    }

    private static String parseFixturesJSON(String response) {

        ArrayList<com.models.Fixtures> fixtures = new ArrayList<>();

        JSONObject object = new JSONObject(response);
        JSONArray matches = object.getJSONArray("matches");

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

            fixtures.add(new com.models.Fixtures(
                    date,
                    time,
                    homeTeam,
                    awayTeam
            ));
        }

        storeFixtures(fixtures);
        updateLastUpdatedDateTime("fixtures");
        return null;

    }

    public static void storeFixtures(ArrayList<com.models.Fixtures> fixtures) {
        for (com.models.Fixtures fixture : fixtures) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = SQL.getDBConnection();

                PreparedStatement pst = con.prepareStatement("insert into upcomingmatches (date,time,homeTeam,awayTeam) values (?,?,?,?);");
                pst.setString(1, fixture.getDate());
                pst.setString(2, fixture.getTime());
                pst.setString(3, fixture.getHomeTeam());
                pst.setString(4, fixture.getAwayTeam());

                int rowsAffected = pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }


    }
}
