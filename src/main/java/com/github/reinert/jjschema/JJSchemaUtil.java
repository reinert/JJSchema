package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JJSchemaUtil {
    private JJSchemaUtil() {
    }

    public static void processCommonAttributes(ObjectNode node, Attributes attributes) {
        if (!attributes.id().isEmpty()) {
            node.put("id", attributes.id());
        }
        if (!attributes.description().isEmpty()) {
            node.put("description", attributes.description());
        }
        if (!attributes.pattern().isEmpty()) {
            node.put("pattern", attributes.pattern());
        }
        if (!attributes.format().isEmpty()) {
            node.put("format", attributes.format());
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
                if (v.equals("null")) {
                    enumArray.addNull();
                } else {
                    enumArray.add(v);
                }
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
            node.put("minLength", attributes.minLength());
        }
        if (attributes.maxLength() > -1) {
            node.put("maxLength", attributes.maxLength());
        }
        if (attributes.readonly()) {
            node.put("readonly", true);
        }
    }
}
