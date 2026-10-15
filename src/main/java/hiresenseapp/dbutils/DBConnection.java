package hiresenseapp.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection conn;
	private static String url;
	private static String username;
	private static String password;

	public static synchronized void openConnection(String dbUrl, String dbUser, String dbPassword) {
		url = dbUrl;
		username = dbUser;
		password = dbPassword;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection opened successfully");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found. Check WEB-INF/lib.");
			e.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// Re-opens the connection if MySQL dropped it (idle timeout) or it was closed.
	public static synchronized Connection getConnection() throws SQLException {
		if (url == null) {
			throw new SQLException("Database is not configured (AppInitializer did not run)");
		}
		if (conn == null || conn.isClosed() || !conn.isValid(2)) {
			conn = DriverManager.getConnection(url, username, password);
		}
		return conn;
	}

	public static synchronized void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
