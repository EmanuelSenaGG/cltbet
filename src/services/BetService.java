package services;


import java.util.List;

import dao.BetDAO;
import model.Bet;
import model.Event;
import utils.Global.CurrentUser;
import viewmodel.BetDetailsViewModel;

public class BetService {
    private BetDAO betDAO = new BetDAO();

    public void insertBet(Bet bet, List<Event> events) {
        try {
            if (!CurrentUser.getAccessToken().isBlank()) 
                 betDAO.insertBet(bet,events);
            else 
                throw new IllegalStateException("Usuário não autenticado.");       
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Bet updateBet(Bet bet) {
        try {
            if(!CurrentUser.getAccessToken().isBlank())
                return betDAO.update(bet);
            else
                throw new IllegalStateException("Usuário não autenticado.");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void deleteBet(String id) {
        try {
            if(!CurrentUser.getAccessToken().isBlank())
                betDAO.delete(id);
            else
                throw new IllegalStateException("Usuário não autenticado.");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Bet getBetById(String id) {
        try {
            if(!CurrentUser.getAccessToken().isBlank())
                return betDAO.getByID(id);
            else
                throw new IllegalStateException("Usuário não autenticado.");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void endBet(String id, boolean result){
        try {
            if(!CurrentUser.getAccessToken().isBlank())
                betDAO.endBet(id, result);
            else
                throw new IllegalStateException("Usuário não autenticado.");
        } catch (Exception ex) {
            throw ex;
        }
    }


    public List<BetDetailsViewModel> getBetDetailsById(String id) {
        try {
            if(!CurrentUser.getAccessToken().isBlank())
                return betDAO.getBetDetailsByID(id);
            else
                throw new IllegalStateException("Usuário não autenticado.");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<BetDetailsViewModel> getBetHistoryByUserId(String userId) {
        try {
            // Verifica se o usuário está autenticado
            if (!CurrentUser.getAccessToken().isBlank()) {
                // Busca o histórico de apostas do usuário pelo ID
                return betDAO.getBetHistoryByUserId(userId);
            } else {
                throw new IllegalStateException("Usuário não autenticado.");
            }
        } catch (Exception ex) {
            throw ex; // Lança a exceção para ser tratada em um nível superior
        }
    }
    
}