package com.revature.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;


public class App {

	public static void main(String[] args) {
		App app = new App();
		app.createTable();
//		System.out.println(app.findAllUsers());
//		System.out.println(app.insertUser("myUsername2", "myPassword2", Role.Admin));
//		app.findAllUsers();
//		app.findAllAccounts();
		
//		System.out.println(app.findIdByUsername("myUsername2"));
//		app.deleteUserByUsername("myUsername2");

	}
	
	public List<User> findAllUsers() {
		
		List<User> result = new ArrayList<>();
		User u = new User();
		List<Account> a = new ArrayList();

		// get connection
		Connection conn = ConnectionUtil.getConnection();
		try {

			// sql statement to select all users from users table
			String sql = "SELECT * FROM markd.users";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			System.out.println(String.format("%-20s", "USERNAME") +
					String.format("%-20s", "PASSWORD") +
					String.format("%-20s", "USER ROLE"));
			System.out.println("===================================================");
			while(rs.next()) {
				result.add(new User(rs.getString("username"), "xxxxx", Role.valueOf(rs.getString("user_role_name")), a));
				
				System.out.println(String.format("%-20s", rs.getString("username")) +
									String.format("%-20s", rs.getString("pwd")) +
									String.format("%-20s", Role.valueOf(rs.getString("user_role_name"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("===================================================");
		System.out.println();
		
		return result;
	}
	
	
	public int insertUser(String username, String password, Role role) {

		int pk = 0;
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {

			String sql = "INSERT INTO markd.users (username, pwd, user_role_name) VALUES (?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setObject(3, role, Types.OTHER);
			
			int num = stmt.executeUpdate();

			App app = new App();
			pk = app.findIdByUsername(username);
			System.out.println("Created New User:" + "\r\n" + 
								"\t Username: " + username + "\r\n" + 
								"\t User ID: " + pk + "\r\n" +
								"\t User Role: "+ role.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pk;
	}
	
	public int findIdByUsername(String username) {
		int id = -1;

		Connection conn = ConnectionUtil.getConnection();
		
		try {

			// sql statement to select all users from users table
			String sql = "SELECT * FROM markd.users WHERE username = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
 
			if (rs.next()) {
				  id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public int deleteUserByUsername(String username) {
		int result = 0;

		Connection conn = ConnectionUtil.getConnection();
		
		try {

			// sql statement to select all users from users table
			String sql = "DELETE FROM markd.users WHERE username = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);
			
			// successful deletion returns 1
			result = stmt.executeUpdate();

			if (result > 0) {
				System.out.println("Deleted user " + username);
			} else {
				System.out.println("Could not delete user " + username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int findAllAccounts() {
		int result = 0;
		
		Connection conn = ConnectionUtil.getConnection();
		
		try {
			String sql = "SELECT users.username, accounts.id, accounts.balance, accounts.active  \r\n"
					+ "FROM accounts\r\n"
					+ "INNER JOIN users\r\n"
					+ "ON users.id = accounts.acc_owner\r\n"
					+ "ORDER BY users.username, accounts.id";

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();
 
			while (rs.next()) {
//				System.out.println(
//						rs.getString("username") + "\t\t\t" + rs.getInt("id") + "\t\t\t" +
//						rs.getDouble("balance") + "\t\t\t" + (rs.getBoolean("active") ? "Active" : "Not Active"));
//				
				System.out.println(String.format("%-15s", rs.getString("username")) +
						String.format("%-5s", rs.getInt("id")) +
						String.format("%-15.2f", rs.getDouble("balance")) +
						String.format("%-15s", (rs.getBoolean("active") ? "Active" : "Not Active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	public int createTable() {
		int result = 0;
		
		Connection conn = ConnectionUtil.getConnection();
		String schemaName = "markd";
		String tableName = "users_" + Instant.now().toEpochMilli() + "_" + (int)(Math.random() * 2147483647);
		List<String> columns = new ArrayList<String>();
		columns.add("id");
		columns.add("username");
		columns.add("pwd");
		columns.add("user_role_name");
		
		ArrayList<String> columnTypes = new ArrayList<String>();
		ArrayList<ArrayList<String>> columnConstraints = new ArrayList<ArrayList<String>>();
		

		ArrayList<String> col1Constraints = new ArrayList<String>();
		ArrayList<String> col2Constraints = new ArrayList<String>();
		ArrayList<String> col3Constraints = new ArrayList<String>();
		ArrayList<String> col4Constraints = new ArrayList<String>();

		columnTypes.add("SERIAL");
		columnTypes.add("VARCHAR(50)");
		columnTypes.add("VARCHAR(50)");
		columnTypes.add("markd.user_role");

		col1Constraints.add("PRIMARY KEY");

		col2Constraints.add("NOT NULL");
		col2Constraints.add("UNIQUE");

		col3Constraints.add("NOT NULL");
		
		col4Constraints.add("");
		
		columnConstraints.add(col1Constraints);
		columnConstraints.add(col2Constraints);
		columnConstraints.add(col3Constraints);
		columnConstraints.add(col4Constraints);
		
		// create table
		
		String sql = "DROP TABLE IF EXISTS " + schemaName + "."+ tableName + " CASCADE;\r\n"
				+ "CREATE TABLE "+ schemaName + "." + tableName + "("
				+ columns.get(0) + " " + columnTypes.get(0) + " " + (columnConstraints.get(0).toString().replace(",", "").replace("[","").replace("]", "")) + ", " 
				+ columns.get(1) + " " + columnTypes.get(1) + " " + (columnConstraints.get(1).toString().replace(",", "").replace("[","").replace("]", "")) + ", " 
				+ columns.get(2) + " " + columnTypes.get(2) + " " + (columnConstraints.get(2).toString().replace(",", "").replace("[","").replace("]", "")) + ", " 
				+ "user_role_name markd.user_role NOT NULL)";

//		for(int i = 0; i<columns.size(); i++) {
//			System.out.println(columnTypes.get(i).substring(0,4));
//		}

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			System.out.println(stmt);
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
//	String sql = "SELECT *\r\n"
//	+ "FROM basket_a \r\n"
//	+ "FULL OUTER JOIN basket_b\r\n"
//	+ "ON fruit_a = fruit_b; \r\n"
//	+ "";


}
