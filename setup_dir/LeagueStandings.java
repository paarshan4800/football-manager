import com.models.TopScorers;
import com.models.LeagueStandings;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class LeagueStandings {
    public LeagueStandings() {
        String url = "https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=707b36608ee5a52c379428e5c13584dc1abc5a063ebad445a3b86421faeac671";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(API::parseLeagueStandingsJSON).join();
    }
}