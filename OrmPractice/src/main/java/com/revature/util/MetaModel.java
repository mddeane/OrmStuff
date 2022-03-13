package com.revature.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Default;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

/**
 * This class gathres information about the class we want to transpose into a
 * database entity.
 */

public class MetaModel<T> {

	private Class<?> clazz; // Table? Entity?
	private EntityType entityType;
	private PrimaryKeyField primaryKeyField;
	private List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;

//****************************
	/*
	 * private DefaultField defaultField; private CheckField checkField; private
	 * NotNullField notNullField; private UniqueField UniqueField;
	 * 
	 * private TableType tableType;
	 * 
	 * 
	 */
//****************************

	public static MetaModel<Class<?>> of(Class<?> clazz) {

		// Entity
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create MetaModel object. Provided entity " + clazz.getName()
					+ "is not annotated with @Entity.");
		}

//		System.out.println("clazz: " + clazz.getName());
//		System.out.println(clazz.getAnnotation(Entity.class));

//		System.out.println(clazz.getAnnotation(Entity.class).entityName());

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

		if (columnFields.isEmpty()) {
			throw new RuntimeException("No column with @Column annotated in: " + clazz.getName());
		}

		return columnFields;
	}

//	public EntityType getEntityType() {
//		//Type entity = clazz.getAnnotation(Entity.class);
//
//		Type entity;
//		
//		if (clazz.getAnnotation(Entity.class) != null) {
//			this.entityType = new EntityType(entity);
//			return entityType;
//		}
//		throw new RuntimeException("No entity with @Entity annotated in: " + clazz.getName());
//	}

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

//		if (foreignKeyFields.isEmpty()) {
//			throw new RuntimeException("No foreign keys with @JoinColumn annotated in: " + clazz.getName());
//		}

		return foreignKeyFields;
	}

	public String getSimpleClassName() {
		return clazz.getSimpleName(); // returns just class name
	}

	public String getClassName() {
		return clazz.getName(); // returns package and class name
	}

	public String getEntityName() {
		return clazz.getAnnotation(Entity.class).entityName();
	}
	
	@Override
	public String toString() {
		return "MetaModel [clazz=" + clazz + ", primaryKeyField=" + primaryKeyField + ", columnFields=" + columnFields
				+ ", foreignKeyFields=" + foreignKeyFields + "]";
	}

}
