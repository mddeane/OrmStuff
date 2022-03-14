package com.revature.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.Column;
import com.revature.annotations.JoinColumn;
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
	 * Method for dropping tables before creating. Only call first time when
	 * creating tables or call to reset tables. Returns the SQL to see if it is
	 * correct. This can be changed to an integer for testing: 1 for success, -1 for
	 * failure.
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
	 * Method for creating tables. Call after dropping if making for the first time
	 * or reseting Returns the SQL to see if it is correct. 1 for success, -1 for
	 * failure Takes in a list of classes that have Annotations needing to be read
	 * 
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
	 * Method converts the field type into variable type for SQL. Returns String SQL
	 * version of the java type For primary key, not relevant if serial.
	 * 
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
	 * Method converts the field type into variable type for SQL. Returns String SQL
	 * version of the java type
	 * 
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
	 * Overloaded version for ForeignKeyField. Nothing different at the moment.
	 * Method converts the field type into variable type for SQL. Returns String SQL
	 * version of the java type
	 * 
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
	 * Method inserts a user into the database as defined by the ORM. It takes in a
	 * list of all classes with annotations (this can come from a configuration
	 * file), the class that is being read, and a List of the values to be inserted.
	 * 
	 * 
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
	 * This insert method works for all tables. Takes in a list of classes to scan
	 * for annotations (this can be handled in configuration or possible scan the
	 * whoel project)
	 * 
	 * @param ormClasses
	 * @param clazz
	 * @param values
	 * @return
	 */

	public int insert(List<Class<?>> ormClasses, Class<?> clazz, List<Object> values) {
		int primaryKey = 0;
		// String username = "";

		String tableName = "";
		List<String> columns = new LinkedList<String>();
		List<String> variables = new LinkedList<String>();

		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(ormClasses);

		String defineInsertSQL = "";

		for (MetaModel<?> mm : cfg.getMetaModels()) {
			String classEntityName = clazz.getAnnotation(Entity.class).entityName();
			String tableEntityName = mm.getEntityName();
			
			System.out.println("classEntityName: " + classEntityName);
			System.out.println("tableEntityName: " + tableEntityName);

			if (classEntityName.equals(tableEntityName)) {
				tableName = tableEntityName;

				Field[] fields = clazz.getDeclaredFields();

				// iterate through columns and get column names

				for (Field field : fields) {
					try {
						if (field != null) {
							Id column = field.getAnnotation(Id.class);
							if (column.isSerial() != true) {
								columns.add(column.columnName());
								variables.add(field.getName());
							}
						}
					} catch (Exception e) {

					}
				}
				for (Field field : fields) {
					try {
						if (field != null) {
							Column column = field.getAnnotation(Column.class);
							columns.add(column.columnName());
							variables.add(field.getName());
						}
					} catch (Exception e) {

					}
				}
				for (Field field : fields) {
					try {
						if (field != null) {
							JoinColumn column = field.getAnnotation(JoinColumn.class);
							columns.add(column.columnName());
							variables.add(field.getName());
						}
					} catch (Exception e) {

					}
				}
				break;
			} else {
				System.out.println("Couldn't find table");
			}
		}

		try {
			String placeholders = "";
			String sqlColumns = "";

			if (columns.size() == values.size()) {
				for (int i = 0; i < columns.size(); i++) {
					if (i != (columns.size() - 1)) {
						placeholders += "?, ";
						sqlColumns += columns.get(i) + ", ";
					} else {
						placeholders += "?";
						sqlColumns += columns.get(i) + "";
					}
				}
			} else {
				System.out.println("Too many values.");
			}

			defineInsertSQL = "INSERT INTO " + schemaName + "." + tableName + " (" + sqlColumns + ") " + "VALUES ("
					+ placeholders + ")";

			PreparedStatement stmt = conn.prepareStatement(defineInsertSQL);

			for (int i = 0; i < values.size(); i++) {
				String val = values.get(i).toString();
				Class cl = values.get(i).getClass();
				if (cl.getSimpleName().equals("String")) {
					try {
						stmt.setString(i + 1, val);
					} catch (Exception e) {
						System.out.println("Prep String");
					}
				} else if (cl.getSimpleName().equals("Integer")) {
					try {
						stmt.setInt(i + 1, Integer.parseInt(val));
					} catch (Exception e) {
						System.out.println("Prep Integer");
					}
				} else if ((cl.getSimpleName().equals("Double"))) {
					try {
						stmt.setDouble(i + 1, Double.parseDouble(val));
					} catch (Exception e) {
						System.out.println("Prep Double");
					}
				} else if (cl.getSimpleName().equals("Boolean")) {
					try {
						stmt.setBoolean(i + 1, Boolean.parseBoolean(val));
					} catch (Exception e) {
						System.out.println("Prep Boolean");
					}
				}
			}

			int num = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return primaryKey;

	}

	/**
	 * Method finds a user ID by the username. Takes in table name and username, and
	 * returns the id (primary key). Returns -1 if not successful.
	 * 
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
	 * Method finds a username by the user id. Takes in table name and id, returns
	 * username. Returns "Could not find usename" if unsuccessful.
	 * 
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
	 * method returns all users in the users table takes in table name parameter
	 * 
	 * @param tableName
	 * @return
	 */

	public int viewAllUsers() {

		int result = -1;
		String tableName = "users";
		try {
			String sql = "SELECT * FROM " + schemaName + "." + tableName;

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println(tableName.toUpperCase());

			printRowDivider();

			System.out.println(
					String.format("%-10s", "ID") + String.format("%-25s", "NAME") + String.format("%-20s", "USERNAME"));

			printRowDivider();

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");

				System.out.println(String.format("%-10s", id) + String.format("%-25s", (firstName + " " + lastName))
						+ String.format("%-20s", username));
			}
			printRowDivider();

			result = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int viewRow(Class<?> clazz, int id) {
		int result = 0;
		String defineUserSQL = "";
		String tableName = clazz.getAnnotation(Entity.class).entityName();

		defineUserSQL = "SELECT * FROM " + schemaName + "." + tableName + " WHERE id = ?";

		
		try {
			PreparedStatement stmt = conn.prepareStatement(defineUserSQL);

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println(tableName.toUpperCase());

			printRowDivider();

			System.out.println(
					String.format("%-10s", "ID") + String.format("%-25s", "NAME") + String.format("%-20s", "USERNAME"));

			printRowDivider();

			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");

				System.out.println(String.format("%-10s", id) + String.format("%-25s", (firstName + " " + lastName))
				+ String.format("%-20s", username));

				result = 1;
			} else {
				System.out.println("Couldn't find user.");
			}
			
			printRowDivider();
			
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

	public void printRowDivider(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print("=");
		}
		System.out.println("");
	}

// **************** ACCOUNT METHODS ********************************

	/**
	 * Method inserts an account into the database. Takes in table name, balance, is
	 * active status, and user id of the account. Returns the new account id if
	 * successful (table generates id).
	 * 
	 * @param tableName
	 * @param accountId
	 * @param balance
	 * @param isActive
	 * @param userId
	 * @return
	 */

	public int insertAccount(String tableName, double balance, boolean isActive, int userId) {
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

	/**
	 * Method fiends account id by the user id (owner of account).
	 * 
	 * @param tableName
	 * @param userId
	 * @return
	 */

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

	/**
	 * Method finds the user id of the account owner by the account id.
	 * 
	 * @param tableName
	 * @param accountId
	 * @return
	 */

	public int findUserIdByAccountId(String tableName, int accountId) {
		int userId = 0;
		try {
			String sql = "SELECT * FROM " + schemaName + "." + tableName + " WHERE account_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, accountId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				userId = rs.getInt("user_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}

	/**
	 * Method prints all accounts Returns 1 if successful, -1 if not
	 * 
	 * @return
	 */

	public int viewAllAccounts() {

		int result = -1;
		String tableName = "accounts";
		try {
			String sql = "SELECT * FROM " + schemaName + "." + tableName;

			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println(tableName.toUpperCase());

			printRowDivider();

			System.out.println(String.format("%-5s", "ID") + String.format("%-20s", "BALANCE")
					+ String.format("%-15s", "STATUS") + String.format("%-5s", "USER ID"));

			printRowDivider();

			while (rs.next()) {
				int accountId = rs.getInt("account_id");
				double balance = rs.getDouble("balance");
				boolean isActive = rs.getBoolean("is_active");
				int userId = rs.getInt("user_id");

				System.out.println(String.format("%-5s", accountId) + String.format("%-20.2f", balance)
						+ String.format("%-15s", (isActive ? "Active" : "Not Active")) + String.format("%-5s", userId));
			}
			printRowDivider();

			result = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int viewAllAccountsWithNameAndUsername() {
		int result = -1;
		String tableNameLeft = "accounts";
		String tableNameRight = "users";
		try {
			String sql = "SELECT users.id, users.first_name, users.last_name, users.username, accounts.account_id, accounts.balance, accounts.is_active  \r\n"
					+ "FROM " + schemaName + "." + tableNameLeft + "\r\n" + "INNER JOIN " + schemaName + "."
					+ tableNameRight + "\r\n" + "ON users.id = accounts.user_id\r\n" + "ORDER BY accounts.account_id";

			PreparedStatement stmt = conn.prepareStatement(sql);

//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println(tableNameLeft.toUpperCase() + " AND " + tableNameRight.toUpperCase());

			printRowDivider(105);

			System.out.println(String.format("%-10s", "ACC ID") + String.format("%-20s", "BALANCE")
					+ String.format("%-15s", "STATUS") + String.format("%-10s", "USER ID")
					+ String.format("%-25s", "NAME") + String.format("%-20s", "USERNAME"));

			printRowDivider(105);

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");
				int accountId = rs.getInt("account_id");
				double balance = rs.getDouble("balance");
				boolean isActive = rs.getBoolean("is_active");

				System.out.println(String.format("%-10s", accountId) + String.format("%-20.2f", balance)
						+ String.format("%-15s", (isActive ? "Active" : "Not Active")) + String.format("%-10s", id)
						+ String.format("%-25s", (firstName + " " + lastName)) + String.format("%-20s", username));
			}
			printRowDivider(105);

			result = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
