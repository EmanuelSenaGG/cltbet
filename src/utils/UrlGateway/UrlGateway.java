package utils.UrlGateway;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UrlGateway {
    private static final String BASE_URL = "https://v3.football.api-sports.io";
    private static final int LEAGUE_BRASILEIRAO_ID = 71; // ID da liga do Brasileirão

    public static String getJogosRodadaAtualUrl(int seasonYear) {
        return String.format("%s/fixtures?league=%d&season=%d&status=FT", BASE_URL, LEAGUE_BRASILEIRAO_ID, seasonYear);
    }
    
    public static int getLeagueBrasileiraoId(){
        return LEAGUE_BRASILEIRAO_ID;
    }
   
    public static String getWeekRange() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(java.time.DayOfWeek.SUNDAY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return String.format("from=%s&to=%s", startOfWeek.format(formatter), endOfWeek.format(formatter));
    }

    public static String getJogosSemanaAtualUrl(int seasonYear) {
        return String.format("%s/fixtures?league=%d&season=%d&%s", BASE_URL, LEAGUE_BRASILEIRAO_ID, seasonYear, getWeekRange());
    }
}

