package com.github.reinert.jjschema.xproperties.api;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.impl.DefaultXPropertiesWriter;

/**
 *
 *
 * X Properties Writer
 *
 *
 * @author WhileTrueEndWhile
 *
 *
 */
public interface XPropertiesWriter {

    /**
     * Fly Weight Instance (inserts null if a values is null)
     */
    static XPropertiesWriter instance = DefaultXPropertiesWriter.instance;

    /**
     * Fly Weight Instance (removes node if a values is null)
     */
    static XPropertiesWriter withRemoveNullValues = DefaultXPropertiesWriter.withRemoveNullValues;

    /**
     *
     *
     * Writes X Properties into a JSON schema.
     *
     *
     * @param schema
     *                   Destination schema.
     *
     * @param properties
     *                   A list of X Properties.
     *
     *
     */
    void writeXProperties(ObjectNode schema, List<XProperty> properties);

}
