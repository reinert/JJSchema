package com.github.reinert.jjschema;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;

/**
 * Mapping of builtin Java types to their matching JSON Schema primitive type
 */
public enum CollectionTypeMappings
{
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

        for (final CollectionTypeMappings mapping: values())
            MAPPINGS.put(mapping.c, mapping.schemaType);
    }

    private final Class<?> c;
    private final String schemaType;

    CollectionTypeMappings(final Class<?> c, final String schemaType)
    {
        this.c = c;
        this.schemaType = schemaType;
    }

    /**
     * Return a primitive type for a given class, if any
     *
     * @param c the class
     * @return the primitive type if found, {@code null} otherwise
     */
    public static String forClass(final Class<?> c)
    {
        return MAPPINGS.get(c);
    }
}
