package com.github.reinert.jjschema.xproperties.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.xproperties.XPropertiesReader;
import com.github.reinert.jjschema.xproperties.XProperty;

public class DefaultXPropertiesFileReader implements XPropertiesReader {

    //
    // Errors
    //
    private static final String ERROR_RESOURCE_NOT_FOUND = "Could not find resource";
    private static final String ERROR_RESOURCE_IO_ERROR = "Could not load resource";

    /**
     * 
     * Reads X Properties from a class.
     * 
     * @param type
     *            A class to read X Properties from.
     * 
     * @return List of X Properties.
     */
    @Override
    public List<XProperty> readXProperties(Class<?> type) {
        final Attributes attributes = type.getAnnotation(Attributes.class);
        return readXProperties(attributes);
    }

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
    @Override
    public List<XProperty> readXProperties(AccessibleObject accessibleObj) {
        final Attributes attributes = accessibleObj.getAnnotation(Attributes.class);
        return readXProperties(attributes);
    }

    /**
     * 
     * Reads X Properties from an annotation instance.
     * 
     * @param attributes
     *            Annotation instance.
     * 
     * @return List of X Properties.
     */
    public static List<XProperty> readXProperties(Attributes attributes) {
        final List<XProperty> listOfProperties = new ArrayList<>();
        if (attributes == null || attributes.xPropertiesFiles() == null) {
            return listOfProperties;
        }
        final String[] xPropertiesFiles = attributes.xPropertiesFiles();
        for (int i = 0; i < xPropertiesFiles.length; ++i) {
            final String xPropertiesFileName = xPropertiesFiles[i];
            final InputStream xPropertiesFile = System.class.getResourceAsStream(xPropertiesFileName);
            if (xPropertiesFile == null) {
                throw new IllegalArgumentException(ERROR_RESOURCE_NOT_FOUND + ": " + xPropertiesFileName);
            }
            final Properties properties = new Properties();
            try {
                properties.load(xPropertiesFile);
            } catch (IOException e) {
                throw new IllegalArgumentException(ERROR_RESOURCE_IO_ERROR + ": " + xPropertiesFileName, e);
            }

            final Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String propertyPath = (String) propertyNames.nextElement();
                final String propertyValue = properties.getProperty(propertyPath);
                final XProperty xProperty = DefaultXPropertiesReader.readProperty(propertyPath, propertyValue);
                listOfProperties.add(xProperty);
            }
        }
        return listOfProperties;
    }
}
