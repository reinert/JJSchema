package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.JsonNode;

public class SchemaFactory {

	private static JsonSchemaGenerator genV4 = new JsonSchemaGeneratorV4();
	private static JsonSchemaNodeGenerator nodeGenV4 = new JsonSchemaNodeGeneratorV4();
	
	public static <T> JsonSchema v4SchemaFrom(Class<T> type) {
		return genV4.generateSchema(type);
	}

	public static <T> JsonNode v4NodeSchemaFrom(Class<T> type) {
		return nodeGenV4.generateSchema(type);
	}
}
