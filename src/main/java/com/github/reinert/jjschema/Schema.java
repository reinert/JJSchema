package com.github.reinert.jjschema;

public class Schema {

	private static JsonSchemaGenerator genV4 = new JsonSchemaGeneratorV4();
	
	public static <T> JsonSchema v4SchemaFrom(Class<T> type) {
		return genV4.generateSchema(type);
	}

}
