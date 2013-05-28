package com.github.reinert.jjschema.v1;

/**
 * @author Danilo Reinert
 */

public class NullSchemaWrapper extends SchemaWrapper {

    public NullSchemaWrapper(Class<?> type) {
        super(type);
        setType("null");
    }

    @Override
    public boolean isNullWrapper() {
        return true;
    }

    @Override
    protected void processNullable() {
        return;
    }
}
