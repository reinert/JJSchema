package com.github.reinert.jjschema.v1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Danilo Reinert
 */

public abstract class PropertyWrapper extends SchemaWrapper {

    final Field field;
    final Method method;

    public PropertyWrapper(Class<?> type, Field field, Method method) {
        super(type);
        this.field = field;
        this.method = method;
    }

    public Field getField() {
        return field;
    }

    public Method getMethod() {
        return method;
    }
}
