package com.github.reinert.jjschema.xproperties;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * X Properties
 * 
 * 
 * @author WhileTrueEndWhile
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD })
public @interface XProperties {

    /**
     * 
     * A list of X Properties.
     * 
     */
    String[] value() default {};

    /**
     * 
     * A list of X Properties Files.
     * 
     */
    String[] files() default {};

}
