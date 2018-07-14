package com.github.reinert.jjschema.xproperties.impl;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.XPropertiesWriter;
import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * X Properties Writer Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesWriter implements XPropertiesWriter {

    /**
     * Mode: Remove null values (true) or insert null (false).
     */
    private final boolean removeNullValues;

    public DefaultXPropertiesWriter() {
        this.removeNullValues = false;
    }

    public DefaultXPropertiesWriter(boolean removeNullValues) {
        this.removeNullValues = removeNullValues;
    }

    /**
     * Writes X Properties into schema.
     * 
     * 
     * @param schema
     *                   Destination schema.
     * 
     * @param properties
     *                   List of X Properties.
     */
    @Override
    public void writeXProperties(ObjectNode schema, List<XProperty> properties) {
        schema = Objects.requireNonNull(schema);
        properties = Objects.requireNonNull(properties);

        for (int i = 0; i < properties.size(); ++i) {
            final XProperty property = properties.get(i);
            try {
                WriteImpl.setNodeValue(schema, property, removeNullValues);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("xProperties[" + i + "]", e);
            }
        }
    }
}
