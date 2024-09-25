package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String name;
    private String id;
    private double balance;
    private String email;
    private String password;
    private String accessToken;
    private List<Bet> bets;

    public User(){
    	
    }

    public User(String name, double balance, String email, String password){
        this.name = name;
        this.balance = balance;
        this.email = email;
        this.password = password;
        this.bets = new ArrayList<Bet>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public void addBet(Bet bet) {
        bets.add(bet);
    }
    
    public void removeBet(Bet bet) {
        bets.remove(bet);
    }
    
    public List<Bet> getBets() {
        return bets;
    }
    
    public List<Bet> getBetHistory() {
        List<Bet> betHistory = new ArrayList<>();
        for (Bet bet : bets) {
            if (!bet.isActive()) {
                betHistory.add(bet);
            }
        }
        return betHistory;
    }
}
