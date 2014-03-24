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

package com.github.reinert.jjschema;

import java.util.*;

/**
 * Mapping of builtin Java types to their matching JSON Schema primitive type
 */
public enum CollectionTypeMappings {
    // Set implementations
    HASH_SET(HashSet.class, "array"),
    TREE_SET(TreeSet.class, "array"),
    LINKED_HASH_SET(LinkedHashSet.class, "array"),

    // List implementations
    ARRAY_LIST(ArrayList.class, "array"),
    LINKED_LIST(LinkedList.class, "array"),

    // Deque implementations
    ARRAY_DEQUE(ArrayDeque.class, "array"),

    // Map implementations
    //HASH_MAP(HashMap.class, "object"),
    //TREE_MAP(TreeMap.class, "object"),
    //LINKED_HASH_MAP(LinkedHashMap.class, "object"),

    ABSTRACT_COLLECTION(AbstractCollection.class, "array");

    private static final Map<Class<?>, String> MAPPINGS;

    static {
        // Class objects are all singletons, so we can use that
        MAPPINGS = new IdentityHashMap<Class<?>, String>();

        for (final CollectionTypeMappings mapping : values())
            MAPPINGS.put(mapping.c, mapping.schemaType);
    }

    private final Class<?> c;
    private final String schemaType;

    CollectionTypeMappings(final Class<?> c, final String schemaType) {
        this.c = c;
        this.schemaType = schemaType;
    }

    /**
     * Return a primitive type for a given class, if any
     *
     * @param c the class
     * @return the primitive type if found, {@code null} otherwise
     */
    public static String forClass(final Class<?> c) {
        return MAPPINGS.get(c);
    }
}
