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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.ManagedReference;
import com.github.reinert.jjschema.SimpleTypeMappings;

import java.util.AbstractCollection;
import java.util.Set;

/**
 * @author Danilo Reinert
 */

public class SchemaWrapperFactory {

    public static final ObjectMapper MAPPER = new ObjectMapper();
    
    private SchemaWrapperFactory() {}

    public static SchemaWrapper createWrapper(Class<?> type) {
        return createWrapper(type, null);
    }

    public static SchemaWrapper createArrayWrapper(Class<?> type, Class<?> parametrizedType, boolean ignoreProperties) {
        return new ArraySchemaWrapper(type, parametrizedType, ignoreProperties);
    }

    public static SchemaWrapper createWrapper(Class<?> type, Set<ManagedReference> managedReferences) {
        return createWrapper(type, managedReferences, null, false);
    }

    public static SchemaWrapper createWrapper(Class<?> type, Set<ManagedReference> managedReferences, String relativeId, boolean ignoreProperties) {
        // If it is void then return null
        if (type == Void.class || type == void.class || type == null) {
            return new NullSchemaWrapper(type);
        }
        // If it is a simple type, then just put the type
        else if (SimpleTypeMappings.isSimpleType(type)) {
            return new SimpleSchemaWrapper(type);
        }
        // If it is an Enum than process like enum
        else if (type.isEnum()) {
            return new EnumSchemaWrapper(type);
        }
        // If none of the above possibilities were true, then it is a custom object
        else {
            if (managedReferences != null)
                if (relativeId != null)
                    return new CustomSchemaWrapper(type, managedReferences, relativeId, ignoreProperties);
                else
                    return new CustomSchemaWrapper(type, managedReferences, ignoreProperties);
            else
                return new CustomSchemaWrapper(type, ignoreProperties);
        }
    }

    public static SchemaWrapper createArrayWrapper(Class<?> type, Class<?> parametrizedType, Set<ManagedReference> managedReferences, boolean ignoreProperties) {
        return new ArraySchemaWrapper(type, parametrizedType, managedReferences, ignoreProperties);
    }

    public static SchemaWrapper createArrayWrapper(Class<?> type, Class<?> parametrizedType, Set<ManagedReference> managedReferences, String relativeId, boolean ignoreProperties) {
        return new ArraySchemaWrapper(type, parametrizedType, managedReferences, relativeId, ignoreProperties);
    }

    public static SchemaWrapper createArrayRefWrapper(RefSchemaWrapper refSchemaWrapper) {
        return new ArraySchemaWrapper(AbstractCollection.class, refSchemaWrapper);
    }
}
