package com.github.reinert.jjschema.xproperties.impl;

/**
 * Errors
 * 
 * @author WhileTrueEndWhile
 */
final class Errors {

    //
    // X Property
    //

    public static final String ERROR_PROPERTY_PATH_EMPTY = "Property path is empty";
    public static final String ERROR_PROPERTY_PATH_FIRST_KEY_NOT_STRING = "First key of the property path is not a string";
    public static final String ERROR_PROPERTY_PATH_FIRST_KEY_EMPTY = "First key of property path is empty";
    public static final String ERROR_PROPERTY_PATH_KEY_TYPE = "At least one key of the property path is not an integer or a string";

    //
    // Read
    //

    public static final String ERROR_NOT_EXACTLY_ONE_PROPERTY = "Exactly one property must be defined";

    // ...from JsonProperty

    public static final String ERROR_FIELD_NOT_FOUND = "Could not find field for JSON schema property:";

    // ...from File

    public static final String ERROR_RESOURCE_NOT_FOUND = "Could not find resource";
    public static final String ERROR_RESOURCE_IO_ERROR = "Could not load resource";

    //
    // Write Errors
    //

    public static final String ERROR_INDEX_OUT_OF_BOUNDS = "An array index is negative (after normalization)";

    private Errors() {
    }
}
