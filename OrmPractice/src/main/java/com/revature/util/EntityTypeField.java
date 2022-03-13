//package com.revature.util;
//
//import java.lang.reflect.Field;
//
//import com.revature.annotations.Id;
//
///**
// * This class extracts the fields marked with @Entity annotation designed in
// * com.revature.annoations. Models the column that will be generated in the
// * database.
// *
// */
//
//public class EntityType {
//
//	
//	private Field field; // from java.lang.reflect
//
//	public EntityTypeClass(Field field) {
//		if (field.getAnnotation(Id.class) == null) {
//			throw new IllegalStateException(
//					"Cannot create ColumnField object. Provided field " + getName() + " is not annotated with @Id.");
//		}
//
//		this.field = field;
//	}
//
//	public String getName() {
//		return field.getName();
//	}
//
//	public Class<?> getType() {
//		return field.getType();
//	}
//
//	public String getColumnName() {
//		return field.getAnnotation(Id.class).columnName();
//	}
//}
