package com.github.reinert.jjschema.xproperties;

import java.lang.reflect.AccessibleObject;
import java.util.List;

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
     * @param type
     *            A class to read X Properties from.
     * 
     * @return List of X Properties.
     */
    List<XProperty> readXProperties(Class<?> type);

    /**
     * 
     * Reads X Properties from a field.
     * 
     * 
     * @param accessibleObj
     *            A field to read X Properties from.
     * 
     * @return List of X Properties.
     */
    List<XProperty> readXProperties(AccessibleObject accessibleObj);

}
