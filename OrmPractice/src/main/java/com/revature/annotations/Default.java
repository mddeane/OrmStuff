package com.revature.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check annotation for DEFAULT constraint
 *int defaultInt() default 0; 
 * String defaultString() default ""; 
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {

	int defaultInt() default 0; 
	double defaultDouble() default 0.0; 
	String defaultString() default "";
	char defaultChar() default '\u0000'; 
	
}
