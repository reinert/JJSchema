/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.reinert.jjschema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Annotation for the attributes of JSON Schema
* @author reinert
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
public @interface SchemaProperty {
	String $ref() default "";
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
	long maxItems() default -1l;
	boolean uniqueItems() default false;
	int multipleOf() default 0;
	int minLength() default 0;
	long maxLength() default -1l;
}
