package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 27/05/13
 * Time: 14:46
 *
 * @author Danilo Reinert
 */

public class JsonSchemaV4Factory extends JsonSchemaFactory {

    @Override
    public JsonNode createSchema(Class<?> type) {
        SchemaWrapper schemaWrapper = SchemaWrapperFactory.createWrapper(type);
        if (isAutoPutDollarSchema())
            schemaWrapper.putDollarSchema();
        return schemaWrapper.asJson();
    }
}
