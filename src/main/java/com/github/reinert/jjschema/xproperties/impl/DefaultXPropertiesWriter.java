package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String PTR = "$ptr";
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
            final Map<String, JsonNode> outerContainer = new HashMap<>();
            outerContainer.put(PTR, schema);

            for (int i = 0; i < propertyPath.size() - 1; ++i) {
                final JsonNode outerPtr = outerContainer.get(PTR);
                final Object outerKey = propertyPath.get(i);
                JsonNode innerPtr;
                if (outerKey instanceof Integer) {
                    innerPtr = ((ArrayNode) outerPtr).get((Integer) outerKey);
                } else {
                    innerPtr = ((ObjectNode) outerPtr).get((String) outerKey);
                }
                final Object innerKey = propertyPath.get(i + 1);
                processNode(outerPtr, outerKey, innerPtr, innerKey);
                if (outerKey instanceof Integer) {
                    innerPtr = ((ArrayNode) outerPtr).get((Integer) outerKey);
                } else {
                    innerPtr = ((ObjectNode) outerPtr).get((String) outerKey);
                }
                outerContainer.put(PTR, innerPtr);
            }
            if (propertyKeyN instanceof Integer) {
                final ArrayNode ptrN = (ArrayNode) outerContainer.get(PTR);
                try {
                    final Method insertMethod = ArrayNode.class.getMethod("insert", int.class,
                            propertyValue.getClass());
                    insertMethod.invoke(ptrN, (Integer) propertyKeyN, propertyValue);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            } else {
                final ObjectNode ptrN = (ObjectNode) outerContainer.get(PTR);
                try {
                    final Method putMethod = ObjectNode.class.getMethod("put", String.class, propertyValue.getClass());
                    putMethod.invoke(ptrN, (String) propertyKeyN, propertyValue);
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
