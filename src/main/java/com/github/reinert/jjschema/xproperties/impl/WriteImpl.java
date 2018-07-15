package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * Write Implementation
 * 
 * @author WhileTrueEndWhile
 */
final class WriteImpl {

    /**
     * Factory for arrays and objects.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Method name for reflective insertions (ArrayNode).
     */
    private static String ARRAY_ADD = "add";

    /**
     * Method name for reflective insertions (ObjectNode).
     */
    private static String OBJECT_PUT = "put";

    /**
     * Sets one node value.
     * 
     * @param schema
     *                 Node to apply value.
     * 
     * @param property
     *                 Data source.
     */
    public static void setNodeValue(ObjectNode schema, XProperty property, boolean removeNullValues) {

        //
        // Walk into the tree
        //

        final List<Object> propertyPath = property.getPropertyPath();
        final Object propertyKey = propertyPath.get(propertyPath.size() - 1);
        final Object propertyValue = property.getPropertyValue();

        //
        // From outer to inner...
        //

        JsonNode outerPtr = schema;
        for (int i = 0; i < propertyPath.size() - 1; ++i) {
            final Object outerKey = propertyPath.get(i);
            final Object innerKey = propertyPath.get(i + 1);

            //
            // Get inner pointer
            //

            JsonNode innerPtr;
            if (outerKey instanceof Integer) {
                innerPtr = ((ArrayNode) outerPtr).get((Integer) outerKey);
            } else {
                innerPtr = ((ObjectNode) outerPtr).get((String) outerKey);
            }
            if (innerPtr == null) {

                //
                // Create array and objects, if necessary...
                //

                prepareNodeStructure(outerPtr, outerKey, innerPtr, innerKey);
                if (outerKey instanceof Integer) {
                    innerPtr = ((ArrayNode) outerPtr).get((Integer) outerKey);
                } else {
                    innerPtr = ((ObjectNode) outerPtr).get((String) outerKey);
                }
            }
            outerPtr = innerPtr;
        }

        //
        // Apply value to last outer pointer
        //

        setNodeValue(outerPtr, propertyKey, propertyValue, removeNullValues);
    }

    // -----------------------------------------------------------------------

    /**
     * Sets one node value.
     * 
     * @param ptr
     *              Node to apply value.
     * 
     * @param key
     *              Property name to apply value.
     * 
     * @param value
     *              Data source.
     */
    private static void setNodeValue(JsonNode ptr, Object key, Object value, boolean removeNullValues) {
        if (key instanceof Integer) {
            //
            // Array
            //

            if (((Integer) key) < 0) {

                //
                // Normalize negative indices
                //

                key = ((((ArrayNode) ptr).size()) - ((Integer) key) - 1);
            }

            //
            // Validate the key (index)
            //

            if (((Integer) key) < 0) {
                throw new IllegalArgumentException(Errors.ERROR_INDEX_OUT_OF_BOUNDS);
            }

            if (removeNullValues && (value == null || value instanceof NullNode)) {

                //
                // Remove
                //

                ((ArrayNode) ptr).remove((Integer) key);
            } else {

                //
                // Insert
                //

                final List<Object> array = new ArrayList<>();

                //
                // 0 ... length(ptr)
                //

                for (int i = 0; i < ((ArrayNode) ptr).size(); ++i) {
                    if (i == ((Integer) key)) {
                        array.add(value);
                    } else {
                        array.add(ptr.get(i));
                    }
                }

                if ((Integer) key >= ((ArrayNode) ptr).size()) {

                    //
                    // length(ptr) ... key
                    //

                    for (int i = ((ArrayNode) ptr).size(); i <= (Integer) key; ++i) {
                        if (i == ((Integer) key)) {
                            array.add(value);
                        } else {
                            array.add(null);
                        }
                    }
                }

                //
                // ptr := {}
                //

                for (int i = ((ArrayNode) ptr).size() - 1; i >= 0; --i) {
                    ((ArrayNode) ptr).remove(i);
                }

                //
                // ptr[i] := array[i]
                //

                for (int i = 0; i < array.size(); ++i) {
                    final Object v = array.get(i);
                    if (v == null) {
                        ((ArrayNode) ptr).add(NullNode.instance);
                    } else if (v instanceof JsonNode) {
                        ((ArrayNode) ptr).add((JsonNode) v);
                    } else {
                        try {

                            //
                            // Select overloaded method...
                            //

                            final Class<?> type = v.getClass();
                            final Method add = ArrayNode.class.getMethod(ARRAY_ADD, type);
                            add.invoke(ptr, v);
                        } catch (ReflectiveOperationException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                }
            }
        } else {

            //
            // Object
            //

            if (removeNullValues && (value == null || value instanceof NullNode)) {

                //
                // Remove
                //

                ((ObjectNode) ptr).remove((String) key);
            } else {

                //
                // Insert
                //

                if (value == null) {
                    ((ObjectNode) ptr).set((String) key, NullNode.instance);
                } else if (value instanceof JsonNode) {
                    ((ObjectNode) ptr).set((String) key, (JsonNode) value);
                } else {
                    try {

                        //
                        // Select overloaded method...
                        //

                        final Class<?> type = value.getClass();
                        final Method put = ObjectNode.class.getMethod(OBJECT_PUT, String.class, type);
                        put.invoke(ptr, (String) key, value);
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }
    }

    /**
     * Creates arrays and objects.
     * 
     * @param outerPtr
     *                 Outer pointer (immutable).
     * 
     * @param outerKey
     *                 Outer key (integer or string).
     * 
     * @param innerPtr
     *                 Inner pointer (mutable).
     * 
     * @param innerKey
     *                 Inner key (integer or string).
     */
    private static void prepareNodeStructure(JsonNode outerPtr, Object outerKey, JsonNode innerPtr, Object innerKey) {
        if (innerKey instanceof Integer) {

            //
            // Insert array...
            //

            if (!(innerPtr instanceof ArrayNode)) {
                final ArrayNode arrayNode = MAPPER.createArrayNode();
                if (outerKey instanceof Integer) {

                    //
                    // ... into an array
                    //

                    ((ArrayNode) outerPtr).insert((Integer) outerKey, arrayNode);
                } else {

                    //
                    // ... into an object
                    //

                    ((ObjectNode) outerPtr).set((String) outerKey, arrayNode);
                }
            }
        } else {

            //
            // Insert object...
            //

            if (!(innerPtr instanceof ObjectNode)) {
                final ObjectNode objectNode = MAPPER.createObjectNode();
                if (outerKey instanceof Integer) {

                    //
                    // ... into an array
                    //

                    ((ArrayNode) outerPtr).insert((Integer) outerKey, objectNode);
                } else {

                    //
                    // ... into an object
                    //

                    ((ObjectNode) outerPtr).set((String) outerKey, objectNode);
                }
            }
        }
    }

    private WriteImpl() {
    }
}
