package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Column;
import com.revature.annotations.Id;

/**
 * This class extracts the fields marked with @Column annotation
 * designed in com.revature.annoations.
 * Models the column that will be generated in the database.
 *
 */

public class ColumnField {

	private Field field; //from java.lang.reflect
	
	public ColumnField(Field field) {
		if(field.getAnnotation(Column.class) == null) {
			throw new IllegalStateException("Cannot create ColumnField object. Provided field " + getName() + " is not annotated with @Column");
		}
		
		this.field = field;
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Class<?> getType() {
		return field.getType();
	}
	
	public String getColumnName() {
		return field.getAnnotation(Column.class).columnName();
	}
	

	public boolean getIsSerial() {
		return field.getAnnotation(Column.class).isSerial();
	}

	public boolean getIsNullable() {
		return field.getAnnotation(Column.class).isNullable();
	}

	public boolean getIsUnique() {
		return field.getAnnotation(Column.class).isUnique();
	}
}
