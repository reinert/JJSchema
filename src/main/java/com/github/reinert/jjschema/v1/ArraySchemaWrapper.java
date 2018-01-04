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

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.ManagedReference;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

/**
 * @author Danilo Reinert
 */

public class ArraySchemaWrapper extends SchemaWrapper {

    final SchemaWrapper itemsSchemaWrapper;

    public ArraySchemaWrapper(Class<?> type, Type propertyType, Set<ManagedReference> managedReferences, String relativeId, boolean ignoreProperties) {
        super(type);
        setType("array");
        if (propertyType != null) {
            if (!Collection.class.isAssignableFrom(type))
                throw new RuntimeException("Cannot instantiate a SchemaWrapper of a non Collection class with a Parametrized Type.");
            if (managedReferences == null)
                this.itemsSchemaWrapper = SchemaWrapperFactory.createWrapper(propertyType);
            else
                this.itemsSchemaWrapper = SchemaWrapperFactory.createWrapper(propertyType, managedReferences, relativeId, ignoreProperties);
            setItems(this.itemsSchemaWrapper.asJson());
        } else {
            this.itemsSchemaWrapper = null;
        }
    }

    public ArraySchemaWrapper(Class<?> type, Class<?> parametrizedType, Set<ManagedReference> managedReferences, boolean ignoreProperties) {
        this(type, parametrizedType, managedReferences, null, ignoreProperties);
    }

    public ArraySchemaWrapper(Class<?> type, Class<?> parametrizedType, boolean ignoreProperties) {
        this(type, parametrizedType, null, ignoreProperties);
    }

    public ArraySchemaWrapper(Class<?> type, RefSchemaWrapper refSchemaWrapper) {
        super(type);
        setType("array");
        this.itemsSchemaWrapper = refSchemaWrapper;
        setItems(this.itemsSchemaWrapper.asJson());
    }

    public Class<?> getJavaParametrizedType() {
        return itemsSchemaWrapper.getJavaType();
    }

    public SchemaWrapper getItemsSchema() {
        return itemsSchemaWrapper;
    }

    @Override
    public boolean isArrayWrapper() {
        return true;
    }

    protected JsonNode getItems() {
        return getNode().get("items");
    }

    protected void setItems(JsonNode itemsNode) {
        getNode().put("items", itemsNode);
    }
}
