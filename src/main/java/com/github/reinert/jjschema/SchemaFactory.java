package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.deprecated.JsonSchema;
import com.github.reinert.jjschema.deprecated.JsonSchemaPojoGenerator;
import com.github.reinert.jjschema.deprecated.JsonSchemaPojoGeneratorV4;

public class SchemaFactory {

	private static JsonSchemaPojoGenerator genV4 = new JsonSchemaPojoGeneratorV4();
	private static JsonSchemaGenerator nodeGenV4 = new JsonSchemaGeneratorV4();
	private static HyperSchemaGenerator hyperGenV4 = new HyperSchemaGenerator(nodeGenV4);
	
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
