package com.zero.core.ddd.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
public @interface DDD {
	
	String name() default "default";
	
	String nameSpace() default "default namespace";
	
}
