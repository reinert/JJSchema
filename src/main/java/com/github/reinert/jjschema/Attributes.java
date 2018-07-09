/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.reinert.jjschema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for the attributes of JSON Schema
 *
 * @author reinert
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
public @interface Attributes {
    String $ref() default "";

    String id() default "";

    String title() default "";

    String description() default "";

    long maximum() default Long.MIN_VALUE;

    boolean exclusiveMaximum() default false;

    int minimum() default Integer.MAX_VALUE;

    boolean exclusiveMinimum() default false;

    String pattern() default "";

    String format() default "";

    boolean required() default false;

    String[] enums() default {};

    int minItems() default 0;

    long maxItems() default -1L;

    boolean uniqueItems() default false;

    int multipleOf() default 0;

    int minLength() default 0;

    long maxLength() default -1L;
    
    boolean readonly() default false;

    boolean additionalProperties() default true;
    
    String[] xProperties() default {};
}
