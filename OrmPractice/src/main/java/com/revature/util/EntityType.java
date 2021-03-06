package com.revature.util;

import java.lang.reflect.Type;

import com.revature.annotations.Entity;

/**
 * This class extracts the fields marked with @Entity annotation
 * designed in com.revature.annoations.
 * Models the entity that will be generated in the database.
 *
 */

public class EntityType {

	private Type type;
	 
	public EntityType(Type type) {
		
		if (type.getClass().getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create EntityType object. Provided entity " + getName() + "is not annotated with @Entity.");
		}
		
		this.type = type;
	}
	
	public String getName() {
		return type.getTypeName();
	}


}
