package com.github.reinert.jjschema.xproperties.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * Read Implementation
 * 
 * @author WhileTrueEndWhile
 */
final class ReadImpl {

    /**
     * Separator for path keys (Key0.Key1.Key2).
     */
    private static final String SEPARATOR_PROPERTY_PATH = "\\.";

    /**
     * Regular expression for integers.
     */
    private static final String REGEX_INTEGER = "^-?[0-9]+$";

    /**
     * Factory for arrays and objects.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Reads a property.
     * 
     * 
     * @param property
     *                 Property as string
     * 
     * @return Property as object
     */
    public static XProperty readProperty(String property) {
        final InputStream xPropertiesStream = new ByteArrayInputStream(property.getBytes());

        //
        // Read pairs via Properties
        //

        final Properties properties = new Properties();
        try {
            properties.load(xPropertiesStream);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        final Enumeration<?> propertyNames = properties.propertyNames();
        final List<XProperty> listOfProperties = new ArrayList<>();
        while (propertyNames.hasMoreElements()) {
            final String propertyPath = (String) propertyNames.nextElement();
            final String propertyValue = properties.getProperty(propertyPath);
            final XProperty xProperty = readProperty(propertyPath, propertyValue);
            listOfProperties.add(xProperty);
        }

        //
        // Exactly one pair is to be read
        //

        if (listOfProperties.size() != 1) {
            throw new IllegalArgumentException(Errors.ERROR_NOT_EXACTLY_ONE_PROPERTY);
        }
        return listOfProperties.get(0);
    }

    /**
     * Reads a property.
     * 
     * 
     * @param propertyPath
     *                      Property path as string.
     * 
     * @param propertyValue
     *                      Property value as string.
     * 
     * @return Property as object.
     */
    public static XProperty readProperty(String propertyPath, String propertyValue) {
        propertyPath = Objects.requireNonNull(propertyPath);

        //
        // k0.k1.k2 = value
        //

        final List<Object> propertyPathAsList = readPropertyPath(propertyPath);
        final Object propertyValueAsObject;
        propertyValueAsObject = readPropertyValue(propertyValue);
        return new DefaultXProperty(propertyPathAsList, propertyValueAsObject);
    }

    // -----------------------------------------------------------------------

    /**
     * Reads a property path.
     * 
     * 
     * @param propertyPath
     *                     Property path as string.
     * 
     * @return Property path as list of objects.
     */
    private static List<Object> readPropertyPath(String propertyPath) {

        //
        // k0.k1.k2
        //

        return Arrays.asList(propertyPath.split(SEPARATOR_PROPERTY_PATH)).stream()
                .map((String k) -> readPropertyPathKey(k)).collect(Collectors.toList());
    }

    /**
     * Reads a property path key.
     * 
     * 
     * @param propertyPathKey
     *                        Property path key as string.
     * 
     * @return Property path key as object (integer or string).
     */
    private static Object readPropertyPathKey(String propertyPathKey) {
        propertyPathKey = propertyPathKey.trim();

        //
        // Integer
        //

        if (propertyPathKey.matches(REGEX_INTEGER)) {
            return Integer.valueOf(propertyPathKey);
        }

        //
        // String
        //

        return propertyPathKey;
    }

    /**
     * Reads a property value.
     * 
     * 
     * @param propertyValue
     *                      Property value as string.
     * 
     * @return An object supported by ArrayNode.insert/ObjectNode.put.
     */
    private static Object readPropertyValue(String propertyValue) {
        try {
            return MAPPER.reader().readTree(propertyValue);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private ReadImpl() {
    }
}
