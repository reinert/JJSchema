package com.github.reinert.jjschema.xproperties;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * X Property Operation
 * 
 * 
 * @author WhileTrueEndWhile
 * 
 */
public interface XPropertyOperation {

    /**
     * 
     * Applies a custom operation on a JSON schema (parent node).
     * 
     * 
     * @param schema
     *            JSON schema to apply a custom operation on.
     * 
     * @param value
     *            Generic string argument.
     * 
     * 
     * @return
     *         Value to replace the JSON schema (parent node).
     * 
     */
    Object applyXProperty(JsonNode schema, String value);

    /**
     * 
     * Method name (for reflective usage).
     * 
     */
    static final String APPlY_X_PROPERTY_NAME = "applyXProperty";

    /**
     * 
     * Method signature (for reflective usage).
     * 
     */
    static final Class<?>[] APPLY_X_PROPERTY_ARGS = { JsonNode.class, String.class };

    /**
     * 
     * Fall back STATIC method name (for reflective usage).
     *
     */
    static final String STATIC_FALL_BACK_NAME = "valueOf";

    /**
     * 
     * Fall back STATIC method signature (for reflective usage).
     * 
     */
    static final Class<?>[] STATIC_FALL_BACK_ARGS = { String.class };

}
