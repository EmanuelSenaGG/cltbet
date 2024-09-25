package business;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import model.Event;
import utils.UrlGateway.UrlGateway;

import org.json.JSONArray;
import org.json.JSONObject;

public class EventBusiness {
    private final String API_KEY = "56f69e95957b20301db785370d67a521";
    private final String API_HOST = "v3.football.api-sports.io";
    private HttpClient _client = HttpClient.newHttpClient();
    private final int SEASON_YEAR = 2024;

    private Double generateRandomOdd() {
        Random RANDOM = new Random();
        double min = 1.1;
        double max = 10.0;
        double randomValue = min + (max - min) * RANDOM.nextDouble();
        return Math.round(randomValue * 100.0) / 100.0;
    }

    private Event createEventFromJson(JSONObject match) {
        String homeTeam = match.getJSONObject("teams").getJSONObject("home").getString("name");
        String awayTeam = match.getJSONObject("teams").getJSONObject("away").getString("name");
        String dateTimeString = match.getJSONObject("fixture").getString("date");
        String dateString = dateTimeString.split("T")[0];
        LocalDate date = LocalDate.parse(dateString);
        String nome = String.format("Jogo %s vs %s", homeTeam, awayTeam);
        String descricao = String.format("Campeonato Brasileiro ASSAI. Partida entre %s e %s", homeTeam, awayTeam);
        Double oddHome = generateRandomOdd();
        Double oddAway = generateRandomOdd();
        Double oddDraw = generateRandomOdd();
        return new Event(nome, oddHome, oddAway, oddDraw, date, descricao, homeTeam, awayTeam);
    }

    public CompletableFuture<List<Event>> getJogosSemanaAtualAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Event> events = new ArrayList<>();
            try {
                String url = UrlGateway.getJogosSemanaAtualUrl(SEASON_YEAR);
                HttpRequest requestGames = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("x-rapidapi-key", API_KEY)
                        .header("x-rapidapi-host", API_HOST)
                        .build();

                HttpResponse<String> responseGames = _client.send(requestGames, HttpResponse.BodyHandlers.ofString());
                JSONObject jsonGames = new JSONObject(responseGames.body());
                JSONArray fixtures = jsonGames.getJSONArray("response");

                for (int i = 0; i < fixtures.length(); i++) {
                    JSONObject match = fixtures.getJSONObject(i);
                    Event event = createEventFromJson(match);
                    events.add(event);
                }
            } catch (Exception e) {
                throw new CompletionException(e);
            }
           
            // Filtrar os eventos cuja data seja anterior à data atual
            LocalDate today = LocalDate.now();
            events.removeIf(e -> e.getDate().isBefore(today));
            return events;
        });
    }
}
