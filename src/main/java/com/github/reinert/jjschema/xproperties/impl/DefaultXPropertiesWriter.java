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

            final Object propertyKeyN = propertyPath.get(propertyPath.size() - 1);
            final Object propertyValue = property.getPropertyValue();

            JsonNode ptrN = schema;

            for (int i = 0; i < propertyPath.size() - 1; ++i) {

                final Object keyN = propertyPath.get(i);
                final Object keyI = propertyPath.get(i + 1);

                JsonNode ptrI;

                if (keyN instanceof Integer) {
                    ptrI = ((ArrayNode) ptrN).get((Integer) keyN);
                } else {
                    ptrI = ((ObjectNode) ptrN).get((String) keyN);
                }

                processNode(ptrN, keyN, ptrI, keyI);

                if (keyN instanceof Integer) {
                    ptrI = ((ArrayNode) ptrN).get((Integer) keyN);
                } else {
                    ptrI = ((ObjectNode) ptrN).get((String) keyN);
                }

                ptrN = ptrI;
            }
            if (propertyKeyN instanceof Integer) {
                try {
                    if (propertyValue == null) {
                        final Method remove = ArrayNode.class.getMethod("remove", int.class);
                        remove.invoke(ptrN, (Integer) propertyKeyN);
                    } else {
                        final Method insert = ArrayNode.class.getMethod("insert", int.class, propertyValue.getClass());
                        insert.invoke(ptrN, (Integer) propertyKeyN, propertyValue);
                    }
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    if (propertyValue == null) {
                        final Method remove = ObjectNode.class.getMethod("remove", String.class);
                        remove.invoke(ptrN, (String) propertyKeyN);
                    } else {
                        final Method put = ObjectNode.class.getMethod("put", String.class, propertyValue.getClass());
                        put.invoke(ptrN, (String) propertyKeyN, propertyValue);
                    }
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void processNode(JsonNode outerPtr, Object outerKey, JsonNode innerPtr, Object innerKey) {
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
