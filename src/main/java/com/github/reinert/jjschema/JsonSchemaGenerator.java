/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.reinert.jjschema;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class JsonSchemaGenerator {

	final ObjectMapper mapper = new ObjectMapper();
    boolean autoPutVersion = true;

    protected JsonSchemaGenerator(){}

    abstract protected void bind(ObjectNode schema, SchemaProperty props);
	
	protected ObjectNode createInstance() {
		return mapper.createObjectNode();
	}

    public boolean isAutoPutVersion() {
        return autoPutVersion;
    }

    public JsonSchemaGenerator setAutoPutVersion(boolean autoPutVersion) {
        this.autoPutVersion = autoPutVersion;
        return this;
    }

    public <T> ObjectNode generateSchema(Class<T> type) {
		ObjectNode schema = createInstance();
        schema = checkType(type, schema);
        return schema;
	}
	
	protected <T> ObjectNode checkType(Class<T> type, ObjectNode schema) {
		String s = SimpleTypeMappings.forClass(type);
	    if (s != null) {
	        schema.put("type", s);
	    } else if (Iterable.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type)) {
	        checkCustomCollection(type, schema);
	    } else if (type == Void.class || type == void.class) {
	    	schema = null;
	    } else {
	    	schema = checkCustomObject(type, schema);
	    }
		return schema;
	}
	
	protected <T> ObjectNode checkCustomObject(Class<T> type, ObjectNode schema) {
		schema.put("type", "object");
	    // fill root object properties
	    bindRoot(type, schema);
	    // Generate the class properties' schemas
	    bindProperties(type, schema);
	    // Merge with parent class
	    schema = mergeWithParent(type, schema);
	    
	    return schema;
	}

	private <T> void checkCustomCollection(Class<T> type, ObjectNode schema) {
		if (AbstractCollection.class.isAssignableFrom(type)) {
			schema.put("type", "array");
		} else {
			bindRoot(type, schema);
			// NOTE: Customized Iterable/Collection Wrapper Class must declare the intended Collection at first
			bindArraySchema(type, schema);
		}
	}

	private <T> void bindArraySchema(Class<T> type, ObjectNode schema) {
		schema.put("type", "array");
		Field field = type.getDeclaredFields()[0];
		ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
        ObjectNode itemsSchema = generateSchema(genericClass);
        itemsSchema.remove("$schema");
        schema.put("items", itemsSchema);
	}
	
	private void bindArraySchema(Method method, ObjectNode schema) {
		schema.put("type", "array");
		ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
		Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
		schema.put("items", generateSchema(genericClass));
	}
	
	protected ObjectNode generatePropertySchema(Method method, Field field) {
    	ObjectNode schema = createInstance();

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            bindArraySchema(method, schema);
        } else {
            schema = generateSchema(method.getReturnType());
        }

        // Check the field annotations if the get method references a field or the   
        // method annotations on the other hand and bind them to the JsonSchema object
        SchemaProperty sProp = field != null ? field.getAnnotation(SchemaProperty.class) : method.getAnnotation(SchemaProperty.class);
        if (sProp != null) {
            bind(schema, sProp);
            // The declaration of $schema is only necessary at the root object
            schema.remove("$schema");
        }
        
        Nullable nullable = field != null ? field.getAnnotation(Nullable.class) : method.getAnnotation(Nullable.class);
        if (nullable != null) {
        	String oldType = schema.get("type").asText();
        	ArrayNode typeArray = schema.putArray("type");
        	typeArray.add(oldType);
        	typeArray.add("null");
        }
            
        return schema;
    }

	protected <T> void bindRoot(Class<T> type, ObjectNode schema) {
		SchemaProperty sProp = type.getAnnotation(SchemaProperty.class);
		if (sProp != null)
			bind(schema, sProp);
	}

	protected <T> void bindProperties(Class<T> type, ObjectNode schema) {
		HashMap<Method, Field> props = findProperties(type);
		for (Map.Entry<Method, Field> entry : props.entrySet()) {
			Field field = entry.getValue();
			Method method = entry.getKey();
			ObjectNode prop = generatePropertySchema(method, field);
			addPropertyToSchema(schema, field, method, prop);
		}
	}

	private void addPropertyToSchema(ObjectNode schema, Field field, Method method, ObjectNode prop) {
		String name = getPropertyName(field, method);
		if (prop.has("selfRequired")) {
			ArrayNode requiredNode = null;
			if (!schema.has("required")) {
				requiredNode = schema.putArray("required");
			} else {
				requiredNode = (ArrayNode) schema.get("required");
			}
			requiredNode.add(name);
			prop.remove("selfRequired");
		}
		if (!schema.has("properties")) schema.putObject("properties");
		((ObjectNode) schema.get("properties")).put(name, prop);
	}

	private String getPropertyName(Field field, Method method) {
		String name = (field == null) ? firstToLowCase(method.getName()
				.replace("get", "")) : field.getName();
		return name;
	}

	protected <T> ObjectNode mergeWithParent(Class<T> type, ObjectNode schema) {
		Class<? super T> superclass = type.getSuperclass();
		if (superclass != Object.class) {
			ObjectNode parentSchema = generateSchema(superclass);
			schema = mergeSchema(parentSchema, schema, false);
		}
		return schema;
	}
	
	protected ObjectNode mergeSchema(ObjectNode parent, ObjectNode child, boolean overwriteProperties) {
		Iterator<String> namesIterator = child.fieldNames();
		
		if (overwriteProperties) {
			while (namesIterator.hasNext()) {
				String propertyName = namesIterator.next();
				overwriteProperty(parent, child, propertyName);
			}
			
		} else {
			
			while (namesIterator.hasNext()) {
				String propertyName = namesIterator.next();
				if (!propertyName.equals("properties")) {
					overwriteProperty(parent, child, propertyName);
				}
			}
			
			ObjectNode properties = (ObjectNode) child.get("properties");
			if (properties != null) {
				if (parent.get("properties") == null) {
					parent.putObject("properties");
				}

				Iterator<Entry<String, JsonNode>> it = properties.fields();
				while (it.hasNext()) {
					Entry<String, JsonNode> entry = it.next();
					String pName = entry.getKey();
					ObjectNode pSchema = (ObjectNode) entry.getValue();
					ObjectNode actualSchema = (ObjectNode) parent.get("properties").get(pName);
					if (actualSchema != null) {
						mergeSchema(pSchema, actualSchema, false);
					}
					((ObjectNode) parent.get("properties")).put(pName, pSchema);
				}
			}
		}
		
		return parent;
    }

	protected void overwriteProperty(ObjectNode parent, ObjectNode child, String propertyName) {
		if (child.has(propertyName)) {
			parent.put(propertyName, child.get(propertyName));
		}
	}
	
//	protected void copyPropertyIfNotExists(ObjectNode parent, ObjectNode child, String propertyName) {
//		if (!parent.has(propertyName)) {
//			parent.put(propertyName, child.get(propertyName));
//		}
//	}

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
		return Character.toLowerCase(string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
	}

}
