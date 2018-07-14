package com.github.reinert.jjschema.xproperties;

import java.lang.reflect.AccessibleObject;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * X Properties Reader
 * 
 * 
 * @author WhileTrueEndWhile
 * 
 */
public interface XPropertiesReader {

    /**
     * 
     * Reads X Properties from a class.
     * 
     * 
     * @param type
     *             A class to read X Properties from.
     * 
     * @return A list of X Properties.
     * 
     */
    List<XProperty> readXProperties(Class<?> type);

    /**
     * 
     * Reads X Properties from a field.
     * 
     * 
     * @param accessibleObj
     *                      A field to read X Properties from.
     * 
     * @return A list of X Properties.
     * 
     */
    List<XProperty> readXProperties(AccessibleObject accessibleObj);

    /**
     * 
     * Reads X Properties from JsonProperty annotation instances.
     * 
     * 
     * @param type
     *               The class containing the fields to read from.
     * 
     * @param schema
     *               Schema of the class containing the fields to read from.
     * 
     * @return A list of X Properties
     * 
     */
    List<XProperty> readXProperties(Class<?> type, ObjectNode schema);

}
