package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.XPropertiesWriter;
import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * X Properties Writer Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesWriter implements XPropertiesWriter {

    /**
     * Factory for arrays and objects.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Field name of the runnable input.
     */
    private static String RUNNABLE_INPUT = "input";

    /**
     * Field name of the runnable output
     */
    private static String RUNNABLE_OUTPUT = "output";

    /**
     * Method name for insertions (ArrayNode)
     */
    private static String ARRAY_NODE_INSERT = "insert";

    /**
     * Method name for removing (ArrayNode)
     */
    private static String ARRAY_NODE_REMOVE = "insert";

    /**
     * Method name for insertions (ObjectNode)
     */
    private static String OBJECT_NODE_PUT = "put";

    /**
     * Method name for removing (ObjectNode)
     */
    private static String OBJECT_NODE_REMOVE = "remove";

    /**
     * Mode: Remove null values (true) or insert null (false).
     */
    private final boolean removeNullValues;

    public DefaultXPropertiesWriter() {
        this.removeNullValues = false;
    }

    public DefaultXPropertiesWriter(boolean removeNullValues) {
        this.removeNullValues = true;
    }

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
        schema = Objects.requireNonNull(schema);
        properties = Objects.requireNonNull(properties);

        for (int i = 0; i < properties.size(); ++i) {
            final XProperty property = properties.get(i);
            try {
                setNodeValue(schema, property);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("xProperties[" + i + "]", e);
            }

        }
    }

    /**
     * Sets one node value.
     * 
     * @param schema
     *            Node to apply value.
     * 
     * @param property
     *            Data source.
     */
    private void setNodeValue(ObjectNode schema, XProperty property) {
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
    }

    /**
     * Sets one node value.
     * 
     * @param ptr
     *            Node to apply value.
     * 
     * @param key
     *            Property name to apply value.
     * 
     * @param value
     *            Data source.
     */
    private void setNodeValue(JsonNode ptr, Object key, Object value) {
        if (value instanceof Runnable) {

            //
            // Call applyXProperty(ptr, value)
            //

            try {
                if (key instanceof Integer) {
                    value.getClass().getField(RUNNABLE_INPUT).set(value, ptr.get((Integer) key));
                } else {
                    value.getClass().getField(RUNNABLE_INPUT).set(value, ptr.get((String) key));
                }
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException();
            }
            ((Runnable) value).run();
            final Object output;
            try {
                output = value.getClass().getField(RUNNABLE_OUTPUT).get(value);
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException();
            }
            value = output;
        }
        if (key instanceof Integer) {

            //
            // Array
            //

            try {
                if (removeNullValues && value == null) {
                    final Method remove = ArrayNode.class.getMethod(ARRAY_NODE_REMOVE, int.class);
                    remove.invoke(ptr, (Integer) key);
                } else {
                    final Method insert;
                    if (value == null || value instanceof JsonNode) {
                        insert = ArrayNode.class.getMethod(ARRAY_NODE_INSERT, int.class, JsonNode.class);
                    } else {
                        insert = ArrayNode.class.getMethod(ARRAY_NODE_INSERT, int.class, value.getClass());
                    }
                    if (value == null) {
                        insert.invoke(ptr, (Integer) key, NullNode.instance);
                    } else {
                        insert.invoke(ptr, (Integer) key, value);
                    }
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        } else {

            //
            // Object
            //

            try {
                if (removeNullValues && value == null) {
                    final Method remove = ObjectNode.class.getMethod(OBJECT_NODE_REMOVE, String.class);
                    remove.invoke(ptr, (String) key);
                } else {
                    final Method put;
                    if (value == null || value instanceof JsonNode) {
                        put = ObjectNode.class.getMethod(OBJECT_NODE_PUT, String.class, JsonNode.class);
                    } else {
                        put = ObjectNode.class.getMethod(OBJECT_NODE_PUT, String.class, value.getClass());
                    }
                    if (value == null) {
                        put.invoke(ptr, (String) key, NullNode.instance);
                    } else {
                        put.invoke(ptr, (String) key, value);
                    }
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates arrays and objects.
     * 
     * @param outerPtr
     *            Outer pointer (immutable).
     * 
     * @param outerKey
     *            Outer key (integer or string).
     * 
     * @param innerPtr
     *            Inner pointer (mutable).
     * 
     * @param innerKey
     *            Inner key (integer or string).
     */
    private static void prepareNodeStructure(JsonNode outerPtr, Object outerKey, JsonNode innerPtr, Object innerKey) {
        if (innerKey instanceof Integer) {

            //
            // Array
            //

            if (!(innerPtr instanceof ArrayNode)) {
                final ArrayNode arrayNode = MAPPER.createArrayNode();
                if (outerKey instanceof Integer) {
                    ((ArrayNode) outerPtr).insert((Integer) outerKey, arrayNode);
                } else {
                    ((ObjectNode) outerPtr).set((String) outerKey, arrayNode);
                }
            }
        } else {

            //
            // Object
            //

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
