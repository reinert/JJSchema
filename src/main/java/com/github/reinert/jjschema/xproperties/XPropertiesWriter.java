package com.github.reinert.jjschema.xproperties;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * X Properties Writer
 * 
 * 
 * @author WhileTrueEndWhile
 * 
 */
public interface XPropertiesWriter {

    /**
     * 
     * Writes X Properties into schema.
     * 
     * 
     * @param schema
     *            Destination schema.
     * 
     * @param properties
     *            A list of X Properties.
     * 
     */
    void writeXProperties(ObjectNode schema, List<XProperty> properties);

}
