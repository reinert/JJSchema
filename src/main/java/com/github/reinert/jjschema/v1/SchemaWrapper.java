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
