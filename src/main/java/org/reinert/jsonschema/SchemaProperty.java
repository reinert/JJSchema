package org.reinert.jsonschema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SchemaProperty {
	String id() default "";
	String title() default "";
	String description() default "";
	long maximun() default -1l;
	int minimum() default -1;
	String pattern() default "";
	boolean required() default false;
	String[] enums() default {};
}
