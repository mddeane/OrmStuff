package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JoinColumn annotation for foreign keys
 * String columnName();
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {

	String columnName();
	boolean isSerial() default false;
	boolean isNullable() default true;
	boolean isUnique() default false;

}
