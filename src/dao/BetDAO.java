package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import interfaces.IGenericDAO;
import model.Bet;
import model.Event;
import utils.Connection.SQLConnection;
import utils.Global.CurrentUser;
import utils.SQLContext.BetContext;
import viewmodel.BetDetailsViewModel;

public class BetDAO implements IGenericDAO<Bet, String> {

    private SQLConnection dbConnection = SQLConnection.getInstance();
    private Connection connection = dbConnection.getConnection();

    @Override
    public void insert(Bet bet) {
        try {
            PreparedStatement pstmt = BetContext.queryInsert(bet, connection);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir aposta.");
        }
    }

    public void insertBet(Bet bet, List<Event> events) {
        PreparedStatement betStmt = null;
        PreparedStatement eventBetStmt = null;
        PreparedStatement userStmt = null;
        ResultSet generatedKeys = null;

        try {
            // Desabilita o auto-commit para iniciar a transação
            connection.setAutoCommit(false);

            // SQL para inserir a aposta e retornar o UUID gerado
            String betSQL = "INSERT INTO bets (user_id, bet_date, status, stake, stake_return) VALUES (?,?,?,?,?)";
            betStmt = connection.prepareStatement(betSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            // Define os parâmetros da aposta
            betStmt.setObject(1, java.util.UUID.fromString(bet.getUserId()));
            betStmt.setObject(2, bet.getDate());
            betStmt.setString(3, bet.getStatus());
            betStmt.setDouble(4, bet.getAmountBet());
            betStmt.setDouble(5, bet.getStakeReturn());

            // Executa a inserção da aposta
            int affectedRows = betStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar aposta, nenhuma linha foi afetada.");
            }

            // Obtém o UUID gerado
            generatedKeys = betStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                String betId = generatedKeys.getString(1); // UUID da aposta

                // SQL para inserir a relação entre aposta e eventos
                String eventBetSQL = "INSERT INTO events_bets (bets_id, events_id, option) VALUES (?,?,?)";
                eventBetStmt = connection.prepareStatement(eventBetSQL);

                // Insere os eventos associados
                for (Event event : events) {
                    eventBetStmt.setObject(1, java.util.UUID.fromString(betId));
                    eventBetStmt.setObject(2, java.util.UUID.fromString(event.getId())); // ID do evento
                    eventBetStmt.setString(3, event.getOption()); // Opção do evento
                    eventBetStmt.addBatch();
                }

                // Executa o batch de inserções na tabela events_bets
                eventBetStmt.executeBatch();
            } else {
                throw new SQLException("Falha ao criar aposta, nenhum ID foi obtido.");
            }

            String sqlUpdatebalance = "UPDATE users SET balance = balance - ? WHERE id = ?::uuid RETURNING balance";
            userStmt = connection.prepareStatement(sqlUpdatebalance);
            userStmt.setDouble(1, bet.getAmountBet());
            userStmt.setString(2, bet.getUserId());

            // Executa a atualização e retorna o novo saldo
            ResultSet rs = userStmt.executeQuery();
            double newBalance = 0;

            if (rs.next()) {
                newBalance = rs.getDouble("balance");
                CurrentUser.setBalance(newBalance);
            }
            rs.close();

            // Confirma a transação
            connection.commit();
        } catch (SQLException e) {
            try {
                // Reverte a transação em caso de erro
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir aposta e eventos.", e);
        } finally {
            // Restaura o auto-commit e fecha os recursos
            try {
                if (betStmt != null)
                    betStmt.close();
                if (eventBetStmt != null)
                    eventBetStmt.close();
                if (generatedKeys != null)
                    generatedKeys.close();
                if (userStmt != null)
                    userStmt.close();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String id) {
        try {
            PreparedStatement pstmt = BetContext.queryDeleteEventsFromBet(id, connection);
            pstmt.executeUpdate();
            pstmt = BetContext.queryDelete(id, connection);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar aposta.");
        }
    }

    @Override
    public Bet update(Bet bet) {
        try {
            PreparedStatement pstmt = BetContext.queryUpdate(bet, connection);
            ResultSet rs = pstmt.executeQuery();
            pstmt.close();

            Bet bet_updated = new Bet();

            if (rs.next()) {
                bet_updated.setId(rs.getString("id"));
                bet_updated.setDate(rs.getDate("bet_date").toLocalDate());
                bet_updated.setUserId(rs.getString("user_id"));
                bet_updated.setEventId(rs.getString("events_id"));
                bet_updated.setResult(rs.getBoolean("result"));
                bet_updated.setOption(rs.getString("option"));
                bet_updated.setStatus(rs.getString("status"));
                bet_updated.setAmountBet(rs.getDouble("amount_bet"));
            }

            return bet;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar aposta.");
        }
    }

    @Override
    public Bet getByID(String id) {
        try {
            PreparedStatement pstmt = BetContext.queryGetById(id, connection);
            ResultSet rs = pstmt.executeQuery();

            Bet bet = new Bet();

            if (rs.next()) {
                bet.setId(rs.getString("id"));
                bet.setDate(rs.getDate("bet_date").toLocalDate());
                bet.setUserId(rs.getString("user_id"));
                bet.setEventId(rs.getString("events_id"));
                bet.setResult(rs.getBoolean("result"));
                bet.setStatus(rs.getString("status"));
                bet.setAmountBet(rs.getDouble("amount_bet"));
            }

            pstmt.close();
            rs.close();

            return bet;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao pegar aposta.");
        }
    }

    public void endBet(String id, boolean result){
        try {
            PreparedStatement pstmt = BetContext.queryEndBet(id,result, connection);
            pstmt.executeUpdate();
            pstmt.close();         
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar aposta.");
        }
    }
    public List<BetDetailsViewModel> getBetDetailsByID(String id) {
        try {
            PreparedStatement pstmt = BetContext.queryGetPendentesById(id, connection);
            ResultSet rs = pstmt.executeQuery();
            List<BetDetailsViewModel> bets = new ArrayList<BetDetailsViewModel>();

            while (rs.next()) {
                Bet bet = new Bet();
                BetDetailsViewModel details = new BetDetailsViewModel();
                bet.setId(rs.getString("id"));
                bet.setStatus(rs.getString("status"));
                bet.setAmountBet(rs.getDouble("stake"));
                bet.setStakeReturn(rs.getDouble("stake_return"));
                details.setBet(bet);
                bets.add(details);
            }

            for (BetDetailsViewModel details : bets) {
                List<Event> events = new ArrayList<>();
                pstmt = BetContext.queryGetEventsBetById(details.getBet().getId(), connection);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Event event = new Event();
                    event.setName(rs.getString("name"));
                    event.setOption(rs.getString("option"));
                    event.setResult(rs.getString("result"));
                    event.setAwayTeam(rs.getString("away_team"));
                    event.setHomeTeam(rs.getString("home_team"));
                    events.add(event);

                }
                details.setEvents(events);

            }

            return bets;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao pegar aposta.");
        }
    }

    public List<BetDetailsViewModel> getBetHistoryByUserId(String userId) {
        try {
            PreparedStatement pstmt = BetContext.queryGetFinalizedBetById(userId, connection);
            ResultSet rs = pstmt.executeQuery();
            List<BetDetailsViewModel> bets = new ArrayList<BetDetailsViewModel>();

            while (rs.next()) {
                Bet bet = new Bet();
                BetDetailsViewModel details = new BetDetailsViewModel();
                bet.setId(rs.getString("id"));
                bet.setStatus(rs.getString("status"));
                bet.setAmountBet(rs.getDouble("stake"));
                bet.setStakeReturn(rs.getDouble("stake_return"));
                bet.setResult(rs.getBoolean("result"));
                details.setBet(bet);
                bets.add(details);
            }

            for (BetDetailsViewModel details : bets) {
                List<Event> events = new ArrayList<>();
                pstmt = BetContext.queryGetEventsBetById(details.getBet().getId(), connection);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Event event = new Event();
                    event.setName(rs.getString("name"));
                    event.setOption(rs.getString("option"));
                    event.setResult(rs.getString("result"));
                    event.setAwayTeam(rs.getString("away_team"));
                    event.setHomeTeam(rs.getString("home_team"));
                    events.add(event);

                }
                details.setEvents(events);

            }

            return bets;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao pegar aposta.");
        }
    }

    // private List<Event> getEventsByBetId(String betId) {
    //     List<Event> events = new ArrayList<>();
    //     String sql = "SELECT E.name, E.id, E.homeOdds, E.awayOdds, E.drawOdds, eb.option, E.description, E.date, E.amountBet, E.isActive " +
    //                  "FROM events E " +
    //                  "INNER JOIN events_bets EB ON E.id = EB.events_id " +
    //                  "WHERE EB.bets_id = ?";
    
    //     try (PreparedStatement statement = connection.prepareStatement(sql)) {
    //         statement.setObject(1, UUID.fromString(betId));
    
    //         try (ResultSet resultSet = statement.executeQuery()) {
    //             while (resultSet.next()) {
    //                 Event event = new Event(
    //                     resultSet.getString("name"),
    //                     resultSet.getString("id"),
    //                     resultSet.getDouble("homeOdds"),
    //                     resultSet.getDouble("awayOdds"),
    //                     resultSet.getDouble("drawOdds"),
    //                     resultSet.getString("option"),
    //                     resultSet.getString("description"),
    //                     resultSet.getDate("date").toLocalDate(),
    //                     resultSet.getDouble("amountBet"),
    //                     resultSet.getBoolean("isActive")
    //                 );
    //                 events.add(event);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         throw new RuntimeException("Erro ao obter eventos por ID de aposta.", e);
    //     }
    
    //     return events;
    // }
}
