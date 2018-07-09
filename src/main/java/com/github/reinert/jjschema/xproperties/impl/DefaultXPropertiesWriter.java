package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.Method;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.XPropertiesWriter;
import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * X Properties Writer Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesWriter implements XPropertiesWriter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Writes X Properties into schema.
     * 
     * 
     * @param schema
     *            Destination schema.
     * 
     * @param properties
     *            List of X Properties.
     */
    @Override
    public void writeXProperties(ObjectNode schema, List<XProperty> properties) {
        properties.forEach(property -> {
            final List<Object> propertyPath = property.getPropertyPath();
            final Object propertyKey = propertyPath.get(propertyPath.size() - 1);
            final Object propertyValue = property.getPropertyValue();

            JsonNode outerPtr = schema;
            for (int i = 0; i < propertyPath.size() - 1; ++i) {

                final Object outerKey = propertyPath.get(i);
                final Object innerKey = propertyPath.get(i + 1);

                JsonNode innerPtr;
                if (outerKey instanceof Integer) {
                    innerPtr = ((ArrayNode) outerPtr).get((Integer) outerKey);
                } else {
                    innerPtr = ((ObjectNode) outerPtr).get((String) outerKey);
                }

                prepareNodeStructure(outerPtr, outerKey, innerPtr, innerKey);

                if (outerKey instanceof Integer) {
                    innerPtr = ((ArrayNode) outerPtr).get((Integer) outerKey);
                } else {
                    innerPtr = ((ObjectNode) outerPtr).get((String) outerKey);
                }
                outerPtr = innerPtr;
            }
            setNodeValue(outerPtr, propertyKey, propertyValue);
        });
    }

    private static void setNodeValue(JsonNode ptr, Object key, Object value) {
        if (key instanceof Integer) {
            try {
                if (value == null) {
                    final Method remove = ArrayNode.class.getMethod("remove", int.class);
                    remove.invoke(ptr, (Integer) key);
                } else {
                    final Method insert = ArrayNode.class.getMethod("insert", int.class, value.getClass());
                    insert.invoke(ptr, (Integer) key, value);
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                if (value == null) {
                    final Method remove = ObjectNode.class.getMethod("remove", String.class);
                    remove.invoke(ptr, (String) key);
                } else {
                    final Method put = ObjectNode.class.getMethod("put", String.class, value.getClass());
                    put.invoke(ptr, (String) key, value);
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void prepareNodeStructure(JsonNode outerPtr, Object outerKey, JsonNode innerPtr, Object innerKey) {
        if (innerKey instanceof Integer) {
            if (!(innerPtr instanceof ArrayNode)) {
                final ArrayNode arrayNode = MAPPER.createArrayNode();
                if (outerKey instanceof Integer) {
                    ((ArrayNode) outerPtr).insert((Integer) outerKey, arrayNode);
                } else {
                    ((ObjectNode) outerPtr).set((String) outerKey, arrayNode);
                }
            }
        } else {
            if (!(innerPtr instanceof ObjectNode)) {
                final ObjectNode objectNode = MAPPER.createObjectNode();
                if (outerKey instanceof Integer) {
                    ((ArrayNode) outerPtr).insert((Integer) outerKey, objectNode);
                } else {
                    ((ObjectNode) outerPtr).set((String) outerKey, objectNode);
                }
            }
        }
    }
}
