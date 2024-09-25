package model;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
public class Bet {
	private String id;
    private LocalDate date;
    private String userId;
    private String eventId;
    private String option;
    private String status;
    private boolean result;
    private List<Event> events;
    private double amountBet;
    private double stakeReturn;


    public Bet (){
        
    }
    

    public Bet(String id, LocalDate date, User user, String eventId, String status) {
        this.id = id;
        this.date = date;
        // this.user = user;
        this.eventId = eventId;
        this.status = status;
        this.events = new ArrayList<>();
    }

    
    public double getStakeReturn() {
        return stakeReturn;
    }

    public void setStakeReturn(double stakeReturn) {
        this.stakeReturn = stakeReturn;
    }
    public String getId() {
        return id;
    }

    // public User getUser() {
    //     return user;
    // }

    public LocalDate getDate() {
        return date;
    }

    public String getUserId(){
        
        return this.userId;
    }

    public String getOption() {
        return option;
    }

    public String getStatus() {
        return status;
    }

    public String getEventId() {
        return eventId;
    }

    public boolean getResult(){
        return result;
    }

    public double getAmountBet() {
        return amountBet;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setEventId(String eventId){
        this.eventId = eventId;
    }

    public void setAmountBet(double amountBet){
        this.amountBet = amountBet;
    }

    public void setResult(boolean result){
        this.result = result;
    }

    public void setStatus(String status){
        this.status = status;
    }
    
    public void setOption(String option){
        this.option = option;
    }
    
    public void setDate(LocalDate date){
        this.date = date;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
    
    public String toString() {
        StringBuilder eventDetails = new StringBuilder();
        for (Event event : events) {
            eventDetails.append(String.format(
                "Evento: %s\n Data: %s \nCasa: %s \nVisitante: %s \nOdds Casa: %.2f \nOdds Visitante: %.2f \nOdds Empate: %.2f\n",
                event.getName(),
                event.getDate().toString(),
                event.getHomeTeam(),
                event.getAwayTeam(),
                event.getHomeOdds(),
                event.getAwayOdds(),
                event.getDrawOdds()
            ));
        }

        return String.format(
            "Aposta ID: %s\n Data: %s \nValor Aposta: R$%.2f \nOpção: %s %s \nDetalhes do Evento(s): \n",
            id,
            date.toString(),
            amountBet,
            option,
            eventDetails.toString()
        );
    }

	public void removeEvent(Event event) {
		events.remove(event);
	}
	
	public boolean isActive() {
        return "ativa".equalsIgnoreCase(status); // ou outra lógica que faça sentido para o seu sistema
    }
	
	public double getPayout() {
        double totalPayout = 0.0;

        for (Event event : events) {
            double odds = 0.0;
            switch (option.toLowerCase()) {
                case "casa":
                    odds = event.getHomeOdds();
                    break;
                case "fora":
                    odds = event.getAwayOdds();
                    break;
                case "empate":
                    odds = event.getDrawOdds();
                    break;
            }

            totalPayout += amountBet * odds;
        }

        return totalPayout;
    }
}
