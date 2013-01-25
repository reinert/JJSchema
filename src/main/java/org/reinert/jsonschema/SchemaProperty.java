package org.reinert.jsonschema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
public @interface SchemaProperty {
	String $ref() default "";
	String $schema() default "";
	String id() default "";
	String title() default "";
	String description() default "";
	long maximum() default -1l;
	boolean exclusiveMaximum() default false;
	int minimum() default -1;
	boolean exclusiveMinimum() default false;
	String pattern() default "";
	boolean required() default false;
	String[] enums() default {};
	int minItems() default 0;
	boolean uniqueItems() default false;
}
