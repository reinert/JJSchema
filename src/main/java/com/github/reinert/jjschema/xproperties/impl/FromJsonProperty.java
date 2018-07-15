package com.github.reinert.jjschema.xproperties.impl;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.xproperties.api.XProperty;

/**
 * From <code>@JsonProperty</code> Read Implementation
 * 
 * Provides support of <code>@JsonProperty(?)</code>.
 * 
 * @author WhileTrueEndWhile
 */
class FromJsonProperty extends FromFields {

    /**
     * Fly Weight Instance
     */
    public static final FromJsonProperty instance = new FromJsonProperty();

    /**
     * 
     * Reads X Properties from one field.
     * 
     * 
     * @param type
     *                      The class containing the field read from.
     * 
     * @param schema
     *                      Schema of the class containing the field to read from.
     * 
     * @param fieldName
     *                      Name of the field to read X Properties from.
     * 
     * @param accessibleObj
     *                      A field to read X Properties from.
     * 
     * @return A list of X Properties
     * 
     */
    @Override
    protected List<XProperty> readXProperties(Class<?> type, ObjectNode schema, String fieldName,
            AccessibleObject accessibleObj) {

        final List<XProperty> listOfProperties = new ArrayList<>();
        final JsonProperty jsonProperty = accessibleObj.getAnnotation(JsonProperty.class);
        if (jsonProperty == null) {
            return listOfProperties;
        }
        final boolean required = jsonProperty.required();
        final String defaultValue = jsonProperty.defaultValue();
        final String value = jsonProperty.value();

        //
        // @JsonProperty(required=true)
        //

        if (required) {
            final ArrayNode requiredArray = (ArrayNode) schema.get(JSON_SCHEMA_REQUIRED);
            final List<String> requiredList = new ArrayList<>();

            if (requiredArray != null) {
                for (int i = 0; i < requiredArray.size(); ++i) {
                    requiredList.add(requiredArray.get(i).asText());
                }
            }

            //
            // Check if required entry already exists...
            //

            final int index = requiredList.indexOf(fieldName);
            if (value == null || value.isEmpty()) {
                if (index < 0) {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, -1), fieldName));
                } else {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, index), fieldName));
                }
            } else {
                if (index < 0) {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, -1), value));
                } else {
                    listOfProperties.add(new DefaultXProperty(
                            Arrays.asList(JSON_SCHEMA_REQUIRED, index), value));
                }

            }
        }

        //
        // @JsonProperty(defaultValue=???)
        //

        if (!defaultValue.isEmpty()) {
            listOfProperties.add(new DefaultXProperty(
                    Arrays.asList(JSON_SCHEMA_PROPERTIES, fieldName, JSON_SCHEMA_DEFAULT), defaultValue));
        }

        //
        // @JsonProperty(???)
        //

        if (!value.isEmpty()) {
            listOfProperties.add(
                    new DefaultXProperty(Arrays.asList(JSON_SCHEMA_PROPERTIES, value),
                            schema.get(JSON_SCHEMA_PROPERTIES).get(fieldName)));
            listOfProperties.add(
                    new DefaultXProperty(Arrays.asList(JSON_SCHEMA_PROPERTIES, fieldName), null));
        }
        return listOfProperties;
    }

    protected FromJsonProperty() {
    }
}
