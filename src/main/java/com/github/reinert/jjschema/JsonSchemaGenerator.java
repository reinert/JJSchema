package com.github.reinert.jjschema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class JsonSchemaGenerator {

	public <T> AbstractJsonSchema generateSchema(Class<T> type) {
		AbstractJsonSchema schema = createInstance();
        schema = checkType(type, schema);
        return schema;
	}

	abstract protected void bind(AbstractJsonSchema schema, SchemaProperty props);

	abstract protected AbstractJsonSchema mergeSchema(
			AbstractJsonSchema parent, AbstractJsonSchema child,
			boolean forceOverride);
	
	abstract protected AbstractJsonSchema createInstance();
	
	protected AbstractJsonSchema generatePropertySchema(Method method, Field field) {
    	AbstractJsonSchema schema = createInstance();

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            schema.setType("array");
            ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
            schema.setItems(generateSchema(genericClass));
        } else {
            schema = generateSchema(method.getReturnType());
        }

        // Check the field annotations if the get method references a field or the   
        // method annotations on the other hand and bind them to the JsonSchema object
        SchemaProperty sProp = field != null ? field.getAnnotation(SchemaProperty.class) : method.getAnnotation(SchemaProperty.class);
        if (sProp != null) {
            bind(schema, sProp);
            // The declaration of $schema is only necessary at the root object
            schema.set$schema(null);
        }
        
        Nullable nullable = field != null ? field.getAnnotation(Nullable.class) : method.getAnnotation(Nullable.class);
        if (nullable != null) {
        	schema.setType(new String[]{schema.getType().toString(),"null"});
        }
            
        return schema;
    }

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

	protected <T> AbstractJsonSchema checkType(Class<T> type, AbstractJsonSchema schema) {
		String s = SimpleTypeMappings.forClass(type);
	    if (s != null) {
	        schema.setType(s);
	    } else if (Iterable.class.isAssignableFrom(type)) {
	        checkCustomCollection(type, schema);
	    } else if (type == Void.class || type == void.class) {
	    	schema = null;
	    } else {
	    	checkCustomObject(type, schema);
	    }
		return schema;
	}

	private <T> void checkCustomObject(Class<T> type, AbstractJsonSchema schema) {
		schema.setType("object");
	    // fill root object properties
	    bindRoot(type, schema);
	    // Generate the class properties' schemas
	    bindProperties(type, schema);
	    // Merge with parent class
	    mergeWithParent(type, schema);
	}

	private <T> void checkCustomCollection(Class<T> type, AbstractJsonSchema schema) {
		schema.setType("array");
		if (!Collection.class.isAssignableFrom(type)) {
		    // NOTE: Customized Iterable Class must declare the Collection object at first
		    Field field = type.getDeclaredFields()[0];
		    ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		    Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
		    schema.setItems(generateSchema(genericClass));
		}
	}

}
