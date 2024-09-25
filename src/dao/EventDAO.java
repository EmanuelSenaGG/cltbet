package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import utils.Connection.SQLConnection;
import utils.SQLContext.EventContext;

import java.util.ArrayList;
import java.util.List;

import interfaces.IGenericDAO;
import model.Event;

public class EventDAO implements IGenericDAO<Event, String> {
    private SQLConnection dbConnection = SQLConnection.getInstance();
    private Connection connection = dbConnection.getConnection();

    @Override
    public void insert(Event event) {
        try {
            PreparedStatement pstmt = EventContext.queryInsert(event, connection);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir evento.");
        }
    }

    @Override
    public void delete(String id) {
        try {
       
            PreparedStatement pstmt = EventContext.queryDelete(id, connection);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar evento.");
        }
    }

    @Override
    public Event update(Event event) {
        try {
            PreparedStatement pstmt = EventContext.queryUpdate(event, connection);
            ResultSet rs = pstmt.executeQuery();
			Event updated_event = new Event();

			if (rs.next()) {
				updated_event.setName(rs.getString("name"));
				updated_event.setAwayOdds(rs.getDouble("odd_out"));
				updated_event.setHomeOdds(rs.getDouble("odd_in"));
				updated_event.setDrawOdds(rs.getDouble("odd_draw"));
				updated_event.setDescription(rs.getString("description"));
				updated_event.setDate(rs.getObject("event_date", LocalDate.class));
				updated_event.setIsActive(rs.getBoolean("is_active"));
                updated_event.setAwayTeam(rs.getString("away_team"));
                updated_event.setHomeTeam(rs.getString(("home_team")));
                pstmt.close();
				return updated_event;
			} else {
				return null;
			}
		
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar evento.");
        }
    }

    @Override
    public Event getByID(String id) {
        try {
           
            PreparedStatement pstmt = EventContext.queryGetById(id, connection);
            ResultSet rs = pstmt.executeQuery();
		

            if (rs.next()) {
            Event foundEvent = new Event();
            foundEvent.setName(rs.getString("name"));
            foundEvent.setId(rs.getString("id"));
            foundEvent.setHomeOdds(rs.getDouble("odd_out"));
            foundEvent.setAwayOdds(rs.getDouble("odd_in"));
            foundEvent.setDrawOdds(rs.getDouble("odd_draw"));
            foundEvent.setDescription(rs.getString("description"));
            foundEvent.setDate(rs.getObject("event_date", LocalDate.class));
            foundEvent.setIsActive(rs.getBoolean("is_active"));
            pstmt.close();
            return foundEvent;
               
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar evento.");
        }
    }

    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement pstmt = EventContext.queryGetAll(connection);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event foundEvent = new Event(); 
                foundEvent.setName(rs.getString("name"));
                foundEvent.setId(rs.getString("id"));
                foundEvent.setHomeOdds(rs.getDouble("odd_in"));
                foundEvent.setAwayOdds(rs.getDouble("odd_out"));
                foundEvent.setDrawOdds(rs.getDouble("odd_draw"));
                foundEvent.setDescription(rs.getString("description"));
                foundEvent.setDate(rs.getObject("event_date", LocalDate.class));
                foundEvent.setIsActive(rs.getBoolean("is_active"));
                foundEvent.setAwayTeam(rs.getString("away_team"));
                foundEvent.setHomeTeam(rs.getString("home_team"));
                events.add(foundEvent);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar eventos.");
        }
        return events;
    }


    public List<Event> getAllForAdmin() {
        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement pstmt = EventContext.queryGetAllForAdmin(connection);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event foundEvent = new Event(); 
                foundEvent.setName(rs.getString("name"));
                foundEvent.setId(rs.getString("id"));
                foundEvent.setHomeOdds(rs.getDouble("odd_in"));
                foundEvent.setAwayOdds(rs.getDouble("odd_out"));
                foundEvent.setDrawOdds(rs.getDouble("odd_draw"));
                foundEvent.setDescription(rs.getString("description"));
                foundEvent.setDate(rs.getObject("event_date", LocalDate.class));
                foundEvent.setIsActive(rs.getBoolean("is_active"));
                foundEvent.setAwayTeam(rs.getString("away_team"));
                foundEvent.setHomeTeam(rs.getString("home_team"));
                foundEvent.setResult(rs.getString("result"));
                events.add(foundEvent);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar eventos.");
        }
        return events;
    }



    public void endEvent(String id, String result){
        try {
       
            PreparedStatement pstmt = EventContext.queryEndEvent(id, result, connection);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao finalizar evento.");
        }
    }
}
