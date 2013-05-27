package com.github.reinert.jjschema.v1;

/**
 * @author Danilo Reinert
 */

public class EmptySchemaWrapper extends SchemaWrapper {

    public EmptySchemaWrapper(Class<?> type) {
        super(type);
    }

    public EmptySchemaWrapper() {
        super(null);
    }

    @Override
    public boolean isEmptyWrapper() {
        return true;
    }

    @Override
    protected void processNullable() {
        return;
    }
}
