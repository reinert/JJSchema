/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
