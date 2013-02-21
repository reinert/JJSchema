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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.reinert.jjschema.exception.InvalidLinkMethod;

public class HyperSchemaGeneratorV4 extends JsonSchemaGenerator {

	final JsonSchemaGenerator jsonSchemaGenerator;

	protected HyperSchemaGeneratorV4(JsonSchemaGenerator jsonSchemaGenerator) {
		this.jsonSchemaGenerator = jsonSchemaGenerator;
	}
	
	private ObjectNode generateLink(Method method) throws InvalidLinkMethod {
		String href = null, rel = null, httpMethod = null;
		boolean isLink = false;
		
		Annotation[] ans = method.getAnnotations();
		for (Annotation a : ans) {
			if (a.annotationType().equals(GET.class)) {
				httpMethod = "GET";
				isLink = true;
			}
			else if (a.annotationType().equals(POST.class)) {
				httpMethod = "POST";
				isLink = true;
			}
			else if (a.annotationType().equals(PUT.class)) {
				httpMethod = "PUT";
				isLink = true;
			}
			else if (a.annotationType().equals(DELETE.class)) {
				httpMethod = "DELETE";
				isLink = true;
			}
			else if (a.annotationType().equals(HEAD.class)) {
				throw new RuntimeException("HEAD not yet supported.");
			}
			else if (a.annotationType().equals(Path.class)) {
				Path p = (Path) a;
				href = p.value();
			}
			else if (a.annotationType().equals(Rel.class)) {
				Rel l = (Rel) a;
				rel = l.value();
			}
		}
		
		// Check if the method is actually a link
		if (!isLink) {
			throw new InvalidLinkMethod("Method " + method.getName() + " is not a link. Must use a HTTP METHOD annotation.");
		}
		
		// If the rel was not informed than assume the method name
		if (rel == null) 
			rel = method.getName();
		
		// if the href was not informed than fill with default #
		if (href == null)
			href = "#";
		
		ObjectNode link = jsonSchemaGenerator.createInstance();
		link.put("href", href);
		link.put("method", httpMethod);
		link.put("rel", rel);
		
		// TODO: by default use a Prototype containing only the $id or $ref for the TargetSchema
		ObjectNode tgtSchema = generateSchema(method.getReturnType());
		if (tgtSchema != null)
			link.put("targetSchema", tgtSchema);
		
		// Check possible params and form schema attribute.
		// If it has QueryParam or FormParam than the schema must have these params as properties.
		// If it has none of the above and has some ordinary object than the schema must be this
		// object and it is passed by the body.
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length > 0) {
			ObjectNode schema = null;
			boolean hasParam = false;
			boolean hasBodyParam = false;
			for (int i = 0; i < paramTypes.length; i++) {
				Annotation[] paramAns = method.getParameterAnnotations()[i];
				com.github.reinert.jjschema.Media media = null;
				String prop = null;
				boolean isBodyParam = true;
				boolean isParam = false;
				for (int j = 0; j < paramAns.length; j++) {
					Annotation a = paramAns[j];
					if (a instanceof QueryParam) {
						if (schema == null) {
							schema = jsonSchemaGenerator.createInstance();
							schema.put("type", "object");
						}
						QueryParam q = (QueryParam) a;
						schema.put(q.value(), jsonSchemaGenerator.generateSchema(paramTypes[i]));
						prop = q.value();
						hasParam = true;
						isBodyParam = false;
						isParam = true;
					}
					else if (a instanceof FormParam) {
						if (schema == null) {
							schema = jsonSchemaGenerator.createInstance();
							schema.put("type", "object");
						}
						FormParam q = (FormParam) a;
                                                
						schema.put(q.value(), jsonSchemaGenerator.generateSchema(paramTypes[i]));
						prop = q.value();
						hasParam = true;
						isBodyParam = false;
						isParam = true;
					}
					else if (a instanceof PathParam) {
						if (media != null) {
							throw new RuntimeException("Media cannot be declared along with PathParam.");
						}
						for (int k = j+1; k < paramAns.length; k++) {
							Annotation a2 = paramAns[k];
							if (a2 instanceof com.github.reinert.jjschema.Media)
								throw new RuntimeException("Media cannot be declared along with PathParam.");
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof CookieParam) {
						if (media != null) {
							media = null;
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof HeaderParam) {
						if (media != null) {
							media = null;
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof MatrixParam) {
						if (media != null) {
							throw new RuntimeException("Media cannot be declared along with MatrixParam.");
						}
						for (int k = j+1; k < paramAns.length; k++) {
							Annotation a2 = paramAns[k];
							if (a2 instanceof com.github.reinert.jjschema.Media)
								throw new RuntimeException("Media cannot be declared along with MatrixParam.");
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof Context) {
						if (media != null) {
							throw new RuntimeException("Media cannot be declared along with Context.");
						}
						isBodyParam = false;
						continue;
					} 
					else if (a instanceof com.github.reinert.jjschema.Media) {
						media = (com.github.reinert.jjschema.Media) a;
					}
				}
				if (isBodyParam) {
					hasBodyParam = true;
					schema = generateSchema(paramTypes[i]);
					if (media != null) {
						schema.put("mediaType", media.type());
						schema.put("binaryEncoding", media.binaryEncoding());
					}
				} else if (isParam) {
					hasParam = true;
					if (media != null) {
						ObjectNode hs = (ObjectNode) schema.get(prop);
						hs.put("mediaType", media.type());
						hs.put("binaryEncoding", media.binaryEncoding());
						schema.put(prop, hs);
					}
				}
			}
			
			if (hasBodyParam && hasParam)
				throw new RuntimeException("JsonSchema does not support both FormParam or QueryParam and BodyParam at the same time.");
			
			link.put("schema", schema);
		}
		
		return link;
	}
	
	private <T> ObjectNode generateHyperSchemaFromResource(Class<T> type) {
		ObjectNode schema = null;
		
		Annotation[] ans = type.getAnnotations();
		boolean hasPath = false;
		for (Annotation a : ans) {
			if (a instanceof Path) {
				hasPath = true;
				Path p = (Path) a;
				if (schema == null) {
					schema = jsonSchemaGenerator.createInstance();
				}
				schema.put("pathStart", p.value());
			}
			if (a instanceof Produces) {
				Produces p = (Produces) a;
				if (schema == null) {
					schema = jsonSchemaGenerator.createInstance();
				}
				schema.put("mediaType", p.value()[0]);
			}
		}
		if (!hasPath) {
			throw new RuntimeException("Invalid Resource class. Must use Path annotation.");
		}
		
		ArrayNode links = schema.putArray("links");
		
		for (Method method : type.getDeclaredMethods()) {
			try {
				ObjectNode link = generateLink(method);
				if (link.get("method").asText().equals("GET") && link.get("href").asText().equals("#")) {
					jsonSchemaGenerator.mergeSchema(schema, (ObjectNode) link.get("targetSchema"), true);
				} else {
					links.add(link);
				}
			} catch(InvalidLinkMethod e) {
				e.printStackTrace();
			}
		}
		
		return schema;
	}
	
	private static <T> ObjectNode transformJsonToHyperSchema(Class<T> type, ObjectNode jsonSchema) {
		ObjectNode properties = (ObjectNode) jsonSchema.get("properties");
		Iterator<String> namesIterator = properties.fieldNames();
		
		while (namesIterator.hasNext()) {
			String prop = namesIterator.next();
			try {
				Field field = type.getDeclaredField(prop);
				com.github.reinert.jjschema.Media media = field.getAnnotation(com.github.reinert.jjschema.Media.class);
				if (media != null) {
					ObjectNode hyperProp = (ObjectNode) properties.get(prop);
					hyperProp.put("mediaType", media.type());
					hyperProp.put("binaryEncoding", media.binaryEncoding());
					if (hyperProp.get("type").isArray()) {
						TextNode typeString = new TextNode("string");
						((ArrayNode) hyperProp.get("type")).set(0, typeString);
					} else {
						hyperProp.put("type", "string");
					}
					properties.put(prop, hyperProp);
				}
			} catch (NoSuchFieldException e) {
				//e.printStackTrace();
			} catch (SecurityException e) {
				//e.printStackTrace();
			}
		}
		return jsonSchema;
	}

    @Override
	public <T> ObjectNode generateSchema(Class<T> type) {
		ObjectNode hyperSchema = null;
		Annotation path = type.getAnnotation(Path.class);
		if (path != null) {
			hyperSchema = generateHyperSchemaFromResource(type);
		} else {
            ObjectNode jsonSchema = (ObjectNode) jsonSchemaGenerator.generateSchema(type);
			if (jsonSchema != null) {
				if (jsonSchema.get("type").asText().equals("array")) {
					if (!Collection.class.isAssignableFrom(type)) {
						ObjectNode items = (ObjectNode) jsonSchema.get("items");
						// NOTE: Customized Iterable Class must declare the Collection object at first
						Field field = type.getDeclaredFields()[0];
						ParameterizedType genericType = (ParameterizedType) field.getGenericType();
				        Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
				        ObjectNode hyperItems = transformJsonToHyperSchema(genericClass, (ObjectNode) items);
				        jsonSchema.put("items", hyperItems);
					}
			        hyperSchema = jsonSchema;
				}
				else if (jsonSchema.has("properties")) {
					hyperSchema = transformJsonToHyperSchema(type, jsonSchema);
				} else {
					hyperSchema = jsonSchema;
				}
			}
		}
		return hyperSchema;
	}

    @Override
    protected void bind(ObjectNode schema, SchemaProperty props) {
        jsonSchemaGenerator.bind(schema, props);
    }
}
