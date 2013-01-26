package com.github.reinert.jjschema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class JsonSchemaGenerator {

	abstract public <T> AbstractJsonSchema generateSchema(Class<T> type);

	abstract protected AbstractJsonSchema generatePropertySchema(Method method,
			Field field);

	abstract protected void bind(AbstractJsonSchema schema, SchemaProperty props);

	abstract protected AbstractJsonSchema mergeSchema(
			AbstractJsonSchema parent, AbstractJsonSchema child,
			boolean forceOverride);

	protected <T> void bindRoot(Class<T> type, AbstractJsonSchema schema) {
		SchemaProperty sProp = type.getAnnotation(SchemaProperty.class);
		if (sProp != null)
			bind(schema, sProp);
	}

	protected <T> void bindProperties(Class<T> type, AbstractJsonSchema schema) {
		HashMap<Method, Field> props = findProperties(type);
		for (Map.Entry<Method, Field> entry : props.entrySet()) {
			Field field = entry.getValue();
			Method method = entry.getKey();
			AbstractJsonSchema prop = generatePropertySchema(method, field);
			addPropertyToSchema(schema, field, method, prop);
		}
	}

	private void addPropertyToSchema(AbstractJsonSchema schema, Field field,
			Method method, AbstractJsonSchema prop) {
		String name = getPropertyName(field, method);
		if (prop.getSelfRequired()) {
			schema.addRequired(name);
		}
		schema.addProperty(name, prop);
	}

	private String getPropertyName(Field field, Method method) {
		String name = (field == null) ? firstToLowCase(method.getName()
				.replace("get", "")) : field.getName();
		return name;
	}

	protected <T> void mergeWithParent(Class<T> type, AbstractJsonSchema schema) {
		Class<? super T> superclass = type.getSuperclass();
		if (superclass != Object.class && superclass != JsonSchema.class) {
			AbstractJsonSchema parentSchema = generateSchema(superclass);
			schema = mergeSchema(parentSchema, schema, true);
		}
		// return schema;
	}

	private <T> HashMap<Method, Field> findProperties(Class<T> type) {
		Field[] fields = type.getDeclaredFields();
		Method[] methods = type.getMethods();
		HashMap<Method, Field> props = new HashMap<Method, Field>();
		// get valid properties (get method and respective field (if exists))
		for (Method method : methods) {
			Class<?> declaringClass = method.getDeclaringClass();
			if (declaringClass.equals(Object.class)
					|| Collection.class.isAssignableFrom(declaringClass)) {
				continue;
			}

			String methodName = method.getName();
			if (methodName.startsWith("get")) {
				boolean hasField = false;
				for (Field field : fields) {
					String name = methodName.substring(3);
					if (field.getName().equalsIgnoreCase(name)) {
						props.put(method, field);
						hasField = true;
						break;
					}
				}
				if (!hasField) {
					props.put(method, null);
				}
			}
		}
		return props;
	}

	private String firstToLowCase(String string) {
		return Character.toLowerCase(string.charAt(0))
				+ (string.length() > 1 ? string.substring(1) : "");
	}

}
