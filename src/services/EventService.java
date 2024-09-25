package services;

import business.EventBusiness;
import dao.EventDAO;
import java.util.List;
import java.util.stream.Collectors;
import model.Event;
import utils.Global.CurrentUser;

public class EventService {
    private EventDAO _eventDAO = new EventDAO();

    private EventBusiness _eventBusiness = new EventBusiness();

    public void insertEvent(Event event) {
        try {
          
         _eventDAO.insert(event);
        
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Event updateEvent(Event event) {
        try {
           
         return _eventDAO.update(event);
           
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void endEvent(String id, String result) {
        try {
           
        _eventDAO.endEvent(id, result);
          
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void deleteEvent(String id) {
        try {
            
        _eventDAO.delete(id);
          
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Event getEventById(String id) {
        try {
            if (!CurrentUser.getAccessToken().isBlank())
                return _eventDAO.getByID(id);
            else
                throw new IllegalStateException("Usuário não autenticado.");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Event> getAllEvents() {
        try {
            
                return _eventDAO.getAll();
            
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Event> getAllEventsForAdmin() {
        try {

            return _eventDAO.getAllForAdmin();

        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Event> getEventsAPI() {
        try {

            List<Event> eventsAPI = _eventBusiness.getJogosSemanaAtualAsync().get();

            List<Event> eventsAlreadyRegistered = _eventDAO.getAll();

            List<Event> listFiltred = eventsAPI.stream()
                    .filter(eventAPI -> eventsAlreadyRegistered.stream()
                            .noneMatch(registeredEvent -> registeredEvent.getName().equals(eventAPI.getName())))
                    .collect(Collectors.toList());
            return listFiltred;

        } catch (Exception ex) {
            throw new RuntimeException("Falha ao obter eventos", ex);
        }
    }
}
