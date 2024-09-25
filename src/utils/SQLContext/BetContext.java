package utils.SQLContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Bet;
import model.Event;

public class BetContext {
    public static PreparedStatement queryInsert(Bet bet, Connection connection) throws SQLException {
        String sql = "INSERT INTO bets (user_id, bet_date, status, stake, stake_return) VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, bet.getUserId()); 
            pstmt.setObject(2, bet.getDate()); 
            pstmt.setString(3, bet.getStatus());
            pstmt.setDouble(4, bet.getAmountBet());
            pstmt.setDouble(5, bet.getStakeReturn());


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir aposta.");
        }
        return pstmt;
    }



    
    public static PreparedStatement queryDelete(String id, Connection connection) throws SQLException {
        String sql = "DELETE FROM bets WHERE id = ?::uuid";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir aposta.");
        }
        return pstmt;
    }

    public static PreparedStatement queryDeleteEventsFromBet(String id, Connection connection) throws SQLException {
        String sql = "DELETE FROM events_bets WHERE bets_id = ?::uuid";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir aposta.");
        }
        return pstmt;
    }

    public static PreparedStatement queryUpdate(Bet bet, Connection connection) throws SQLException {
        String sql = "UPDATE bets SET user_id = ?, events_id = ?, bet_date = ?, result = ?, option = ?, status = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, bet.getUserId()); 
            pstmt.setString(2, bet.getEventId());
            pstmt.setObject(3, bet.getDate()); 
            pstmt.setBoolean(4, bet.getResult());
            pstmt.setString(5, bet.getOption());
            pstmt.setString(6, bet.getStatus());
            pstmt.setString(7, bet.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar aposta.");
        }
        return pstmt;
    }


    public static PreparedStatement queryEndBet(String id, boolean result, Connection connection) throws SQLException {
        String sql = "UPDATE bets SET status = ?, result = ? WHERE id = ?::uuid";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, "Finalizada"); 
            pstmt.setBoolean(2, result);
            pstmt.setString(3, id);
        
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar aposta.");
        }
        return pstmt;
    }
    public static PreparedStatement queryGetById(String id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM bets WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, id); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar aposta.");
        }
        return pstmt;
    }

    public static PreparedStatement queryGetPendentesById(String id, Connection connection) throws SQLException {
        String sql = "select B.id,B.stake, B.stake_return, B.status from bets B WHERE B.user_id = ?::uuid AND B.status = 'Pendente'";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, id); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar aposta.");
        }
        return pstmt;
    }

    public static PreparedStatement queryGetFinalizedBetById(String id, Connection connection) throws SQLException {
        String sql = "select B.id,B.stake, B.stake_return, B.status, B.result from bets B WHERE B.user_id = ?::uuid AND B.status = 'Finalizada'";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, id); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar aposta.");
        }
        return pstmt;
    }

    public static PreparedStatement queryGetEventsBetById(String id, Connection connection) throws SQLException {
        String sql = """
        SELECT 
    E.name, 
    E.home_team, 
    E.away_team, 
    COALESCE(E.result, 'Em andamento') AS result, 
    EB.option
FROM 
    events E
INNER JOIN 
    events_bets EB ON E.id = EB.events_id
INNER JOIN 
    bets B ON B.id = EB.bets_id
WHERE 
    EB.bets_id = ?::uuid;
 """;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        try {
            pstmt.setString(1, id); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar aposta.");
        }
        return pstmt;
    }
}
