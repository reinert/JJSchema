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

    public void setRef(String ref) {
        getNode().put("$ref", ref);
    }
}
