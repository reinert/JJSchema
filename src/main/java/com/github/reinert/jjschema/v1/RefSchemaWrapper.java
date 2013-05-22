package com.github.reinert.jjschema.v1;

/**
 * @author Danilo Reinert
 */

public class RefSchemaWrapper extends SchemaWrapper {

    public RefSchemaWrapper(Class<?> type) {
        super(type);
        setRef("#");
    }

    public RefSchemaWrapper(Class<?> type, String ref) {
        super(type);
        setRef(ref);
    }

    @Override
    public boolean isRefWrapper() {
        return true;
    }

    @Override
    protected String extractType(Class<?> type) {
        return null;
    }

    public void setRef(String ref) {
        node.put("$ref", ref);
    }
}
