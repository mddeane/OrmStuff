package com.revature.dao;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

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


	//List<Class<?>> ormClasses = Arrays.asList(Account.class, User.class);

	public void getTableInfo(List<Class<?>> ormClasses) {


		Configuration cfg = new Configuration();

		Configuration ormAnnotatedClasses = cfg.addAnnotatedClass(ormClasses);

		System.out.println(ormAnnotatedClasses.toString());
		
		List<MetaModel<Class<?>>> ormMetaModels = ormAnnotatedClasses.getMetaModels();

		System.out.println(ormMetaModels.toString());
		
		for (MetaModel<?> mm : cfg.getMetaModels()) {
			
			System.out.println("Table: " + mm.getSimpleClassName());

			System.out.println("Primary Key: " + mm.getPrimaryKey().getName());
			System.out.println("\t Type: " + mm.getPrimaryKey().getType().getSimpleName());
			System.out.println("\t SQL Type: " + getSQLType(mm));
			
			System.out.println("Columns: ");
			for (ColumnField field : mm.getColumns()) {
				System.out.println("\t Column: " + field.getName());
				System.out.println("\t\t Type: " + field.getType().getSimpleName());
				System.out.println("\t\t SQL Type: " + getSQLType(field));
				getSQLType(field);
			}
			
			try {
				for (ForeignKeyField field : mm.getForeignKey()) {
					System.out.println("\t Column: " + field.getName());
					System.out.println("\t\t Type: " + field.getType().getSimpleName());
					System.out.println("\t\t SQL Type: " + getSQLType(field));
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}


	}
	
	
	public String getSQLType(MetaModel<?> mm) {
		
		String result = "";
		
		if (mm.getPrimaryKey().getClass()==PrimaryKeyField.class) {
			result = "SERIAL";
		} else {
			result = null;
		}
		
		return result;
	}

	public String getSQLType(ColumnField field) {
		
		String result = "'";
		String type = field.getType().getSimpleName();
		
		if(type.equals("String")) {
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
		
		if(type.equals("String")) {
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
	
	
	public int createTable(String tableName, PrimaryKeyField primaryKeyField, List<ColumnField> columnFields, List<ForeignKeyField> foreignKeyField) {
		int result = 0;
		return result;

	}






}
