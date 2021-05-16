package setup;

import com.models.Results;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Fixtures {

    public Fixtures() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String toDate = LocalDateTime.now().plusMonths(2).format(dateTimeFormatter);
        String fromDate = LocalDateTime.now().plusMinutes(90).format(dateTimeFormatter);

        String url = String.format("http://api.football-data.org/v2/competitions/2021/matches?dateFrom=%s&dateTo=%s", fromDate, toDate);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).setHeader("X-Auth-Token", "be5e8fa7c3b746fd81ed522c955ee399").build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(Fixtures::parseFixturesJSON).join();

    }

    private static String parseFixturesJSON(String response) {
        System.out.println(response);

        ArrayList<com.models.Fixtures> fixtures = new ArrayList<>();

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

//            JSONObject scoreObject = match.getJSONObject("score").getJSONObject("fullTime");
//            long homeTeamScore = scoreObject.getLong("homeTeam");
//            long awayTeamScore = scoreObject.getLong("awayTeam");

            fixtures.add(new com.models.Fixtures(
                    date,
                    time,
                    homeTeam,
                    awayTeam
            ));
        }

        storeFixtures(fixtures);

        return null;

    }

    public static void storeFixtures(ArrayList<com.models.Fixtures> fixtures) {
        for (com.models.Fixtures fixture : fixtures) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                        "14valentine");

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
