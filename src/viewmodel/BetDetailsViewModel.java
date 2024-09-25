package viewmodel;

import java.util.ArrayList;
import java.util.List;

import model.Bet;
import model.Event;

public class BetDetailsViewModel {

    private Bet bet;
    private List<Event> events;
    
    public BetDetailsViewModel(Bet bet, List<Event> events) {
        this.bet = bet;
        this.events = events != null ? events : new ArrayList<>();
    }
    
    
    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }


    public BetDetailsViewModel(){
        events = new ArrayList<>();
    }
    
}
