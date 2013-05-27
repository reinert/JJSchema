package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 27/05/13
 * Time: 14:42
 *
 * @author Danilo Reinert
 */

public abstract class JsonSchemaFactory {

    private boolean autoPutDollarSchema;

    public abstract JsonNode createSchema(Class<?> type);

    public boolean isAutoPutDollarSchema() {
        return autoPutDollarSchema;
    }

    public void setAutoPutDollarSchema(boolean autoPutDollarSchema) {
        this.autoPutDollarSchema = autoPutDollarSchema;
    }
}
