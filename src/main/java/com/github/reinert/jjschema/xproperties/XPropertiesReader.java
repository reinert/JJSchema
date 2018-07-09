package com.github.reinert.jjschema.xproperties;

import java.util.List;

import com.github.reinert.jjschema.Attributes;

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
     * Reads X Properties from an annotation instance.
     * 
     * 
     * @param attributes
     *            Annotation instance.
     * 
     * @return List of X Properties.
     * 
     */
    List<XProperty> readXProperties(Attributes attributes);

}
