package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check annotation for CHECK constraint
 * boolean isCheck();
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {

	boolean isCheck() default true;
	boolean isSerial() default false;
	boolean isNullable() default false;
	boolean isUnique() default false;

	
}
