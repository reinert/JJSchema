/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.reinert.jjschema.v1;

import java.lang.reflect.Type;

/**
 * @author Danilo Reinert
 */

public class RefSchemaWrapper extends SchemaWrapper {

    public RefSchemaWrapper(Class<?> type) {
        super(type);
        setRef("#");
    }

    public RefSchemaWrapper(Type propertyType, String ref) {
        super(propertyType);
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
