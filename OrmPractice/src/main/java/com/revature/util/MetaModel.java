package com.revature.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

/**
 * This class gathres information about the class we
 * want to transpose into a database entity.
 */

public class MetaModel<T> {

	private Class<?> clazz; // Table? Entity?
	private PrimaryKeyField primaryKeyField;
	private List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;
	
	
	
	public static MetaModel<Class<?>> of(Class<?> clazz) {
		
		// Entity
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create MetaModel object. Provided entity " + clazz.getName() + "is not annotated with @Entity.");
		}
		
		return new MetaModel<Class<?>>(clazz);
	}

	
	
	
	public MetaModel(Class<?> clazz) {
		this.clazz = clazz;
		this.columnFields = new LinkedList<ColumnField>();
		this.foreignKeyFields = new LinkedList<ForeignKeyField>(); 
	}
	
	
	public List<ColumnField> getColumns() {
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			
			if (column != null) {
				columnFields.add(new ColumnField(field));
			}
		}
		
		if(columnFields.isEmpty()) {
			throw new RuntimeException("No column with @Column annotated in: " + clazz.getName());
		}
		
		return columnFields;
	}

	public PrimaryKeyField getPrimaryKey() {

		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			Id primaryKey = field.getAnnotation(Id.class);
			
			if (primaryKey != null) {
				this.primaryKeyField = new PrimaryKeyField(field);
				return primaryKeyField;
			}
		}
		
		throw new RuntimeException("No field with @Id annotated in: " + clazz.getName());
	}

	public List<ForeignKeyField> getForeignKey() {
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			JoinColumn foreignKeyField = field.getAnnotation(JoinColumn.class);
			
			if (foreignKeyField != null) {
				foreignKeyFields.add(new ForeignKeyField(field));
			}
		}
		
		if(foreignKeyFields.isEmpty()) {
			throw new RuntimeException("No foreign keys with @JoinColumn annotated in: " + clazz.getName());
		}
		
		return foreignKeyFields;	
	}
	
	public String getSimpleClassName() {
		return clazz.getSimpleName(); // returns just class name
	}
	
	public String getClassName() {
		return clazz.getName(); // returns package and class name
	}




	@Override
	public String toString() {
		return "MetaModel [clazz=" + clazz + ", primaryKeyField=" + primaryKeyField + ", columnFields=" + columnFields
				+ ", foreignKeyFields=" + foreignKeyFields + "]";
	}

	

}
