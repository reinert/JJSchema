package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.annotations.OneOf;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * From OneOf Read Implementation
 * 
 * Provides support of <code>@OneOf()</code>.
 * 
 * @author WhileTrueEndWhile
 */
class FromOneOf extends FromFields {

    /**
     * Fly Weight Instance
     */
    public static final FromOneOf instance = new FromOneOf();

    /**
     * Factory for arrays and Objects.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected List<XProperty> readXProperties(Class<?> type, ObjectNode schema, String fieldName, // <====
            AccessibleObject accessibleObj) {

        final List<XProperty> listOfProperties = new ArrayList<>();
        if (accessibleObj.getAnnotation(OneOf.class) == null) {
            return listOfProperties;
        }
        final ObjectNode outerPtr = (ObjectNode) getProperty(schema, fieldName); // <====
        final JsonProperty jsonProperty = accessibleObj.getAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            final String value = jsonProperty.value();
            if (!value.isEmpty()) {
                fieldName = value; // <====
            }
        }
        final Iterator<String> fieldNames = outerPtr.get(JSON_SCHEMA_PROPERTIES).fieldNames();
        while (fieldNames.hasNext()) {
            final String innerKey = fieldNames.next();
            final ObjectNode innerPtr = (ObjectNode) getProperty(outerPtr, innerKey);
            final ArrayNode required = MAPPER.createArrayNode();
            required.add(innerKey);
            final ObjectNode props = MAPPER.createObjectNode();
            props.set(innerKey, innerPtr);
            final ObjectNode option = MAPPER.createObjectNode();
            option.put(JSON_SCHEMA_TYPE, "object");
            option.set(JSON_SCHEMA_REQUIRED, required);
            option.set(JSON_SCHEMA_PROPERTIES, props);
            option.put(JSON_SCHEMA_ADDITIONAL_PROPERTIES, false);
            listOfProperties.add(
                    new DefaultXProperty(Arrays.asList(JSON_SCHEMA_PROPERTIES, fieldName, JSON_SCHEMA_ONE_OF, -1),
                            option));
            listOfProperties.add(new DefaultXProperty(
                    Arrays.asList(JSON_SCHEMA_PROPERTIES, fieldName, JSON_SCHEMA_PROPERTIES, innerKey), null));
        }
        return listOfProperties;
    }

    private static JsonNode getProperty(JsonNode schema, String fieldName) {

        JsonNode ptr = schema;
        ptr = ptr.get(JSON_SCHEMA_PROPERTIES);
        ptr = ptr.get(fieldName);
        return ptr;
    }

    protected FromOneOf() {
    }
}
