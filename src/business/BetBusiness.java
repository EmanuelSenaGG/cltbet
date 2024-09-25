package business;
import java.util.List;

import model.Event;

public class BetBusiness {
    private double possibleAmount;

    public void calculatePossibleAmount(String option, List<Event> events, double amountBet) {
        double totalPossibleAmount = 0.0;

        for (Event event : events) {
            double eventOdds;

            switch (option) {
                case "home":
                    eventOdds = event.getHomeOdds();
                    break;
                case "away":
                    eventOdds = event.getAwayOdds();
                    break;
                case "drew":
                    eventOdds = event.getDrawOdds();
                    break;
                default:
                    eventOdds = 0.0;
                    break;
            }

            totalPossibleAmount += amountBet * eventOdds;
        }

        this.possibleAmount = totalPossibleAmount;
    }

    public double getPossibleAmount() {
        return possibleAmount;
    }
}
