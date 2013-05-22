package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.Nullable;

import java.lang.reflect.AccessibleObject;

/**
 * @author Danilo Reinert
 */

public class NullableProcessor implements AnnotationProcessor {

    public boolean processAnnotation(ObjectNode schema, AccessibleObject accessibleObject, SchemaContext schemaContext) {
        Nullable nullable = accessibleObject.getAnnotation(Nullable.class);

        if (nullable == null)
            return false;

        if (schema.isArray()) {
            if (schema.has("enum"))
                ((ArrayNode) schema.get("enum")).add("null");
            else
                return false;
        } else {
            if (schema.has("type")) {
                String oldType = schema.get("type").asText();
                ArrayNode typeArray = schema.putArray("type");
                typeArray.add(oldType);
                typeArray.add("null");
            } else {
                return false;
            }
        }
        return true;
    }
}
