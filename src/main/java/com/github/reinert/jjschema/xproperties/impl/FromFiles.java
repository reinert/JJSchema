package com.github.reinert.jjschema.xproperties.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.github.reinert.jjschema.xproperties.annotations.XProperties;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * From File Read Implementation
 * 
 * Provides support of <code>@XProperties(files=?)</code>.
 * 
 * @author WhileTrueEndWhile
 */
class FromFiles {

    /**
     * Fly Weight Instance
     */
    public static final FromFiles instance = new FromFiles();

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
        if (attributes == null || attributes.files() == null) {
            return listOfProperties;
        }
        final String[] xPropertiesFiles = attributes.files();
        for (int i = 0; i < xPropertiesFiles.length; ++i) {
            final String xPropertiesFileName = xPropertiesFiles[i];

            //
            // Load file via System
            //

            final InputStream xPropertiesFile = System.class.getResourceAsStream(xPropertiesFileName);
            if (xPropertiesFile == null) {
                throw new IllegalArgumentException(Errors.ERROR_RESOURCE_NOT_FOUND + ": " + xPropertiesFileName);
            }

            //
            // Read via Properties
            //

            final Properties properties = new Properties();
            try {
                properties.load(xPropertiesFile);
            } catch (IOException e) {
                throw new IllegalArgumentException(Errors.ERROR_RESOURCE_IO_ERROR + ": " + xPropertiesFileName, e);
            }
            final Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String propertyPath = (String) propertyNames.nextElement();
                final String propertyValue = properties.getProperty(propertyPath);
                final XProperty xProperty = ReadImpl.readProperty(propertyPath, propertyValue);
                listOfProperties.add(xProperty);
            }
        }
        return listOfProperties;
    }

    protected FromFiles() {
    }
}
