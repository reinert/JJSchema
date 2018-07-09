package com.github.reinert.jjschema.xproperties.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * X Property Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXProperty implements XProperty {

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
     *            Property path as list of objects.
     * 
     * @param propertyValue
     *            Property value as object (integer or string).
     */
    public DefaultXProperty(List<Object> propertyPath, Object propertyValue) {
        propertyPath = new ArrayList<>(propertyPath);
        propertyPath.forEach(propertyPathKey -> validatePropertyPathKey(propertyPathKey));

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
     * @return A boolean, an integer, a double or a string.
     */
    @Override
    public Object getPropertyValue() {
        return this.propertyValue;
    }

    /**
     * Validates one key of the property path.
     * 
     * 
     * @param propertyPathKey
     *            Property path key to validate.
     */
    private static void validatePropertyPathKey(Object propertyPathKey) {
        final boolean isInteger = propertyPathKey instanceof Integer;
        final boolean isString = propertyPathKey instanceof String;
        if ((!isInteger) && (!isString)) {
            throw new IllegalArgumentException("propertyPath");
        }
    }
}
