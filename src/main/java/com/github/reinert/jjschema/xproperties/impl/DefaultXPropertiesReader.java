package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.AccessibleObject;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.annotations.XProperties;
import com.github.reinert.jjschema.xproperties.api.XPropertiesReader;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * X Properties Reader Implementation
 * 
 * @author WhileTrueEndWhile
 */
public class DefaultXPropertiesReader implements XPropertiesReader {

    /**
     * Fly Weight Instance
     */
    public static XPropertiesReader instance = new DefaultXPropertiesReader();

    /**
     * Reads X Properties from a class.
     * 
     * @param type
     *             A class to read X Properties from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readFromClass(Class<?> type) {

        type = Objects.requireNonNull(type);
        final XProperties attributes = type.getAnnotation(XProperties.class);
        return FromXProperties.instance.readXProperties(attributes);
    }

    /**
     * Reads X Properties from a field.
     * 
     * 
     * @param accessibleObj
     *                      A field to read X Properties from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readFromField(AccessibleObject accessibleObj) {

        accessibleObj = Objects.requireNonNull(accessibleObj);
        final XProperties attributes = accessibleObj.getAnnotation(XProperties.class);
        return FromXProperties.instance.readXProperties(attributes);
    }

    /**
     * Reads X Properties from JsonProperty annotation instances.
     * 
     * @param type
     *               The class containing the fields to read from.
     * 
     * @param schema
     *               Schema of the class containing the fields to read from.
     * 
     * @return A list of X Properties
     */

    @Override
    public List<XProperty> readFromJsonProperty(Class<?> type, ObjectNode schema) {

        type = Objects.requireNonNull(type);
        schema = Objects.requireNonNull(schema);
        return FromJsonProperty.instance.readXProperties(type, schema);
    }

    /**
     * Reads the presence the oneOf annotation from a field.
     * 
     * @param type
     *               The class containing the fields to read from.
     * 
     * @param schema
     *               Schema of the class containing the fields to read from.
     * 
     * @return A list of X Properties.
     */
    @Override
    public List<XProperty> readOneOf(Class<?> type, ObjectNode schema) {

        type = Objects.requireNonNull(type);
        schema = Objects.requireNonNull(schema);
        return FromOneOf.instance.readXProperties(type, schema);
    }

    protected DefaultXPropertiesReader() {
    }
}
