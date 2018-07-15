package com.github.reinert.jjschema.xproperties.api;

import java.util.List;

/**
 *
 *
 * X Property
 *
 *
 * @author WhileTrueEndWhile
 *
 *
 */
public interface XProperty {

    /**
     *
     *
     * Gets the property path.
     *
     *
     * @return A list of integers and strings.
     *
     *
     */
    List<Object> getPropertyPath();

    /**
     *
     *
     * Gets the property value.
     *
     *
     * @return An object supported by ArrayNode.insert/ObjectNode.put.
     *
     *
     */
    Object getPropertyValue();

}
