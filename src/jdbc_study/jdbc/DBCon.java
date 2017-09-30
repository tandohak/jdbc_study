package jdbc_study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {
	private String user ="root";
	private String password = "plzdoctor22!";
	private String url = "jdbc:mysql://localhost/mysql_study";
	private Connection conn;
	
	private static DBCon instance = new DBCon();
	
	
	public static DBCon getInstance() {
		return instance;
	}

	private DBCon() {
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.err.printf("%s - %s%n" , e.getErrorCode(), e.getMessage());
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}	
	
	public void connClose(){
		if (conn != null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}
}
