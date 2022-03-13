package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.revature.annotations.Entity;
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

	private String schemaName = "proj1";

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
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return defineTableSQL;
	}

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
				//defineTableSQL += " FOREIGN KEY";
				defineTableSQL += ",\r\n";
			}

			if (defineTableSQL.endsWith(",\r\n")) {
				defineTableSQL = defineTableSQL.substring(0, (defineTableSQL.length()) - 3);
			}

			defineTableSQL += "\r\n\t);\r\n";
		}
		
		try {
			PreparedStatement stmt = conn.prepareStatement(defineTableSQL);
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return defineTableSQL;
	}

	public String getSQLType(MetaModel<?> mm) {

		String result = "";

		if (mm.getPrimaryKey().getClass() == PrimaryKeyField.class) {
			result = "";
		} else {
			result = null;
		}

		return result;
	}

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

//	public int createTable(String tableName, PrimaryKeyField primaryKeyField, List<ColumnField> columnFields,
//			List<ForeignKeyField> foreignKeyField) {
//		int result = 0;
//		return result;
//
//	}
	
	

}
