package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.reinert.jjschema.Attributes;

/**
 * @author Danilo Reinert
 */

public class CustomSchemaWrapper extends SchemaWrapper {

    public CustomSchemaWrapper(Class<?> type) {
        super(type);
        processRootAttributes();
    }

    @Override
    public boolean isCustomWrapper() {
        return true;
    }

    @Override
    protected String extractType(Class<?> type) {
        return "object";
    }

    protected void processRootAttributes() {
        final Attributes attributes = type.getAnnotation(Attributes.class);
        node.put("$schema", SchemaVersion.DRAFTV4.getLocation().toString());
        if (attributes != null) {
            if (!attributes.id().isEmpty()) {
                node.put("id", attributes.id());
            }
            if (!attributes.description().isEmpty()) {
                node.put("description", attributes.description());
            }
            if (!attributes.pattern().isEmpty()) {
                node.put("pattern", attributes.pattern());
            }
            if (!attributes.title().isEmpty()) {
                node.put("title", attributes.title());
            }
            if (attributes.maximum() > -1) {
                node.put("maximum", attributes.maximum());
            }
            if (attributes.exclusiveMaximum()) {
                node.put("exclusiveMaximum", true);
            }
            if (attributes.minimum() > -1) {
                node.put("minimum", attributes.minimum());
            }
            if (attributes.exclusiveMinimum()) {
                node.put("exclusiveMinimum", true);
            }
            if (attributes.enums().length > 0) {
                ArrayNode enumArray = node.putArray("enum");
                String[] enums = attributes.enums();
                for (String v : enums) {
                    enumArray.add(v);
                }
            }
            if (attributes.uniqueItems()) {
                node.put("uniqueItems", true);
            }
            if (attributes.minItems() > 0) {
                node.put("minItems", attributes.minItems());
            }
            if (attributes.maxItems() > -1) {
                node.put("maxItems", attributes.maxItems());
            }
            if (attributes.multipleOf() > 0) {
                node.put("multipleOf", attributes.multipleOf());
            }
            if (attributes.minLength() > 0) {
                node.put("minLength", attributes.minItems());
            }
            if (attributes.maxLength() > -1) {
                node.put("maxLength", attributes.maxItems());
            }
        }
    }
}
