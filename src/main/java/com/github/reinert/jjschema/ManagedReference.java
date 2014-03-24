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

public class ManagedReference {

    Class<?> collectionType;
    final Class<?> type;
    final String name;
    final Class<?> backwardType;

    public ManagedReference(Class<?> type, String name, Class<?> backwardType) {
        this.type = type;
        this.name = name;
        this.backwardType = backwardType;
    }

    public ManagedReference(Class<?> collectionType, Class<?> type, String name, Class<?> backwardType) {
        this.collectionType = collectionType;
        this.type = type;
        this.name = name;
        this.backwardType = backwardType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((collectionType == null) ? 0 : collectionType.getName().hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.getName().hashCode());
        result = prime * result + ((backwardType == null) ? 0 : backwardType.getName().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ManagedReference other = (ManagedReference) obj;
        if (collectionType == null) {
            if (other.collectionType != null)
                return false;
        } else if (!collectionType.getName().equals(other.collectionType.getName()))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.getName().equals(other.type.getName()))
            return false;
        if (backwardType == null) {
            if (other.backwardType != null)
                return false;
        } else if (!backwardType.getName().equals(other.backwardType.getName()))
            return false;
        return true;
    }

}
