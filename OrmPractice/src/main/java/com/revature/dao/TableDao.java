package com.revature.dao;

import java.sql.Connection;
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

	private String schemaName = "markd";
	String createTableSQL = "";

	// List<Class<?>> ormClasses = Arrays.asList(Account.class, User.class);

	public void getTableInfo(List<Class<?>> ormClasses) {

		Configuration cfg = new Configuration();

		Configuration ormAnnotatedClasses = cfg.addAnnotatedClass(ormClasses);

//		List<MetaModel<Class<?>>> ormMetaModels = ormAnnotatedClasses.getMetaModels();

//		
//		System.out.println("ormAnnotatedClasses: " + ormAnnotatedClasses.toString());

//		System.out.println("ormMetaModels: " + ormMetaModels.toString());
//
//		System.out.println(ormMetaModels.size());

		for (MetaModel<?> mm : cfg.getMetaModels()) {
//			System.out.println("Table name: " + mm.getEntityName());

//			String primaryKey = "";

			// SQL statement to DROP table if exists then CREATE table
			createTableSQL = "DROP TABLE IF EXISTS " + schemaName + "." + mm.getEntityName() + " CASCADE;" + "\r\n"
					+ "CREATE TABLE " + schemaName + "." + mm.getEntityName() + "(" + "\r\n";

			// SQL statement for primary key column if it exists
			if (mm.getPrimaryKey().getName() != null) {

				createTableSQL += "\t";

				createTableSQL += mm.getPrimaryKey().getColumnName();

				if (mm.getPrimaryKey().getIsSerial()) {
					createTableSQL += " SERIAL";
				}

				if (mm.getPrimaryKey().getIsNullable() == false) {
					createTableSQL += " NOT NULL";
				}

				if (mm.getPrimaryKey().getIsUnique()) {
					createTableSQL += " UNIQUE";
				}

				createTableSQL += " PRIMARY KEY";
			}

			createTableSQL += ",\r\n";

			// SQL statement for columns not a primary key or foreign key
			// iterate through columns
			for (ColumnField field : mm.getColumns()) {

				createTableSQL += "\t" + field.getColumnName() + " " + getSQLType(field);

				if (field.getIsSerial()) {
					createTableSQL += " SERIAL";
				}

				if (field.getIsNullable() == false) {
					createTableSQL += " NOT NULL";
				}

				if (field.getIsUnique()) {
					createTableSQL += " UNIQUE";
				}
				createTableSQL += ",\r\n";
			}

			for (ForeignKeyField field : mm.getForeignKey()) {
				createTableSQL += "\t" + field.getColumnName() + " " + getSQLType(field);

				if (field.getIsSerial()) {
					createTableSQL += " SERIAL";
				}

				if (field.getIsNullable() == false) {
					createTableSQL += " NOT NULL";
				}

				if (field.getIsUnique()) {
					createTableSQL += " UNIQUE";
				}
				createTableSQL += " FOREIGN KEY";
				createTableSQL += ",\r\n";
			}

			System.out.println(createTableSQL);

//			if (mm.getColumns()!=null) {
//				for (ColumnField field : mm.getColumns()) {
//					System.out.println("\t Column: " + field.getName());
//					System.out.println("\t\t Type: " + field.getType().getSimpleName());
//					System.out.println("\t\t SQL Type: " + getSQLType(field));
//					getSQLType(field);
//				}
//
//				try {
//					for (ForeignKeyField field : mm.getForeignKey()) {
//						System.out.println("\t Column: " + field.getName());
//						System.out.println("\t\t Type: " + field.getType().getSimpleName());
//						System.out.println("\t\t SQL Type: " + getSQLType(field));
//					}
//				} catch (RuntimeException e) {
//					e.printStackTrace();
//				}				
//				
//			}

			// System.out.println("entityName: " +
			// mm.getClass().getAnnotation(Entity.class).entityName());

//			System.out.println("Primary Key: " + mm.getPrimaryKey().getName());
//			System.out.println(getSQLType(mm));
//			String str = getSQLType(mm);

//			System.out.println("Columns: ");
//			for (ColumnField field : mm.getColumns()) {
//				System.out.println("\t Column: " + field.getName());
//				System.out.println("\t\t Type: " + field.getType().getSimpleName());
//				System.out.println("\t\t SQL Type: " + getSQLType(field));
//				getSQLType(field);
//			}
//
//			try {
//				for (ForeignKeyField field : mm.getForeignKey()) {
//					System.out.println("\t Column: " + field.getName());
//					System.out.println("\t\t Type: " + field.getType().getSimpleName());
//					System.out.println("\t\t SQL Type: " + getSQLType(field));
//				}
//			} catch (RuntimeException e) {
//				e.printStackTrace();
//			}
		}

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

	public int createTable(String tableName, PrimaryKeyField primaryKeyField, List<ColumnField> columnFields,
			List<ForeignKeyField> foreignKeyField) {
		int result = 0;
		return result;

	}

}
