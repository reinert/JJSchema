package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Danilo Reinert
 */

public abstract class SchemaWrapper {
    final Class<?> type;
    final ObjectNode node = SchemaCreator.MAPPER.createObjectNode();

    public SchemaWrapper(Class<?> type) {
        this.type = type;
        String v = extractType(type);
        if (v != null)
            node.put("type", v);
    }

    protected abstract String extractType(Class<?> type);

    public JsonNode asJson() {
        return node;
    }

    public String getDollarSchema() {
        return getNodeTextValue(node.get("$schema"));
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

    public <T extends SchemaWrapper> T cast() {
        return (T) this;
    }

    protected String getNodeTextValue(JsonNode node) {
        return node == null ? null : node.textValue();
    }
}
