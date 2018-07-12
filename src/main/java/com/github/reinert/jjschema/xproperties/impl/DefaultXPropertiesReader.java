package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.xproperties.XPropertiesReader;
import com.github.reinert.jjschema.xproperties.XProperty;
import com.github.reinert.jjschema.xproperties.XPropertyOperation;

/**
 * X Properties Reader Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesReader implements XPropertiesReader {

    //
    // Errors
    //
    private static final String ERROR_PROPERTY_HAS_NO_SEPARATOR = "Property has no separator (=)";
    private static final String ERROR_PROPERTY_VALUE_HAS_NO_SEPARATOR = "Property value has no separator (:) and is not null, a boolean or an integer";
    private static final String ERROR_CLASS_NOT_FOUND = "Custom property value factory/operation class not found";
    private static final String ERROR_METHOD_NOT_FOUND = "Custom property value factory/operation method not found or has error";

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
     * Regular expression for null values.
     */
    private static final String REGEX_NULL = "^null$";

    /**
     * Regular expression for booleans.
     */
    private static final String REGEX_BOOLEAN = "^(false|true)$";

    /**
     * Regular expression for integers.
     */
    private static final String REGEX_INTEGER = "^[0-9]+$";

    /**
     * 
     * Reads X Properties from a class.
     * 
     * @param type
     *            A class to read X Properties from.
     * 
     * @return List of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(Class<?> type) {
        final Attributes attributes = type.getAnnotation(Attributes.class);
        return readXProperties(attributes);
    }

    /**
     * 
     * Reads X Properties from a field.
     * 
     * 
     * @param accessibleObj
     *            A field to read X Properties from.
     * 
     * @return List of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(AccessibleObject accessibleObj) {
        final Attributes attributes = accessibleObj.getAnnotation(Attributes.class);
        return readXProperties(attributes);
    }

    /**
     * 
     * Reads X Properties from an annotation instance.
     * 
     * @param attributes
     *            Annotation instance.
     * 
     * @return List of X Properties.
     */
    private static List<XProperty> readXProperties(Attributes attributes) {
        final List<XProperty> listOfProperties = new ArrayList<>();
        listOfProperties.addAll(DefaultXPropertiesFileReader.readXProperties(attributes));

        if (attributes == null || attributes.xProperties() == null) {
            return listOfProperties;
        }

        final String[] xProperties = attributes.xProperties();
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
     * Reads a property.
     * 
     * 
     * @param property
     *            Property as string
     * 
     * @return Property as object
     */
    private static XProperty readProperty(String property) {
        final int indexOfSeparator = property.indexOf(SEPARATOR_PROPERTY);
        if (indexOfSeparator < 0) {
            throw new IllegalArgumentException(ERROR_PROPERTY_HAS_NO_SEPARATOR);
        }
        final String propertyPath = property.substring(0, indexOfSeparator);
        final String propertyValue = property.substring(indexOfSeparator + 1);
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
    public static XProperty readProperty(String propertyPath, String propertyValue) {

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
     *            Property path as string.
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
     *            Property path key as string.
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
     *            Property value as string.
     * 
     * @return An object supported by ArrayNode.insert/ObjectNode.put.
     */
    private static Object readPropertyValue(String propertyValue) {
        propertyValue = propertyValue.trim();

        //
        // value without ':'
        //

        if (propertyValue.matches(REGEX_NULL)) {
            return null;
        }
        if (propertyValue.matches(REGEX_BOOLEAN)) {
            return Boolean.valueOf(propertyValue);
        }
        if (propertyValue.matches(REGEX_INTEGER)) {
            return Integer.valueOf(propertyValue);
        }

        //
        // value with ':'
        //

        final int index = propertyValue.indexOf(SEPARATOR_PROPERTY_VALUE);
        if (index < 0) {
            throw new IllegalArgumentException(ERROR_PROPERTY_VALUE_HAS_NO_SEPARATOR);
        }
        final String type = propertyValue.substring(0, index).trim();
        final String value = propertyValue.substring(index + 1).trim();
        if (type.isEmpty()) {
            return value;
        }

        return callStaticFactoryMethod(type, value);
    }

    /**
     * Calls applyXProperty or valueOf.
     * 
     * @param type
     *            Name of the class to invoke applyXProperty or valueOf.
     *
     * @param value
     *            Value to pass to applyXProperty or valueOf.
     *
     * @return An object supported by ArrayNode.insert/ObjectNode.put.
     */
    private static Object callStaticFactoryMethod(String type, String value) {
        final Class<?> typeClass;
        try {
            typeClass = Class.forName(type);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(ERROR_CLASS_NOT_FOUND, e);
        }

        //
        // Try to call the custom static factory method:
        //
        // Object applyXProperty(JsonNode schema, String value)
        //

        {
            try {
                final Method applyXProperty = typeClass.getMethod(
                        XPropertyOperation.APPlY_X_PROPERTY_NAME,
                        XPropertyOperation.APPLY_X_PROPERTY_ARGS);

                final Object typeInstance = typeClass.getConstructor().newInstance();

                final Runnable runnable = new Runnable() {
                    public JsonNode input = null;

                    @SuppressWarnings("unused")
                    public Object output = null;

                    @Override
                    public void run() {
                        try {
                            final Object valueObject = applyXProperty.invoke(typeInstance, input, value);
                            this.output = valueObject;
                        } catch (ReflectiveOperationException e) {
                            throw new IllegalArgumentException(ERROR_METHOD_NOT_FOUND, e);
                        }
                    }
                };
                return runnable;
            } catch (ReflectiveOperationException e) {
                // Use default valueOf below...
            }
        }

        //
        // Call the default static factory method:
        //
        // Object valueOf (String value)
        //

        {
            try {

                final Method valueOf = typeClass.getMethod(
                        XPropertyOperation.STATIC_FALL_BACK_NAME,
                        XPropertyOperation.STATIC_FALL_BACK_ARGS);

                final Object valueObject = valueOf.invoke(null, value);
                return valueObject;
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException(ERROR_METHOD_NOT_FOUND, e);
            }
        }
    }
}
