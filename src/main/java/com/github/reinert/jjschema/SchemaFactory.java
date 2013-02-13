package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.JsonNode;

public class SchemaFactory {

	private static JsonSchemaPojoGenerator genV4 = new JsonSchemaPojoGeneratorV4();
	private static JsonSchemaNodeGenerator nodeGenV4 = new JsonSchemaNodeGeneratorV4();
	private static HyperSchemaNodeGenerator hyperGenV4 = new HyperSchemaNodeGenerator(nodeGenV4);
	
	public static <T> JsonSchema v4PojoSchemaFrom(Class<T> type) {
		return genV4.generateSchema(type);
	}

	public static <T> JsonNode v4SchemaFrom(Class<T> type) {
		return nodeGenV4.generateSchema(type);
	}
	
	public static <T> JsonNode v4HyperSchemaFrom(Class<T> type) {
		return hyperGenV4.generateHyperSchema(type);
	}
}
