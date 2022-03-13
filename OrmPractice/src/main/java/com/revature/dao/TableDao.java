package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import com.revature.annotations.Entity;
import com.revature.main.App;
//import com.revature.models.Account;
//import com.revature.models.User;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ConnectionUtil;
import com.revature.util.ForeignKeyField;
import com.revature.util.MetaModel;
import com.revature.util.PrimaryKeyField;

public class TableDao {

	Connection conn = ConnectionUtil.getConnection();

	// hardcode schema into project

	private String schemaName = "proj1";

	/**
	 * Method for dropping tables before creating.
	 * Only call first time when creating tables or call to reset tables.
	 * Returns the SQL to see if it is correct.
	 * This can be changed to an integer for testing: 1 for success, -1 for failure.
	 * 
	 * @param ormClasses
	 * @return
	 */
	
	public String dropTables(List<Class<?>> ormClasses) {
		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(ormClasses);
		String defineTableSQL = "";

		for (MetaModel<?> mm : cfg.getMetaModels()) {
			// SQL statement to DROP table if exists
			defineTableSQL += "DROP TABLE IF EXISTS " + schemaName + "." + mm.getEntityName() + " CASCADE;" + "\r\n";
		}

		try {
			PreparedStatement stmt = conn.prepareStatement(defineTableSQL);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return defineTableSQL;
	}

	
	/**
	 * Method for creating tables.
	 * Call after dropping if making for the first time or reseting
	 * Returns the SQL to see if it is correct.
	 * 1 for success, -1 for failure
	 * Takes in a list of classes that have Annotations needing to be read
	 * @param ormClasses
	 * @return
	 */

	public String createTables(List<Class<?>> ormClasses) {

		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(ormClasses);
		String defineTableSQL = "";

		for (MetaModel<?> mm : cfg.getMetaModels()) {

			// SQL statement to CREATE table
			defineTableSQL += "CREATE TABLE " + schemaName + "." + mm.getEntityName() + "(" + "\r\n";

			// SQL statement for primary key column if it exists
			if (mm.getPrimaryKey().getName() != null) {

				defineTableSQL += "\t";

				defineTableSQL += mm.getPrimaryKey().getColumnName();

				if (mm.getPrimaryKey().getIsSerial()) {
					defineTableSQL += " SERIAL";
				}

				if (mm.getPrimaryKey().getIsNullable() == false) {
					defineTableSQL += " NOT NULL";
				}

				if (mm.getPrimaryKey().getIsUnique()) {
					defineTableSQL += " UNIQUE";
				}

				defineTableSQL += " PRIMARY KEY";
			}

			defineTableSQL += ",\r\n";

			// SQL statement for columns not a primary key or foreign key
			// iterate through columns
			
			for (ColumnField field : mm.getColumns()) {

				defineTableSQL += "\t" + field.getColumnName() + " " + getSQLType(field);

				if (field.getIsSerial()) {
					defineTableSQL += " SERIAL";
				}

				if (field.getIsNullable() == false) {
					defineTableSQL += " NOT NULL";
				}

				if (field.getIsUnique()) {
					defineTableSQL += " UNIQUE";
				}
				defineTableSQL += ",\r\n";
			}

			// SQL statement for primary key column if it exists
			try {
				for (ForeignKeyField field : mm.getForeignKey()) {
					defineTableSQL += "\t" + field.getColumnName() + " " + getSQLType(field);

					if (field.getIsSerial()) {
						defineTableSQL += " SERIAL";
					}

					if (field.getIsNullable() == false) {
						defineTableSQL += " NOT NULL";
					}

					if (field.getIsUnique()) {
						defineTableSQL += " UNIQUE";
					}
					// defineTableSQL += " FOREIGN KEY";
					defineTableSQL += ",\r\n";
				}

			} catch (RuntimeException e) {
				e.printStackTrace();
			}

			if (defineTableSQL.endsWith(",\r\n")) {
				defineTableSQL = defineTableSQL.substring(0, (defineTableSQL.length()) - 3);
			}

			defineTableSQL += "\r\n\t);\r\n";
		}

		try {
			PreparedStatement stmt = conn.prepareStatement(defineTableSQL);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return defineTableSQL;
	}

	/**
	 * Method converts the field type into variable type for SQL.
	 * Returns String SQL version of the java type
	 * For primary key, not relevant if serial.
	 * @param mm
	 * @return
	 */
	public String getSQLType(MetaModel<?> mm) {

		String result = "";

		if (mm.getPrimaryKey().getClass() == PrimaryKeyField.class) {
			result = "";
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Method converts the field type into variable type for SQL.
	 * Returns String SQL version of the java type
	 * @param field
	 * @return
	 */
	public String getSQLType(ColumnField field) {

		String result = "'";
		String type = field.getType().getSimpleName();

		if (type.equals("String")) {
			result = "VARCHAR(50)";
		} else if (type.equals("int")) {
			result = "INTEGER";
		} else if (type.equals("double")) {
			result = "NUMERIC(50,2)";
		} else if (type.equals("boolean")) {
			result = "BOOLEAN";
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Overloaded version for ForeignKeyField.
	 * Nothing different at the moment.
	 * Method converts the field type into variable type for SQL.
	 * Returns String SQL version of the java type
	 * @param field
	 * @return
	 */
	
	public String getSQLType(ForeignKeyField field) {

		String result = "'";
		String type = field.getType().getSimpleName();

		if (type.equals("String")) {
			result = "VARCHAR(50)";
		} else if (type.equals("int")) {
			result = "INTEGER";
		} else if (type.equals("double")) {
			result = "NUMERIC(50,2)";
		} else if (type.equals("boolean")) {
			result = "BOOLEAN";
		} else {
			result = null;
		}

		return result;
	}

/**
 * method inserts a user into the database
 * takes in table name, first name, last name, username, and password 
 * returns the new user id if successful (table generates id)
 * @param tableName
 * @param firstName
 * @param lastName
 * @param username
 * @param pwd
 * @return
 */
	public int insertUser(String tableName, String firstName, String lastName, String username, String pwd) {
		int primaryKey = 0;

		try {

			String sql = "INSERT INTO " + schemaName + "." + tableName
					+ " (first_name, last_name, username, pwd) VALUES (?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, username);
			stmt.setString(4, pwd);

			int num = stmt.executeUpdate();

			primaryKey = findUserIdByUsername(tableName, username);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return primaryKey;
	}
	
	/**
	 * Method finds a user ID by the username.
	 * Takes in table name and username, and returns the id (primary key).
	 * Returns -1 if not successful.
	 * @param tableName
	 * @param username
	 * @return
	 */

	public int findUserIdByUsername(String tableName, String username) {
		int id = -1;
		try {
			String sql = "SELECT * FROM " + schemaName + "." + tableName + " WHERE username = ?";

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

	/**
	 * Method finds a username by the user id.
	 * Takes in table name and id, returns username.
	 * Returns "Could not find usename" if unsuccessful.
	 * @param tableName
	 * @param id
	 * @return
	 */
	
	public String findUsernameByUserId(String tableName, int id) {
		String username = "Could not find username.";
		try {
			String sql = "SELECT * FROM " + schemaName + "." + tableName + " WHERE id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				username = rs.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return username;
	}

	
	/**
	 * method returns all users in the users table
	 * takes in table name parameter
	 * @param tableName
	 * @return
	 */
	
	public int viewAll(String tableName) {

		int result = -1;
		try {
			String sql = "SELECT * FROM " + schemaName + "." + tableName;

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			printRowDivider();

			System.out.println(
					String.format("%-5s", "ID") + String.format("%-25s", "NAME") + String.format("%-20s", "USERNAME"));

			printRowDivider();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");

				System.out.println(String.format("%-5s", id) + String.format("%-25s", (firstName + " " + lastName))
						+ String.format("%-20s", username));
			}
			printRowDivider();
			
			result = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * method prints a row of equal signs to separate rows
	 */
	public void printRowDivider() {
		for (int i = 0; i < 50; i++) {
			System.out.print("=");
		}
		System.out.println("");
	}

// **************** ACCOUNT METHODS ********************************
	
	/**
	 * Method inserts an account into the database.
	 * Takes in table name, balance, is active status, and user id of the account. 
	 * Returns the new account id if successful (table generates id).
	 * @param tableName
	 * @param accountId
	 * @param balance
	 * @param isActive
	 * @param userId
	 * @return
	 */
	
	public int insertAccount (String tableName, double balance, boolean isActive, int userId) {
		int primaryKey = 0;

		try {

			String sql = "INSERT INTO " + schemaName + "." + tableName
					+ " (balance, is_active, user_id) VALUES (?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setDouble(1, balance);
			stmt.setBoolean(2, isActive);
			stmt.setInt(3, userId);

			int num = stmt.executeUpdate();

			primaryKey = findAccountIdByUserId(tableName, userId);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return primaryKey;
	}
	
	public int findAccountIdByUserId(String tableName, int userId) {
		int accountId = -1;
		try {
			String sql = "SELECT * FROM " + schemaName + "." + tableName + " WHERE user_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				accountId = rs.getInt("account_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountId;
	}

}
