package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NotNull annotation for NOT NULL constraint
 * boolean isNotNull();
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

	boolean isSerial() default false;
	boolean isNullable() default true;
	boolean isUnique() default false;

}
