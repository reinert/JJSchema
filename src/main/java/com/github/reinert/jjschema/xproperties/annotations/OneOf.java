package com.github.reinert.jjschema.xproperties.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *
 *
 * Indicates that the properties (class attributes)
 * of the field's type (class)
 * should be converted to an oneOf schema.
 * 
 * 
 * <br />
 * <code>
 * oneOf = propertiesToOneOf(field.properties)
 * </code>
 * <br />
 *
 *
 * The result is a schema that is supported by default.
 * Each class attribute of the field's type
 * gets an item of the oneOf list.
 *
 *
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface OneOf {
}
