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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.XProperties;
import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * Read Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class ReadImpl {

    //
    // X Properties Files Errors
    //
    private static final String ERROR_RESOURCE_NOT_FOUND = "Could not find resource";
    private static final String ERROR_RESOURCE_IO_ERROR = "Could not load resource";

    //
    // X Properties Errors
    //
    private static final String ERROR_NOT_EXACTLY_ONE_PROPERTY = "Exactly one property must be defined";

    /**
     * Separator for path keys (Key0.Key1.Key2).
     */
    private static final String SEPARATOR_PROPERTY_PATH = "\\.";

    /**
     * Regular expression for integers.
     */
    private static final String REGEX_INTEGER = "^-?[0-9]+$";

    /**
     * JSON Schema properties.
     */
    private static final String JSON_SCHEMA_PROPERTIES = "properties";

    /**
     * JSON Schema required.
     */
    private static final String JSON_SCHEMA_REQUIRED = "required";

    /**
     * JSON Schema required.
     */
    private static final String JSON_SCHEMA_DEFAULT = "default";

    /**
     * Factory for arrays and objects.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Reads X Properties from an annotation instance.
     * 
     * @param attributes
     *                   An annotation instance.
     * 
     * @return A list of X Properties.
     */
    public static List<XProperty> readXProperties(XProperties attributes) {
        final List<XProperty> listOfProperties = new ArrayList<>();

        //
        // Read X Properties Files
        //

        listOfProperties.addAll(ReadImpl.readXPropertiesFiles(attributes));

        //
        // Read X Properties
        //

        if (attributes == null || attributes.value() == null) {
            return listOfProperties;
        }
        final String[] xProperties = attributes.value();
        for (int i = 0; i < xProperties.length; ++i) {
            final String xProperty = xProperties[i];
            final XProperty property;
            try {
                property = readProperty(xProperty);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("xProperties[" + i + "]", e);
            }
            listOfProperties.add(property);
        }
        return listOfProperties;
    }

    /**
     * 
     * Reads X Properties from a JsonProperty annotation instance.
     * 
     * 
     * @param type
     *                     The class containing the field read from.
     * 
     * @param schema
     *                     Schema of the class containing the field to read from.
     * 
     * @param fieldName
     *                     Name of the field to read X Properties from.
     * 
     * @param jsonProperty
     *                     A JsonProperty annotation instance to read X Properties
     *                     from.
     * 
     * @return A list of X Properties
     * 
     */
    public static List<XProperty> readXProperties(Class<?> type, ObjectNode schema, String fieldName,
            JsonProperty jsonProperty) {
        final List<XProperty> listOfProperties = new ArrayList<>();
        final boolean required = jsonProperty.required();
        final String defaultValue = jsonProperty.defaultValue();
        final String value = jsonProperty.value();

        //
        // @JsonProperty(required=true)
        //

        if (required) {
            final ArrayNode requiredArray = (ArrayNode) schema.get(JSON_SCHEMA_REQUIRED);
            final List<String> requiredList = new ArrayList<>();

            if (requiredArray != null) {
                for (int i = 0; i < requiredArray.size(); ++i) {
                    requiredList.add(requiredArray.get(i).asText());
                }
            }

            //
            // Check if required entry already exists...
            //

            final int index = requiredList.indexOf(fieldName);
            if (value.isEmpty()) {
                if (index < 0) {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, -1), fieldName));
                } else {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, index), fieldName));
                }
            } else {
                if (index < 0) {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, -1), value));
                } else {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, index), value));
                }

            }
        }

        //
        // @JsonProperty(defaultValue=???)
        //

        if (!defaultValue.isEmpty()) {
            listOfProperties.add(new DefaultXProperty(
                    Arrays.asList(JSON_SCHEMA_PROPERTIES, fieldName, JSON_SCHEMA_DEFAULT), defaultValue));
        }

        //
        // @JsonProperty(???)
        //

        if (!value.isEmpty()) {
            listOfProperties.add(
                    new DefaultXProperty(Arrays.asList(JSON_SCHEMA_PROPERTIES, value),
                            schema.get(JSON_SCHEMA_PROPERTIES).get(fieldName)));
            listOfProperties.add(
                    new DefaultXProperty(Arrays.asList(JSON_SCHEMA_PROPERTIES, fieldName), null));
        }
        return listOfProperties;
    }

    // -----------------------------------------------------------------------

    /**
     * Reads X Properties from an annotation instance.
     * 
     * @param attributes
     *                   An annotation instance.
     * 
     * @return A list of X Properties.
     */
    private static List<XProperty> readXPropertiesFiles(XProperties attributes) {
        final List<XProperty> listOfProperties = new ArrayList<>();
        if (attributes == null || attributes.files() == null) {
            return listOfProperties;
        }
        final String[] xPropertiesFiles = attributes.files();
        for (int i = 0; i < xPropertiesFiles.length; ++i) {
            final String xPropertiesFileName = xPropertiesFiles[i];

            //
            // Load file via System
            //

            final InputStream xPropertiesFile = System.class.getResourceAsStream(xPropertiesFileName);
            if (xPropertiesFile == null) {
                throw new IllegalArgumentException(ERROR_RESOURCE_NOT_FOUND + ": " + xPropertiesFileName);
            }

            //
            // Read via Properties
            //

            final Properties properties = new Properties();
            try {
                properties.load(xPropertiesFile);
            } catch (IOException e) {
                throw new IllegalArgumentException(ERROR_RESOURCE_IO_ERROR + ": " + xPropertiesFileName, e);
            }
            final Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String propertyPath = (String) propertyNames.nextElement();
                final String propertyValue = properties.getProperty(propertyPath);
                final XProperty xProperty = readProperty(propertyPath, propertyValue);
                listOfProperties.add(xProperty);
            }
        }
        return listOfProperties;
    }

    /**
     * Reads a property.
     * 
     * 
     * @param property
     *                 Property as string
     * 
     * @return Property as object
     */
    private static XProperty readProperty(String property) {
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
            throw new IllegalArgumentException(ERROR_NOT_EXACTLY_ONE_PROPERTY);
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
    private static XProperty readProperty(String propertyPath, String propertyValue) {
        propertyPath = Objects.requireNonNull(propertyPath);

        //
        // k0.k1.k2 = value
        //

        final List<Object> propertyPathAsList = readPropertyPath(propertyPath);
        final Object propertyValueAsObject;
        propertyValueAsObject = readPropertyValue(propertyValue);
        return new DefaultXProperty(propertyPathAsList, propertyValueAsObject);
    }

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
}
