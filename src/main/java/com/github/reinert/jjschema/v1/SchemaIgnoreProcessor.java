package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.SchemaIgnore;

import java.lang.reflect.AccessibleObject;

/**
 * @author Danilo Reinert
 */

public class SchemaIgnoreProcessor implements AnnotationProcessor {

    public boolean processAnnotation(ObjectNode schema, AccessibleObject accessibleObject, SchemaContext schemaContext) {
        SchemaIgnore ignore = accessibleObject.getAnnotation(SchemaIgnore.class);

        if (ignore == null)
            return false;

        // Process SchemaIgnore
        schema = null;
        return true;
    }
}
