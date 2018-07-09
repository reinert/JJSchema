package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.xproperties.XPropertiesReader;
import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * X Properties Reader Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesReader implements XPropertiesReader {

    /**
     * Separator for properties (Key=Value).
     */
    private static final String SEPARATOR_PROPERTY = "=";

    /**
     * Separator for path keys (Key0.Key1.Key2).
     */
    private static final String SEPARATOR_PROPERTY_PATH = "\\.";

    /**
     * Separator for type and value (type:value)
     */
    private static final String SEPARATOR_PROPERTY_VALUE = ":";

    /**
     * Regular expression for integers.
     */
    private static final String REGEX_INTEGER = "^[0-9]+$";

    /**
     * Regular expression for null values.
     */
    private static final String REGEX_NULL = "^null$";

    /**
     * Reads X Properties from an annotation instance.
     * 
     * 
     * @param attributes
     *            Annotation instance.
     * 
     * @return List of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(Attributes attributes) {
        final List<XProperty> listOfProperties = new ArrayList<>();
        if (attributes == null || attributes.xProperties() == null) {
            return listOfProperties;
        }
        final String[] xProperties = attributes.xProperties();
        for (int i = 0; i < xProperties.length; ++i) {
            final String xProperty = xProperties[i];
            final XProperty property = readProperty(xProperty);
            listOfProperties.add(property);
        }
        return listOfProperties;
    }

    /**
     * Reads a property.
     * 
     * 
     * @param property
     *            Property as string
     * 
     * @return Property as object
     */
    private XProperty readProperty(String property) {
        String propertyPath = property;
        String propertyValue = null;
        final int indexOfSeparator = property.indexOf(SEPARATOR_PROPERTY);
        if (indexOfSeparator > -1) {
            propertyPath = property.substring(0, 0 + indexOfSeparator);
            propertyValue = property.substring(indexOfSeparator + 1);
        }
        return readProperty(propertyPath, propertyValue);
    }

    /**
     * Reads a property.
     * 
     * 
     * @param propertyPath
     *            Property path as string
     * 
     * @param propertyValue
     *            Property value as string
     * 
     * @return Property as object
     */
    private static XProperty readProperty(String propertyPath, String propertyValue) {
        final List<Object> propertyPathAsList = readPropertyPath(propertyPath);
        final Object propertyValueAsObject = readPropertyValue(propertyValue);
        return new DefaultXProperty(propertyPathAsList, propertyValueAsObject);
    }

    /**
     * Reads a property path.
     * 
     * 
     * @param propertyPath
     *            Property path as string.
     * 
     * @return Property path as list of objects.
     */
    private static List<Object> readPropertyPath(String propertyPath) {
        return Arrays.asList(propertyPath.split(SEPARATOR_PROPERTY_PATH)).stream()
                .map((String k) -> readPropertyPathKey(k)).collect(Collectors.toList());
    }

    /**
     * Reads a property path key.
     * 
     * 
     * @param propertyPathKey
     *            Property path key as string.
     * 
     * @return Property path key as object (integer or string).
     */
    private static Object readPropertyPathKey(String propertyPathKey) {
        propertyPathKey = propertyPathKey.trim();
        if (propertyPathKey.matches(REGEX_INTEGER)) {
            return Integer.valueOf(propertyPathKey);
        }
        return propertyPathKey;
    }

    /**
     * Reads a property value.
     * 
     * 
     * @param propertyValue
     *            Property value as string.
     * 
     * @return Property value as object (boolean, integer, double or string).
     */
    private static Object readPropertyValue(String propertyValue) {
        propertyValue = propertyValue.trim();
        if (propertyValue.matches(REGEX_NULL)) {
            return null;
        }

        final int index = propertyValue.indexOf(SEPARATOR_PROPERTY_VALUE);
        if (index < 0) {
            return propertyValue;
        }

        final String type = propertyValue.substring(0, index).trim();
        final String value = propertyValue.substring(index + 1).trim();

        try {
            final Class<?> typeClass = Class.forName(type);
            if (typeClass == String.class) {
                return value;
            }

            final Method valueOf = typeClass.getMethod("valueOf", String.class);
            final Object valueObject = valueOf.invoke(null, value);

            return valueObject;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
