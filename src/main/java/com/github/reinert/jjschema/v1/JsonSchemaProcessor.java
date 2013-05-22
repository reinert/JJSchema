package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Abstract class for processing JsonSchema
 *
 * @author reinert
 */
public abstract class JsonSchemaProcessor {

    private final Class<?> type;

    protected JsonSchemaProcessor(Class<?> type) {
        this.type = type;
    }

    protected abstract ObjectNode process();

    // Process annotations
    // Process attributes
    // Process properties
    // Process $ref
    // and go on...
}
