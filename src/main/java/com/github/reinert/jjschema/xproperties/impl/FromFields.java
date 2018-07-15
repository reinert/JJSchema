package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * Abstract From Fields Read Implementation
 * 
 * @author WhileTrueEndWhile
 */
abstract class FromFields {
    /**
     * JSON Schema required.
     */
    protected static final String JSON_SCHEMA_REQUIRED = "required";

    /**
     * JSON Schema properties.
     */
    protected static final String JSON_SCHEMA_PROPERTIES = "properties";

    /**
     * JSON Schema required.
     */
    protected static final String JSON_SCHEMA_DEFAULT = "default";
    
    /**
     * JSON Schema oneOf.
     */
    protected static final String JSON_SCHEMA_TYPE = "type";

    /**
     * JSON Schema oneOf.
     */
    protected static final String JSON_SCHEMA_ONE_OF = "oneOf";
    
    /**
     * JSON Schema additionalProperties
     */
    protected static final String JSON_SCHEMA_ADDITIONAL_PROPERTIES = "additionalProperties";

    /**
     * Reads X Properties from fields.
     * 
     * @param type
     *               The class containing the fields to read from.
     * 
     * @param schema
     *               Schema of the class containing the fields to read from.
     * 
     * @return A list of X Properties
     */
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

                    final AccessibleObject field = ptr.getDeclaredField(fieldName);
                    listOfProperties.addAll(readXProperties(ptr, schema, fieldName, field));
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
                        final AccessibleObject method = ptr.getDeclaredMethod(methodName);
                        listOfProperties.addAll(readXProperties(ptr, schema, fieldName, method));
                        break superClassLoop;
                    } catch (NoSuchMethodException ignored) {
                        // ignored.printStackTrace();

                        //
                        // Throw the field error to always use the original name
                        // of the field!
                        //

                        if (ptr.getSuperclass() == null) {
                            throw new IllegalArgumentException(Errors.ERROR_FIELD_NOT_FOUND + " " + fieldName, e);
                        }
                    }
                }
                ptr = ptr.getSuperclass();
            }
        }
        return listOfProperties;
    }

    /**
     * 
     * Reads X Properties from one field.
     * 
     * 
     * @param type
     *                      The class containing the field read from.
     * 
     * @param schema
     *                      Schema of the class containing the field to read from.
     * 
     * @param fieldName
     *                      Name of the field to read X Properties from.
     * 
     * @param accessibleObj
     *                      A field to read X Properties from.
     * 
     * @return A list of X Properties
     * 
     */
    protected abstract List<XProperty> readXProperties(Class<?> type, ObjectNode schema, String fieldName,
            AccessibleObject accessibleObj);

    protected FromFields() {
    }
}
