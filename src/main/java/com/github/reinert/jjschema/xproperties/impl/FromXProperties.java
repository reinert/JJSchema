package com.github.reinert.jjschema.xproperties.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.reinert.jjschema.xproperties.annotations.XProperties;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * From XProperties Read Implementation
 * 
 * Provides support of <code>@XProperties(?)</code>.
 */
class FromXProperties {

    /**
     * Fly Weight Instance
     */
    public static final FromXProperties instance = new FromXProperties();

    /**
     * Reads X Properties from an annotation instance.
     * 
     * @param attributes
     *                   An annotation instance.
     * 
     * @return A list of X Properties.
     */
    public List<XProperty> readXProperties(XProperties attributes) {
        final List<XProperty> listOfProperties = new ArrayList<>();

        //
        // Read X Properties Files
        //

        listOfProperties.addAll(FromFiles.instance.readXProperties(attributes));

        //
        // Read X Properties
        //

        if (attributes == null || attributes.value() == null) {
            return listOfProperties;
        }
        final String[] xProperties = attributes.value();
        for (int i = 0; i < xProperties.length; ++i) {
            final String xProperty = xProperties[i];
            final XProperty property;
            try {
                property = ReadImpl.readProperty(xProperty);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("xProperties[" + i + "]", e);
            }
            listOfProperties.add(property);
        }
        return listOfProperties;
    }

    protected FromXProperties() {
    }
}
