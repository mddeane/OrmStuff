package com.revature.util;

import java.lang.reflect.Type;

import com.revature.annotations.Table;

/**
 * This class extracts the fields marked with @Table annotation
 * designed in com.revature.annoations.
 * Models the entity that will be generated in the database.
 *
 */

public class TableType {

	private Type type;
	 
	public TableType(Type type) {
		
		if (type.getClass().getAnnotation(Table.class) == null) {
			throw new IllegalStateException("Cannot create TableType object. Provided entity " + getName() + "is not annotated with @Table.");
		}
		
		this.type = type;
	}
	
	public String getName() {
		return type.getTypeName();
	}
	
}
