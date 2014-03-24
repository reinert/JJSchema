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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.reinert.jjschema.Nullable;

/**
 * @author Danilo Reinert
 */

public abstract class SchemaWrapper {
    private final Class<?> type;
    private final ObjectNode node = SchemaWrapperFactory.MAPPER.createObjectNode();

    public SchemaWrapper(Class<?> type) {
        this.type = type;
    }

    public JsonNode asJson() {
        return node;
    }

    public String getDollarSchema() {
        return getNodeTextValue(node.get("$schema"));
    }

    public SchemaWrapper putDollarSchema() {
        node.put("$schema", SchemaVersion.DRAFTV4.getLocation().toString());
        return this;
    }

    public String getId() {
        return getNodeTextValue(node.get("id"));
    }

    public String getRef() {
        return getNodeTextValue(node.get("$ref"));
    }

    public String getType() {
        return getNodeTextValue(node.get("type"));
    }

    public Class<?> getJavaType() {
        return type;
    }

    public boolean isEnumWrapper() {
        return false;
    }

    public boolean isSimpleWrapper() {
        return false;
    }

    public boolean isCustomWrapper() {
        return false;
    }

    public boolean isRefWrapper() {
        return false;
    }

    public boolean isArrayWrapper() {
        return false;
    }

    public boolean isEmptyWrapper() {
        return false;
    }

    public boolean isNullWrapper() {
        return false;
    }

    public <T extends SchemaWrapper> T cast() {
        return (T) this;
    }

    protected ObjectNode getNode() {
        return node;
    }

    // TODO: Shouldn't I check the Nullable annotation only on fields or methods?
    protected void processNullable() {
        final Nullable nullable = type.getAnnotation(Nullable.class);
        if (nullable != null) {
            String oldType = node.get("type").asText();
            ArrayNode typeArray = node.putArray("type");
            typeArray.add(oldType);
            typeArray.add("null");
        }
    }

    protected String getNodeTextValue(JsonNode node) {
        return node == null ? null : node.textValue();
    }

    protected void setType(String type) {
        node.put("type", type);
    }
}
