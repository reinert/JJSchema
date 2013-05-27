package com.github.reinert.jjschema.v1;

import com.github.reinert.jjschema.SimpleTypeMappings;

/**
 * @author Danilo Reinert
 */

public class SimpleSchemaWrapper extends SchemaWrapper {

    public SimpleSchemaWrapper(Class<?> type) {
        super(type);
        setType(SimpleTypeMappings.forClass(type));
        processNullable();
    }

    @Override
    public boolean isSimpleWrapper() {
        return true;
    }
}
