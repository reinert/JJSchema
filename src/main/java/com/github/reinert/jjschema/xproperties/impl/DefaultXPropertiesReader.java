package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.XProperties;
import com.github.reinert.jjschema.xproperties.XPropertiesReader;
import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * X Properties Reader Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesReader implements XPropertiesReader {

    //
    // Errors
    //
    private static final String ERROR_FIELD_NOT_FOUND = "Could not find field for JSON schema property:";

    /**
     * JSON Schema properties.
     */
    private static final String JSON_SCHEMA_PROPERTIES = "properties";

    /**
     * Reads X Properties from a class.
     * 
     * @param type
     *             A class to read X Properties from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(Class<?> type) {
        type = Objects.requireNonNull(type);

        final XProperties attributes = type.getAnnotation(XProperties.class);
        return ReadImpl.readXProperties(attributes);
    }

    /**
     * Reads X Properties from a field.
     * 
     * 
     * @param accessibleObj
     *                      A field to read X Properties from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(AccessibleObject accessibleObj) {
        accessibleObj = Objects.requireNonNull(accessibleObj);

        final XProperties attributes = accessibleObj.getAnnotation(XProperties.class);
        return ReadImpl.readXProperties(attributes);
    }

    /**
     * Reads X Properties from JsonProperty annotation instances.
     * 
     * @param type
     *               The class containing the fields to read from.
     * 
     * @param schema
     *               Schema of the class containing the fields to read from.
     * 
     * @return A list of X Properties
     */

    @Override
    public List<XProperty> readXProperties(Class<?> type, ObjectNode schema) {
        type = Objects.requireNonNull(type);
        schema = Objects.requireNonNull(schema);

        final List<XProperty> listOfProperties = new ArrayList<>();
        final ObjectNode properties = (ObjectNode) schema.get(JSON_SCHEMA_PROPERTIES);
        if (properties == null) {
            return listOfProperties;
        }

        //
        // Object.keys(schema.properties).forEach(...))
        //

        final Iterator<String> fieldNames = properties.fieldNames();
        while (fieldNames.hasNext()) {
            final String fieldName = fieldNames.next();
            Class<?> ptr = type;

            //
            // Find corresponding field or method...
            //
            // On error: Loop up super class!
            //

            superClassLoop: while (ptr != null) {
                try {

                    //
                    // Field
                    //

                    final Field field = ptr.getDeclaredField(fieldName);
                    final JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    if (jsonProperty != null) {
                        listOfProperties.addAll(ReadImpl.readXProperties(ptr, schema, fieldName, jsonProperty));
                    }
                    break superClassLoop;
                } catch (NoSuchFieldException e) {
                    // e.printStackTrace();

                    //
                    // Method (Fall Back)
                    //

                    final String methodName;
                    if (fieldName.isEmpty() || fieldName.equals("get")) {
                        methodName = fieldName;
                    } else {
                        methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    }
                    try {
                        final Method method = ptr.getDeclaredMethod(methodName);
                        final JsonProperty jsonProperty = method.getAnnotation(JsonProperty.class);
                        if (jsonProperty != null) {
                            listOfProperties.addAll(ReadImpl.readXProperties(ptr, schema, fieldName, jsonProperty));
                        }
                        break superClassLoop;
                    } catch (NoSuchMethodException ignored) {
                        // ignored.printStackTrace();

                        //
                        // Throw the field error to always use the original name
                        // of the field!
                        //

                        if (ptr.getSuperclass() == null) {
                            throw new IllegalArgumentException(ERROR_FIELD_NOT_FOUND + " " + fieldName, e);
                        }
                    }
                }
                ptr = ptr.getSuperclass();
            }
        }
        return listOfProperties;
    }
}
