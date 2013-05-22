package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.AccessibleObject;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 22/05/13
 * Time: 09:48
 *
 * @author Danilo Reinert
 */

public interface AnnotationProcessor {

    boolean processAnnotation(ObjectNode schema, AccessibleObject accessibleObject, SchemaContext schemaContext);
}
