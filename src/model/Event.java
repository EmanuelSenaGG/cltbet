package model;

import java.time.LocalDate;

public class Event {
    private String name;
    private String homeTeam;
    private String awayTeam;
    private String id;
    private double homeOdds;
    private double awayOdds;
    private double drawOdds;
    private String option;
    private String description;
    private LocalDate date;
    private boolean isActive;
    private double amountBet;
    private String result;

    public Event(String name, String id, double homeOdds, double awayOdds, double drawOdds, String option, String description, LocalDate date, double amountBet, boolean isActive) {
        this.name = name;
        this.id = id;
        this.homeOdds = homeOdds;
        this.awayOdds = awayOdds;
        this.drawOdds = drawOdds;
        this.setOption(option);
        this.description = description;
        this.date = date;
        this.isActive = isActive;
        this.amountBet = amountBet;
        this.result = null;
    }

    public Event() {
        this.result = null;
    }

    public Event(String name, Double oddCasa, Double oddFora, Double oddEmpate, LocalDate data, String descricao, String hometeam, String awayteam) {
        this.name = name;
        this.homeOdds = oddCasa;
        this.awayOdds = oddFora;
        this.drawOdds = oddEmpate;
        this.date = data;
        this.description = descricao;
        this.homeTeam = hometeam;
        this.awayTeam = awayteam;
        this.result = null;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }
    public void setResult(String result){
        this.result = result;
    }

    public String getResult(){
        return this.result;
    }

    public String getId() {
        return id;
    }

    public double getHomeOdds() {
        return homeOdds;
    }

    public double getAwayOdds() {
        return awayOdds;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayteam) {
        this.awayTeam = awayteam;
    }

    public double getDrawOdds() {
        return drawOdds;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHomeOdds(double homeOdds) {
        this.homeOdds = homeOdds;
    }

    public void setAwayOdds(double awayOdds) {
        this.awayOdds = awayOdds;
    }

    public void setDrawOdds(double drawOdds) {
        this.drawOdds = drawOdds;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public double getAmountBet() {
    	return amountBet;
    }

    public Double getSelectedOdd(){
        if(this.option.equals("Casa"))
            return this.homeOdds;
        else if (this.option.equals("Fora"))
            return this.awayOdds;
        else 
            return this.drawOdds;
    }
    public String toString() {
        return String.format(
                "Nome: %s\nHome Odds: %.2f\nAway Odds: %.2f\nDraw Odds: %.2f\nDate: %s\nDescription: %s\n",
                this.name, this.homeOdds, this.awayOdds, this.drawOdds, date.toString(), this.description);
    }

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
}
