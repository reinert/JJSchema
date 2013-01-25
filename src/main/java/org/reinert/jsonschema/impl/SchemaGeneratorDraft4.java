package org.reinert.jsonschema.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.reinert.jsonschema.JsonSchema;
import org.reinert.jsonschema.SchemaGenerator;
import org.reinert.jsonschema.SchemaProperty;
import org.reinert.jsonschema.SimpleTypeMappings;

public class SchemaGeneratorDraft4 extends SchemaGenerator {
    @Override
    public <T> JsonSchema from(Class<T> type) {
        JsonSchema schema = new JsonSchema();

        String s = SimpleTypeMappings.forClass(type);

        if (s != null) {
            schema.setType(s);
        } else if (Iterable.class.isAssignableFrom(type)) {
            schema.setType("array");
            if (!Collection.class.isAssignableFrom(type)) {
                // NOTE: Customized Iterable Class must declare the Collection object at first
                Field field = type.getDeclaredFields()[0];
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
                schema.setItems(from(genericClass));
            }
        } else if (type == Void.class || type == void.class) {
            return null;
        } else {
            schema.setType("object");
            // fill base object properties
            SchemaProperty sProp = type.getAnnotation(SchemaProperty.class);
            if(sProp != null)
                schema.bind(sProp);
            
            Field[] fields = type.getDeclaredFields();
            Method[] methods = type.getMethods();
            HashMap<Method, Field> props = new HashMap<Method, Field>();
            // get valid properties (get method and respective field (if exists))
            for (Method method : methods) {
                Class<?> declaringClass = method.getDeclaringClass();
                if (declaringClass.equals(Object.class) || Collection.class.isAssignableFrom(declaringClass)) {
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
            
            for (Map.Entry<Method, Field> entry : props.entrySet()) {
                Field field = entry.getValue();
                Method method = entry.getKey();

                JsonSchema prop = generateSchema(method, field);

                String name = (field == null)
                        ? firstToLowCase(method.getName().replace("get", "")) : field.getName();
                if (prop.getSelfRequired()) {
                    schema.addRequired(name);
                }

                schema.addProperty(name, prop);
            }
            
            // Merge with parent class
            Class<? super T> superclass = type.getSuperclass();
			if (superclass != Object.class) {
            	JsonSchema parentSchema = from(superclass);
            	mergeSchema(parentSchema, schema, true);
            	schema = parentSchema;
            }
        }

        return schema;
    }

    private JsonSchema generateSchema(Method method, Field field) {
        JsonSchema schema = new JsonSchema();

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            schema.setType("array");
            ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
            schema.setItems(from(genericClass));
        } else {
            schema = (JsonSchema) from(method.getReturnType());
        }

        // Check the field annotations if the get method references a field or the   
        // method annotations on the other hand and bind them to the JsonSchema object
        Annotation[] ans = field != null ? field.getAnnotations() : method.getAnnotations();
        for (Annotation a : ans) {
            if (a.annotationType() == SchemaProperty.class) {
                SchemaProperty sProp = (SchemaProperty) a;
                schema.bind(sProp);
                // The declaration of $schema is only necessary at the root object
                schema.set$schema("");
            }
        }

        return schema;
    }

    private String firstToLowCase(String string) {
        return Character.toLowerCase(string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
    }
    
    
    //TODO: compare with default values using reflection
    private void mergeSchema(JsonSchema parent, JsonSchema schema, boolean forceOverride) {
    	if (forceOverride) {
    		if (!schema.get$ref().isEmpty() && !schema.get$ref().equals(parent.get$ref())) {
    			parent.set$ref(schema.get$ref());
    		}
    		if (!schema.get$schema().isEmpty() && !schema.get$schema().equals(parent.get$schema())) {
    			parent.set$schema(schema.get$schema());
    		}
    		if (schema.getDefault() != null && !schema.getDefault().equals(parent.getDefault())) {
    			parent.setDefault(schema.getDefault());
    		}
    		if (!schema.getDescription().isEmpty() && !schema.getDescription().equals(parent.getDescription())) {
    			parent.setDescription(schema.getDescription());
    		}
    		if (schema.getEnum() != null && !schema.getEnum().equals(parent.getEnum())) {
    			parent.setEnum(schema.getEnum());
    		}
    		if (!schema.getExclusiveMaximum().equals(parent.getExclusiveMaximum())) {
    			parent.setExclusiveMaximum(schema.getExclusiveMaximum());
    		}
    		if (!schema.getExclusiveMinimum().equals(parent.getExclusiveMinimum())) {
    			parent.setExclusiveMinimum(schema.getExclusiveMinimum());
    		}
    		if (!schema.getId().isEmpty() && !schema.getId().equals(parent.getId())) {
    			parent.setId(schema.getId());
    		}
    		if (schema.getItems() != null && !schema.getItems().equals(parent.getItems())) {
    			parent.setItems(schema.getItems());
    		}
    		if (schema.getMaximum() > -1l && !schema.getMaximum().equals(parent.getMaximum())) {
    			parent.setMaximum(schema.getMaximum());
    		}
    		if (schema.getMaxItems() > 0 && !schema.getMaxItems().equals(parent.getMaxItems())) {
    			parent.setMaxItems(schema.getMaxItems());
    		}
    		if (schema.getMaxLength() > 0 && !schema.getMaxLength().equals(parent.getMaxLength())) {
    			parent.setMaxLength(schema.getMaxLength());
    		}
    		if (parent.getMinimum() > -1 && !schema.getMinimum().equals(parent.getMinimum())) {
    			parent.setMinimum(schema.getMinimum());
    		}
    		if (schema.getMinItems() > 0 && !schema.getMinItems().equals(parent.getMinItems())) {
    			parent.setMinItems(schema.getMinItems());
    		}
    		if ((schema.getMinLength() > 0) && !schema.getMinLength().equals(parent.getMinLength())) {
    			parent.setMinLength(schema.getMinLength());
    		}
    		if (!schema.getMultipleOf().equals(parent.getMultipleOf())) {
    			parent.setMultipleOf(schema.getMultipleOf());
    		}
    		if (!schema.getPattern().isEmpty() && !schema.getPattern().equals(parent.getPattern())) {
    			parent.setPattern(schema.getPattern());
    		}
    		if (schema.getRequired() != null && !schema.getRequired().equals(parent.getRequired())) {
    			parent.setRequired(schema.getRequired());
    		}
    		if (!schema.getSelfRequired().equals(parent.getSelfRequired())) {
    			parent.setSelfRequired(schema.getSelfRequired());
    		}
    		if (!schema.getTitle().isEmpty() && !schema.getTitle().equals(parent.getTitle())) {
    			parent.setTitle(schema.getTitle());
    		}
    		if (!schema.getType().equals(parent.getType())) {
    			parent.setType(schema.getType());
    		}
    		if (!schema.getUniqueItems().equals(parent.getUniqueItems())) {
    			parent.setUniqueItems(schema.getUniqueItems());
    		}

    		HashMap<String, JsonSchema> properties = schema.getProperties();
			if (properties != null) {
				if (parent.getProperties() == null) parent.setProperties(new HashMap<String, JsonSchema>());
    			for (Map.Entry<String, JsonSchema> entry : properties.entrySet()) {
    				String pName = entry.getKey();
    				JsonSchema pSchema = entry.getValue();
    				JsonSchema actualSchema = parent.getProperties().get(pName);
    				if (actualSchema != null) {
                                    mergeSchema(pSchema, actualSchema, true);
    				}
    				parent.getProperties().put(pName, pSchema);
    			}
    		}
    	} else {
    		//TODO: implement not forcing overriding
    	}
    }

    
}
