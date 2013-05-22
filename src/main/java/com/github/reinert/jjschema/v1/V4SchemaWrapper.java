package com.github.reinert.jjschema.v1;

/**
 * @author Danilo Reinert
 */

public class V4SchemaWrapper extends CustomSchemaWrapper {

    public V4SchemaWrapper(Class<?> type) {
        super(type);
    }

    @Override
    public String getId() {
        return getNodeTextValue(node.get("id"));
    }

    @Override
    public String getDollarSchema() {
        return getNodeTextValue(node.get("$schema"));
    }

    @Override
    public String getRef() {
        return getNodeTextValue(node.get("$ref"));
    }


}
