package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.reinert.jjschema.Attributes;

import java.lang.reflect.AccessibleObject;

/**
 * @author Danilo Reinert
 */

public class AttributesProcessor implements AnnotationProcessor {

    public boolean processAnnotation(ObjectNode schema, AccessibleObject accessibleObject, SchemaContext schemaContext) {
        Attributes props = accessibleObject.getAnnotation(Attributes.class);

        if (props == null)
            return false;

        // Process Attributes
        if (!props.$ref().isEmpty()) {
            schema.put("$ref", props.$ref());
        }
        if (schemaContext.getAutoPutVersion()) {
            schema.put("$schema", SchemaVersion.DRAFTV4.getLocation().toString());
        }
        if (!props.id().isEmpty()) {
            schema.put("id", props.id());
        }
        if (props.required()) {
            schema.put("selfRequired", true);
        }
        if (!props.description().isEmpty()) {
            schema.put("description", props.description());
        }
        if (!props.pattern().isEmpty()) {
            schema.put("pattern", props.pattern());
        }
        if (!props.title().isEmpty()) {
            schema.put("title", props.title());
        }
        if (props.maximum() > -1) {
            schema.put("maximum", props.maximum());
        }
        if (props.exclusiveMaximum()) {
            schema.put("exclusiveMaximum", true);
        }
        if (props.minimum() > -1) {
            schema.put("minimum", props.minimum());
        }
        if (props.exclusiveMinimum()) {
            schema.put("exclusiveMinimum", true);
        }
        if (props.enums().length > 0) {
            ArrayNode enumArray = schema.putArray("enum");
            String[] enums = props.enums();
            for (String v : enums) {
                enumArray.add(v);
            }
        }
        if (props.uniqueItems()) {
            schema.put("uniqueItems", true);
        }
        if (props.minItems() > 0) {
            schema.put("minItems", props.minItems());
        }
        if (props.maxItems() > -1) {
            schema.put("maxItems", props.maxItems());
        }
        if (props.multipleOf() > 0) {
            schema.put("multipleOf", props.multipleOf());
        }
        if (props.minLength() > 0) {
            schema.put("minLength", props.minItems());
        }
        if (props.maxLength() > -1) {
            schema.put("maxLength", props.maxItems());
        }
        return true;
    }
}
