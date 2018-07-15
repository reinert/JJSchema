package com.github.reinert.jjschema.xproperties.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * X Property Implementation
 * 
 * @author WhileTrueEndWhile
 */
class DefaultXProperty implements XProperty {

    /**
     * Property Path
     */
    private final Object[] propertyPath;

    /**
     * Property Value
     */
    private final Object propertyValue;

    /**
     * Creates an immutable X Property instance.
     * 
     * 
     * @param propertyPath
     *                      Property path as list of objects.
     * 
     * @param propertyValue
     *                      Property value as object (integer or string).
     */
    public DefaultXProperty(List<Object> propertyPath, Object propertyValue) {

        propertyPath = Objects.requireNonNull(propertyPath);
        if (propertyPath.size() < 1) {
            throw new IllegalArgumentException(Errors.ERROR_PROPERTY_PATH_EMPTY);
        }
        if (!(propertyPath.get(0) instanceof String)) {
            throw new IllegalArgumentException(Errors.ERROR_PROPERTY_PATH_FIRST_KEY_NOT_STRING);
        }
        if (((String) propertyPath.get(0)).isEmpty()) {
            throw new IllegalArgumentException(Errors.ERROR_PROPERTY_PATH_FIRST_KEY_EMPTY);
        }
        propertyPath.forEach(propertyPathKey -> validatePropertyPathKey(propertyPathKey));
        propertyPath = new ArrayList<>(propertyPath);
        this.propertyPath = propertyPath.toArray();
        this.propertyValue = propertyValue;
    }

    /**
     * Gets the property path.
     * 
     * 
     * @return A List of integers and strings.
     */
    @Override
    public List<Object> getPropertyPath() {

        return Arrays.asList(this.propertyPath);
    }

    /**
     * Gets the property value.
     * 
     * 
     * @return An object supported by ArrayNode.insert/ObjectNode.put.
     */
    @Override
    public Object getPropertyValue() {

        return this.propertyValue;
    }

    // -----------------------------------------------------------------------

    /**
     * Validates one key of the property path.
     * 
     * 
     * @param propertyPathKey
     *                        Property path key to validate.
     */
    private static void validatePropertyPathKey(Object propertyPathKey) {

        final boolean isInteger = propertyPathKey instanceof Integer;
        final boolean isString = propertyPathKey instanceof String;
        if ((!isInteger) && (!isString)) {
            throw new IllegalArgumentException(Errors.ERROR_PROPERTY_PATH_KEY_TYPE);
        }
    }
}
