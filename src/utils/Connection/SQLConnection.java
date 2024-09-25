package utils.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	    private static final String URL = "jdbc:postgresql://localhost:5435/clt_bet";
	    private static final String USER = "dev";
	    private static final String PASSWORD = "1234";
	    private static SQLConnection instance;
	    private Connection connection;

	    private SQLConnection() {
	        try {
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Falha ao criar conexão.");
	        }
	    }

	    public static SQLConnection getInstance() {
	        if (instance == null) {
	            synchronized (SQLConnection.class) {
	                if (instance == null) {
	                    instance = new SQLConnection();
	                }
	            }
	        }
	        return instance;
	    }

	    public Connection getConnection() {
	        return connection;
	    }
}
