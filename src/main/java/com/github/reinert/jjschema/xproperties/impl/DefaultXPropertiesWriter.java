package com.github.reinert.jjschema.xproperties.impl;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.api.XPropertiesWriter;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * X Properties Writer Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesWriter implements XPropertiesWriter {

    /**
     * Fly Weight Instance (inserts null if a values is null)
     */
    public static XPropertiesWriter instance = new DefaultXPropertiesWriter();

    /**
     * Fly Weight Instance (removes node if a values is null)
     */
    public static XPropertiesWriter withRemoveNullValues = new DefaultXPropertiesWriter(true);

    /**
     * Mode: Remove null values (true) or insert null (false).
     */
    private final boolean removeNullValues;

    protected DefaultXPropertiesWriter() {

        this.removeNullValues = false;
    }

    protected DefaultXPropertiesWriter(boolean removeNullValues) {

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
