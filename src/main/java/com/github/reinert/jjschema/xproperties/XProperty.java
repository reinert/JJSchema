package com.github.reinert.jjschema.xproperties;

import java.util.List;

/**
 * 
 * X Property
 * 
 * 
 * @author WhileTrueEndWhile
 * 
 */
public interface XProperty {

    /**
     * 
     * Gets the property path.
     * 
     * 
     * @return A List of integers and strings.
     * 
     */
    List<Object> getPropertyPath();

    /**
     * 
     * Gets the property value.
     * 
     * 
     * @return A boolean, an integer, a double or a string.
     * 
     */
    Object getPropertyValue();

}
