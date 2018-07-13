package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.AccessibleObject;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.XProperties;
import com.github.reinert.jjschema.xproperties.XPropertiesReader;
import com.github.reinert.jjschema.xproperties.XProperty;

/**
 * X Properties File Reader Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesFileReader implements XPropertiesReader {

    /**
     * Reads X Properties from a class.
     * 
     * @param type
     *            A class to read X Properties from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(Class<?> type) {
        type = Objects.requireNonNull(type);

        final XProperties attributes = type.getAnnotation(XProperties.class);
        return ReadImpl.readXProperties(attributes);
    }

    /**
     * Reads X Properties from a field.
     * 
     * 
     * @param accessibleObj
     *            A field to read X Properties from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(AccessibleObject accessibleObj) {
        accessibleObj = Objects.requireNonNull(accessibleObj);

        final XProperties attributes = accessibleObj.getAnnotation(XProperties.class);
        return ReadImpl.readXProperties(attributes);
    }

    /**
     * Reads X Properties from JsonProperty annotation instances.
     * 
     * @param type
     *            The class containing the fields to read from.
     * 
     * @param schema
     *            Schema of the class containing the fields to read from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(Class<?> type, ObjectNode schema) {
        throw new UnsupportedOperationException();
    }
}
