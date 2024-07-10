package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJDBCUtil {
	static final String strDbUrl = "jdbc:mysql://localhost:3306/estatebasic?autoReconnect=true&useSSL=false";
	static final String user = "root";
	static final String pass = "viethoang123";
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(strDbUrl, user, pass);
		}
		catch ( SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
