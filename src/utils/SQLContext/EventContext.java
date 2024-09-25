package utils.SQLContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Event;

public class EventContext {
	public static PreparedStatement queryInsert(Event event, Connection connection) throws SQLException {
		String sql = "INSERT INTO events (name, event_date, description, odd_out, odd_in, odd_draw, is_active,home_team,away_team) VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
			pstmt.setString(1, event.getName());
			pstmt.setObject(2, event.getDate());
			pstmt.setString(3, event.getDescription());
			pstmt.setDouble(4, event.getAwayOdds());
			pstmt.setDouble(5, event.getHomeOdds());
			pstmt.setDouble(6, event.getDrawOdds());
			pstmt.setBoolean(7, event.getIsActive());
			pstmt.setString(8, event.getHomeTeam());
			pstmt.setString(9, event.getAwayTeam());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao inserir evento.");
		}
		return pstmt;
	}

	public static PreparedStatement queryDelete(String id, Connection connection) throws SQLException {
		String sql = "DELETE FROM events WHERE id = ?::uuid";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
			pstmt.setString(1, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao excluir evento.");
		}
		return pstmt;
	}

	public static PreparedStatement queryUpdate(Event event, Connection connection) throws SQLException {
		String sql = """
				UPDATE events SET name = ?, event_date = ?, description = ?, odd_out = ?, odd_in = ?, odd_draw = ?, is_active = ?, home_team = ?, away_team = ? WHERE id = ?::uuid
				RETURNING name, odd_out, odd_in, odd_draw, description, DATE(event_date) AS event_date, is_active, home_team, away_team
				""";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
			pstmt.setString(1, event.getName());
			pstmt.setObject(2, event.getDate());
			pstmt.setString(3, event.getDescription());
			pstmt.setDouble(4, event.getAwayOdds());
			pstmt.setDouble(5, event.getHomeOdds());
			pstmt.setDouble(6, event.getDrawOdds());
			pstmt.setBoolean(7, event.getIsActive());
			pstmt.setString(8, event.getHomeTeam());
			pstmt.setString(9, event.getAwayTeam());
			pstmt.setString(10, event.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar evento.");
		}
		return pstmt;
	}

	public static PreparedStatement queryGetById(String id, Connection connection) throws SQLException {
		String sql = "SELECT * FROM events WHERE id = ?::uuid";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
			pstmt.setString(1, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao buscar evento.");
		}
		return pstmt;
	}

	public static PreparedStatement queryGetAll(Connection connection) throws SQLException {
		String sql = """
				SELECT id,
				name,
				DATE(event_date) AS event_date,
				description,
				odd_out,
				odd_in,
				odd_draw,
				is_active,
				away_team,
				home_team
				FROM events  
				WHERE is_active = ?
				""";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setBoolean(1, true);
		return pstmt;
	}

	public static PreparedStatement queryGetAllForAdmin(Connection connection) throws SQLException {
		String sql = """
				SELECT id,
				name,
				DATE(event_date) AS event_date,
				description,
				odd_out,
				odd_in,
				odd_draw,
				is_active,
				away_team,
				home_team,
				result
				FROM events
				where is_active = ?
				
				""";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setBoolean(1, true);

		return pstmt;
	}

	public static PreparedStatement queryEndEvent(String id, String result, Connection connection) throws SQLException {
		String sql = """
				UPDATE events
				SET result = ?,
				is_active = ?
				WHERE id = ?::uuid
				""";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, result);
		pstmt.setBoolean(2,false);
		pstmt.setString(3, id);	
		return pstmt;
	}
}
