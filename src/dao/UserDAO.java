package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import interfaces.IGenericDAO;

import model.User;
import model.Bet;
import utils.Connection.SQLConnection;
import utils.SQLContext.UserContext;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IGenericDAO<User, String> {
	private SQLConnection dbConnection = SQLConnection.getInstance();
	private Connection connection = dbConnection.getConnection();

	@Override
	public void insert(User user) {
		try {
			PreparedStatement pstmt = UserContext.queryInsert(user, connection);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao inserir usuário.");
		}
	}

	@Override
	public void delete(String id) {
		try {
			PreparedStatement pstmt = UserContext.queryDelete(id, connection);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao deletar usuário.");
		}
	}

	@Override
	public User update(User user) {
		try {
			PreparedStatement pstmt = UserContext.queryUpdate(user, connection);
			pstmt.executeUpdate();
			pstmt.close();
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao atualizar usuário.");
		}

	}

	@Override
	public User getByID(String id) {
		try {
			PreparedStatement pstmt = UserContext.queryGetById(id, connection);
			ResultSet rs = pstmt.executeQuery();
			

			User user_info = new User();

			if (rs.next()) {
				user_info.setId(rs.getString("id"));
				user_info.setName((rs.getString("name")));
				user_info.setEmail(rs.getString("email"));
				user_info.setPassword(rs.getString("password"));
				user_info.setBalance(rs.getDouble("balance"));
				pstmt.close();
				rs.close();
				return user_info;

			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao pegar usuário.");
		}
	}

	public User getByEmail(String email) {
		try {
			PreparedStatement pstmt = UserContext.queryGetByEmail(email, connection);
			ResultSet rs = pstmt.executeQuery();
		

			User user_info = new User();

			if (rs.next()) {
				user_info.setId(rs.getString("id"));
				user_info.setName((rs.getString("name")));
				user_info.setEmail(rs.getString("email"));
				user_info.setPassword(rs.getString("password"));
				user_info.setBalance(rs.getDouble("balance"));
				rs.close();
				pstmt.close();
				return user_info;

			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao pegar usuário.");
		}
		
	}

	public User getUserBets(User user) {
		try {
			PreparedStatement pstmt = UserContext.queryGetUserBets(user, connection);
			ResultSet rs = pstmt.executeQuery();

			User user_info = new User();

			user_info.setName(user.getName());
			user_info.setId(user.getId());
			user_info.setBalance(user.getBalance());
			user_info.setEmail(user.getEmail());
			user_info.setPassword(user.getPassword());

			List<Bet> bets = new ArrayList<>();

			while (rs.next()) {
				Bet bet = new Bet();

				bet.setAmountBet(rs.getDouble("amount"));
				bet.setEventId(rs.getString("event_id"));
				bet.setId(rs.getString("bet_id"));
				bet.setDate(rs.getDate("date").toLocalDate());
				bet.setOption(rs.getString("option"));
				bet.setResult(rs.getBoolean("result"));
				bet.setStatus(rs.getString("status"));
				bet.setUserId(rs.getString("user_id"));

				bets.add(bet);
			}

			pstmt.close();

			if (bets.isEmpty()) {
				return null;
			}

			user_info.getBets().addAll(bets);

			return user_info;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao pegar histórico de apostas.");
		}
	}

	public void depositMoney(String Id, double value) {
		try {
			PreparedStatement pstmt = UserContext.queryDepositUserMoneyByID(Id, value, connection);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Erro ao atualizar saldo do usuario.");
		}
	}

	public void sacMoney(String Id, double value) {
		try {
			PreparedStatement pstmt = UserContext.querySacUserMoneyByID(Id, value, connection);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Erro ao atualizar saldo do usuario.");
		}
	}
}
