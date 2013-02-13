package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonSchemaGeneratorV4 extends JsonSchemaGenerator {

	@Override
	protected void bind(ObjectNode schema, SchemaProperty props) {
    	if (!props.$ref().isEmpty()) {
        	schema.put("$ref", props.$ref());
        }
    	if (!props.$schema().isEmpty()) {
        	schema.put("$schema", props.$schema());
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
    }

}
