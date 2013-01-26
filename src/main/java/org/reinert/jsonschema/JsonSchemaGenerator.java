package org.reinert.jsonschema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface JsonSchemaGenerator {

	<T> AbstractJsonSchema generateSchema(Class<T> type);
	JsonSchema generatePropertySchema(Method method, Field field);
	void bind(AbstractJsonSchema schema, SchemaProperty props);
	AbstractJsonSchema mergeSchema(AbstractJsonSchema parent, AbstractJsonSchema child, boolean forceOverride);
	
}
