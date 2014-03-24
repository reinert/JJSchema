/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.reinert.jjschema.exception.InvalidLinkMethod;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;

/**
 * A Hyper-Schema generator from JSR311 specification (Java RESTful) annotated classes.
 * It generates an hyper-schema with correct links, targets, mediaType, and other properties.
 * Please consider looking at JavaRESTfulTest for an example of how to use it.
 *
 * @author Danilo
 */
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
            } else if (a.annotationType().equals(POST.class)) {
                httpMethod = "POST";
                isLink = true;
            } else if (a.annotationType().equals(PUT.class)) {
                httpMethod = "PUT";
                isLink = true;
            } else if (a.annotationType().equals(DELETE.class)) {
                httpMethod = "DELETE";
                isLink = true;
            } else if (a.annotationType().equals(HEAD.class)) {
                throw new RuntimeException("HEAD not yet supported.");
            } else if (a.annotationType().equals(Path.class)) {
                Path p = (Path) a;
                href = p.value();
            } else if (a.annotationType().equals(Rel.class)) {
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
                    } else if (a instanceof FormParam) {
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
                    } else if (a instanceof PathParam) {
                        if (media != null) {
                            throw new RuntimeException("Media cannot be declared along with PathParam.");
                        }
                        for (int k = j + 1; k < paramAns.length; k++) {
                            Annotation a2 = paramAns[k];
                            if (a2 instanceof com.github.reinert.jjschema.Media)
                                throw new RuntimeException("Media cannot be declared along with PathParam.");
                        }
                        isBodyParam = false;
                        continue;
                    } else if (a instanceof CookieParam) {
                        if (media != null) {
                            media = null;
                        }
                        isBodyParam = false;
                        continue;
                    } else if (a instanceof HeaderParam) {
                        if (media != null) {
                            media = null;
                        }
                        isBodyParam = false;
                        continue;
                    } else if (a instanceof MatrixParam) {
                        if (media != null) {
                            throw new RuntimeException("Media cannot be declared along with MatrixParam.");
                        }
                        for (int k = j + 1; k < paramAns.length; k++) {
                            Annotation a2 = paramAns[k];
                            if (a2 instanceof com.github.reinert.jjschema.Media)
                                throw new RuntimeException("Media cannot be declared along with MatrixParam.");
                        }
                        isBodyParam = false;
                        continue;
                    } else if (a instanceof Context) {
                        if (media != null) {
                            throw new RuntimeException("Media cannot be declared along with Context.");
                        }
                        isBodyParam = false;
                        continue;
                    } else if (a instanceof com.github.reinert.jjschema.Media) {
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
            } catch (InvalidLinkMethod e) {
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
                } else if (jsonSchema.has("properties")) {
                    hyperSchema = transformJsonToHyperSchema(type, jsonSchema);
                } else {
                    hyperSchema = jsonSchema;
                }
            }
        }

        //TODO: When available by SchemaVersion, put the $schema attribute as the correct HyperSchema ref.

        return hyperSchema;
    }

    @Override
    protected void processSchemaProperty(ObjectNode schema, Attributes props) {
        jsonSchemaGenerator.processSchemaProperty(schema, props);
    }
}
