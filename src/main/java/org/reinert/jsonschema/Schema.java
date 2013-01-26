package org.reinert.jsonschema;

public class Schema {

	private static JsonSchemaGeneratorV4 genV4 = new JsonSchemaGeneratorV4();
	
	public static <T> JsonSchema v4SchemaFrom(Class<T> type) {
		return genV4.generateSchema(type);
	}

}
