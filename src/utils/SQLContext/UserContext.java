package utils.SQLContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class UserContext {
	public static PreparedStatement queryInsert(User user, Connection connection) throws SQLException {
		String sql = "INSERT INTO users (name, email, password, balance) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setString(1, user.getName());
	        pstmt.setString(2, user.getEmail());
	        pstmt.setString(3, user.getPassword());
	        pstmt.setDouble(4, user.getBalance());	
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro ao inserir usuário.");
	    }
		return pstmt;
	}

	public static PreparedStatement queryDelete(String id, Connection connection) throws SQLException {
		String sql = "DELETE FROM users WHERE id = ?::uuid";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setString(1, id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro ao excluir usuário.");
	    }
		return pstmt;
	}

	public static PreparedStatement queryUpdate(User user, Connection connection) throws SQLException {
		String sql = "UPDATE users SET name = ?, email = ?, password = ?, balance = ? WHERE id = ?::uuid";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setString(1, user.getName());
	        pstmt.setString(2, user.getEmail());
	        pstmt.setString(3, user.getPassword());
	        pstmt.setDouble(4, user.getBalance());
	        pstmt.setString(5, user.getId());
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro ao atualizar usuário.");	
		}
		return pstmt;
	}

	public static PreparedStatement queryGetById(String id, Connection connection) throws SQLException {
		String sql = "SELECT * FROM users WHERE id = ?::uuid";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setString(1, id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro ao buscar usuário.");
	    }
		return pstmt;
	}

	public static PreparedStatement queryGetByEmail(String email, Connection connection) throws SQLException {
		String sql = "SELECT * FROM users WHERE email = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setString(1, email);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro ao buscar usuário.");
	    }
		return pstmt;
	}

	public static PreparedStatement queryGetUserBets(User user, Connection connection) throws SQLException {
		String sql = "SELECT * FROM users AS u INNER JOIN bets AS b ON u.id = b.user_id WHERE u.id = ?::uuid";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setString(1, user.getId());
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro ao buscar apostas.");
	    }
		return pstmt;
	}

	public static PreparedStatement queryDepositUserMoneyByID(String Id, double balance, Connection connection) throws SQLException{
		String sql = "UPDATE users SET balance = balance + ? WHERE id = ?::uuid";

		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setDouble(1, balance);
	        pstmt.setString(2, Id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro obter dados do saldo do usuario.");
	    }
		return pstmt;
	}	

	
	public static PreparedStatement querySacUserMoneyByID(String Id, double balance, Connection connection) throws SQLException{
		String sql = "UPDATE users SET balance = balance - ? WHERE id = ?::uuid";

		PreparedStatement pstmt = connection.prepareStatement(sql);
		try {
	        pstmt.setDouble(1, balance);
	        pstmt.setString(2, Id);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro obter dados do saldo do usuario.");
	    }
		return pstmt;
	}	
}
