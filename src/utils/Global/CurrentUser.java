package utils.Global;

import java.util.List;

import model.Event;

import java.util.ArrayList;


public class CurrentUser {
    private static CurrentUser instance = new CurrentUser();
    private String username;
    private String id;
    private String email;
    private String accessToken;
    private Double balance;
    private List<Event> events = new ArrayList<>();;

    public CurrentUser() {
       accessToken = "";
    }

    public static String getId() {
        return instance.id;
    }

    public static void setId(String id) {
        instance.id = id;
    }

    public static String getEmail() {
        return instance.email;
    }

    public static void setEmail(String email) {
        instance.email = email;
    }

    public static Double getBalance() {
        return instance.balance;
    }

    public static void setBalance(Double balance) {
        instance.balance = balance;
    }

    public static String getAccessToken() {
        return instance.accessToken;
    }

    public static void setAccessToken(String accessToken) {
        instance.accessToken = accessToken;
    }

    public static String getUsername() {
        return instance.username;
    }

    public static void setUsername(String username) {
        instance.username = username;
    }

    public static void setEvents(List<Event> list){
        instance.events = list;
    }

    public static List<Event> getEventsSelected(){
        return instance.events;
    }

    public static void putEventsFromList(Event event){
        instance.events.add(event);
    }
    
    public static void removeEventFromList(String eventName) {
        instance.events.removeIf(e -> e.getName().equals(eventName));
    }
    


    public static String getUserDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(getId()).append("\n");
        sb.append("Email: ").append(getEmail()).append("\n");
        sb.append("Access Token: ").append(getAccessToken()).append("\n");
        sb.append("Username: ").append(getUsername()).append("\n");
        sb.append("Balance: ").append(getBalance()).append("\n");
        return sb.toString();
    }
}
