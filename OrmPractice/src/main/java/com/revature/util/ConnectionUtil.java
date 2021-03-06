package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

	private static Connection conn = null;
	
	private ConnectionUtil() {
		
	}
	
	public static Connection getConnection() {

		try {
			if(conn != null && !conn.isClosed()) {
				return conn;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		
		String url = "";
		String username = "";
		String password = "";
		
		try {
			
			FileReader loadFile = new FileReader("C:\\Users\\mddea\\OneDrive\\Desktop\\practice20220310\\OrmPractice\\src\\main\\resources\\applications.properties");
			
			prop.load(loadFile);
			
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	
	
}
