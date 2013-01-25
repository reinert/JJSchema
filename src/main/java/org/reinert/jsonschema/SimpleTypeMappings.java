package org.reinert.jsonschema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Mapping of builtin Java types to their matching JSON Schema primitive type
 */
public enum SimpleTypeMappings
{
    // Integer types
    PRIMITIVE_BYTE(byte.class, "integer"),
    PRIMITIVE_SHORT(short.class, "integer"),
    PRIMITIVE_INTEGER(int.class, "integer"),
    PRIMITIVE_LONG(long.class, "integer"),
    BYTE(Byte.class, "integer"),
    SHORT(Short.class, "integer"),
    INTEGER(Integer.class, "integer"),
    LONG(Long.class, "integer"),
    BIGINTEGER(BigInteger.class, "integer"),
    // Number types
    PRIMITIVE_FLOAT(float.class, "number"),
    PRIMITIVE_DOUBLE(double.class, "number"),
    FLOAT(Float.class, "number"),
    DOUBLE(Double.class, "number"),
    BIGDECIMAL(BigDecimal.class, "number"),
    // Boolean types
    PRIMITIVE_BOOLEAN(boolean.class, "boolean"),
    BOOLEAN(Boolean.class, "boolean"),
    // String types
    PRIMITIVE_CHAR(char.class, "string"),
    CHAR(Character.class, "string"),
    CHARSEQUENCE(CharSequence.class, "string"),
    STRING(String.class, "string");

    private static final Map<Class<?>, String> MAPPINGS;

    static {
        // Class objects are all singletons, so we can use that
        MAPPINGS = new IdentityHashMap<Class<?>, String>();

        for (final SimpleTypeMappings mapping: values())
            MAPPINGS.put(mapping.c, mapping.schemaType);
    }

    private final Class<?> c;
    private final String schemaType;

    SimpleTypeMappings(final Class<?> c, final String schemaType)
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
