package org.reinert.jsonschema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JsonSchemaGeneratorV4 implements JsonSchemaGenerator {

	@Override
    public <T> AbstractJsonSchema generateSchema(Class<T> type) {
		AbstractJsonSchema schema = new JsonSchemaV4();

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
                schema.setItems(generateSchema(genericClass));
            }
        } else if (type == Void.class || type == void.class) {
            return null;
        } else {
            schema.setType("object");
            // fill base object properties
            SchemaProperty sProp = type.getAnnotation(SchemaProperty.class);
            if(sProp != null)
                bind(schema, sProp);
            
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

                AbstractJsonSchema prop = generatePropertySchema(method, field);

                String name = (field == null)
                        ? firstToLowCase(method.getName().replace("get", "")) : field.getName();
                if (prop.getSelfRequired()) {
                    schema.addRequired(name);
                }

                schema.addProperty(name, prop);
            }
            
            // Merge with parent class
            Class<? super T> superclass = type.getSuperclass();
			if (superclass != Object.class && superclass != JsonSchema.class) {
            	AbstractJsonSchema parentSchema = generateSchema(superclass);
            	schema = mergeSchema(parentSchema, schema, true);
            }
        }

        return schema;
    }

    public AbstractJsonSchema generatePropertySchema(Method method, Field field) {
    	AbstractJsonSchema schema = new JsonSchemaV4();

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
            
        return schema;
    }

    private String firstToLowCase(String string) {
        return Character.toLowerCase(string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
    }
    
    //TODO: compare with default values using constants
    public AbstractJsonSchema mergeSchema(AbstractJsonSchema parent, AbstractJsonSchema child, boolean forceOverride) {
    	JsonSchemaV4 _parent = (JsonSchemaV4) parent;
    	//JsonSchemaV4 _child = (JsonSchemaV4) child;
    	if (forceOverride) {
    		if (child.get$ref() != null && !child.get$ref().equals(parent.get$ref())) {
    			parent.set$ref(child.get$ref());
    		}
    		if (child.get$schema() != null && !child.get$schema().equals(parent.get$schema())) {
    			parent.set$schema(child.get$schema());
    		}
    		if (child.getDefault() != null && !child.getDefault().equals(parent.getDefault())) {
    			parent.setDefault(child.getDefault());
    		}
    		if (child.getDescription() != null && !child.getDescription().equals(parent.getDescription())) {
    			parent.setDescription(child.getDescription());
    		}
    		if (child.getEnum() != null && !child.getEnum().equals(parent.getEnum())) {
    			parent.setEnum(child.getEnum());
    		}
    		if (child.getExclusiveMaximum() != null && !child.getExclusiveMaximum().equals(parent.getExclusiveMaximum())) {
    			parent.setExclusiveMaximum(child.getExclusiveMaximum());
    		}
    		if (child.getExclusiveMinimum() != null && !child.getExclusiveMinimum().equals(parent.getExclusiveMinimum())) {
    			parent.setExclusiveMinimum(child.getExclusiveMinimum());
    		}
    		if (child.getId() != null && !child.getId().equals(parent.getId())) {
    			parent.setId(child.getId());
    		}
    		if (child.getItems() != null && !child.getItems().equals(parent.getItems())) {
    			parent.setItems(child.getItems());
    		}
    		if (child.getMaximum() != null && !child.getMaximum().equals(parent.getMaximum())) {
    			parent.setMaximum(child.getMaximum());
    		}
    		if (child.getMaxItems() != null && !child.getMaxItems().equals(parent.getMaxItems())) {
    			parent.setMaxItems(child.getMaxItems());
    		}
    		if (child.getMaxLength() != null && !child.getMaxLength().equals(parent.getMaxLength())) {
    			parent.setMaxLength(child.getMaxLength());
    		}
    		if (parent.getMinimum() != null && !child.getMinimum().equals(parent.getMinimum())) {
    			parent.setMinimum(child.getMinimum());
    		}
    		if (child.getMinItems() != null && !child.getMinItems().equals(parent.getMinItems())) {
    			parent.setMinItems(child.getMinItems());
    		}
    		if ((child.getMinLength() != null) && !child.getMinLength().equals(parent.getMinLength())) {
    			parent.setMinLength(child.getMinLength());
    		}
    		if (child.getMultipleOf() != null && !child.getMultipleOf().equals(parent.getMultipleOf())) {
    			parent.setMultipleOf(child.getMultipleOf());
    		}
    		if (child.getPattern() != null && !child.getPattern().equals(parent.getPattern())) {
    			parent.setPattern(child.getPattern());
    		}
    		if (child.getRequired() != null && !child.getRequired().equals(parent.getRequired())) {
    			_parent.setRequired(child.getRequired());
    		}
    		if (child.getSelfRequired() != null && !child.getSelfRequired().equals(parent.getSelfRequired())) {
    			parent.setSelfRequired(child.getSelfRequired());
    		}
    		if (child.getTitle() != null && !child.getTitle().equals(parent.getTitle())) {
    			parent.setTitle(child.getTitle());
    		}
    		if (child.getType() != null && !child.getType().equals(parent.getType())) {
    			parent.setType(child.getType());
    		}
    		if (child.getUniqueItems() != null && !child.getUniqueItems().equals(parent.getUniqueItems())) {
    			parent.setUniqueItems(child.getUniqueItems());
    		}

    		Map<String, JsonSchema> properties = child.getProperties();
			if (properties != null) {
				if (parent.getProperties() == null) parent.setProperties(new HashMap<String, JsonSchema>());
    			for (Map.Entry<String, JsonSchema> entry : properties.entrySet()) {
    				String pName = entry.getKey();
    				AbstractJsonSchema pSchema = (AbstractJsonSchema) entry.getValue();
    				AbstractJsonSchema actualSchema = (AbstractJsonSchema) parent.getProperties().get(pName);
    				if (actualSchema != null) {
    					mergeSchema(pSchema, actualSchema, true);
    				}
    				parent.getProperties().put(pName, pSchema);
    			}
    		}
    	} else {
    		//TODO: implement not forcing overriding
    	}
    	
		return parent;
    }

	public void bind(AbstractJsonSchema schema, SchemaProperty props) {
    	if (!props.$ref().isEmpty()) {
        	schema.set$ref(props.$ref());
        }
    	if (!props.$schema().isEmpty()) {
        	schema.set$schema(props.$schema());
        }
        if (!props.id().isEmpty()) {
            schema.setId(props.id());
        }
        if (props.required()) {
            schema.setSelfRequired(true);
        }
        if (!props.description().isEmpty()) {
            schema.setDescription(props.description());
        }
        if (!props.pattern().isEmpty()) {
            schema.setPattern(props.pattern());
        }
        if (!props.title().isEmpty()) {
            schema.setTitle(props.title());
        }
        if (props.maximum() > -1) {
        	schema.setMaximum(props.maximum());
        }
        if (props.exclusiveMaximum()) {
        	schema.setExclusiveMaximum(true);
        }
        if (props.minimum() > -1) {
        	schema.setMinimum(props.minimum());
        }
        if (props.exclusiveMinimum()) {
        	schema.setExclusiveMinimum(true);
        }
        if (props.enums().length > 0) {
        	schema.setEnum(props.enums());
        }
        if (props.uniqueItems()) {
        	schema.setUniqueItems(true);
        }
        if (props.minItems() > 0) {
        	schema.setMinItems(props.minItems());
        }
    }

}
