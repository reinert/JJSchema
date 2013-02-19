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
