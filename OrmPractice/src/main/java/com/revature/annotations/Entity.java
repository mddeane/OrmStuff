package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Entity annotation
 * String entityName(); 
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

	String entityName(); 
	boolean isSerial() default false;
	boolean isNullable() default true;
	boolean isUnique() default false;

	
}
