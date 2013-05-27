package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.SimpleTypeMappings;

import java.util.Collection;

/**
 * @author Danilo Reinert
 */

public class CustomArraySchemaWrapper extends CustomSchemaWrapper {

    final SchemaWrapper itemsSchemaWrapper;

    public CustomArraySchemaWrapper(Class<?> type, Class<?> parametrizedType) {
        super(type);
        if (parametrizedType != null) {
            if (!Collection.class.isAssignableFrom(type))
                throw new RuntimeException("Cannot instantiate a SchemaWrapper of a non Collection class with a Parametrized Type.");
            this.itemsSchemaWrapper = SchemaWrapperFactory.createWrapper(parametrizedType);
        } else {
            this.itemsSchemaWrapper = null;
        }
    }

    public Class<?> getJavaParametrizedType() {
        return itemsSchemaWrapper.getJavaType();
    }

    public boolean hasParametrizedType() {
        return itemsSchemaWrapper != null;
    }

    protected JsonNode getItems() {
        return getNode().get("items");
    }

    protected void setItems(JsonNode itemsNode) {
        getNode().put("items", itemsNode);
    }
}
