package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import com.revature.models.Role;

public class ConnectionUtil {

	private static Connection conn = null;

	private ConnectionUtil() {

	}

	public static Connection getConnection() {

		try {
			if (conn != null && !conn.isClosed()) {
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
			prop.load(new FileReader(
					"C:\\Users\\mddea\\OneDrive\\Desktop\\practice20220310\\TablePractice\\src\\main\\resources\\applications.properties"));

			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

}
