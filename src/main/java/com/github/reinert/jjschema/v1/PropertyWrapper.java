package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.ManagedReference;
import com.github.reinert.jjschema.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Set;

/**
 * @author Danilo Reinert
 */

public class PropertyWrapper extends SchemaWrapper {

    enum ReferenceType {NONE, FORWARD, BACKWARD}

    final CustomSchemaWrapper ownerSchemaWrapper;
    final SchemaWrapper schemaWrapper;
    final Field field;
    final Method method;
    String name;
    String relativeId = "#";
    boolean required;
    ManagedReference managedReference;
    ReferenceType referenceType;

    public PropertyWrapper(CustomSchemaWrapper ownerSchemaWrapper, Set<ManagedReference> managedReferences, Method method, Field field) {
        super(null);

        if (method == null)
            throw new RuntimeException("Error at " + ownerSchemaWrapper.getJavaType().getName() + ": Cannot instantiate a PropertyWrapper with a null method.");

        this.ownerSchemaWrapper = ownerSchemaWrapper;
        this.field = field;
        this.method = method;

        String relativeId;

        Class<?> propertyType = method.getReturnType();
        Class<?> collectionType = null;
        final String propertiesStr = "/properties/";
        String itemsStr = "/items";
        if (Collection.class.isAssignableFrom(propertyType)) {
            collectionType = method.getReturnType();
            ParameterizedType genericType = (ParameterizedType) method
                    .getGenericReturnType();
            propertyType = (Class<?>) genericType.getActualTypeArguments()[0];

            relativeId = propertiesStr + getName() + itemsStr;
            addTokenToRelativeId(relativeId);
        } else {
            relativeId = propertiesStr + getName();
            addTokenToRelativeId(relativeId);
        }

        processReference(propertyType);

        if (getReferenceType() == ReferenceType.BACKWARD) {
            SchemaWrapper schemaWrapper;
            String id = processId(method.getReturnType());
            if (id != null) {
                schemaWrapper = new RefSchemaWrapper(propertyType, id);
                ownerSchemaWrapper.pushReference(getManagedReference());
            } else {
                if (ownerSchemaWrapper.pushReference(getManagedReference())) {
                    String relativeId1 = ownerSchemaWrapper.getRelativeId();
                    if (relativeId1.endsWith(itemsStr)) {
                        relativeId1 = relativeId1.substring(0, relativeId1.substring(0, relativeId1.length() - itemsStr.length()).lastIndexOf("/") - (propertiesStr.length()-1));
                    } else {
                        relativeId1 = relativeId1.substring(0, relativeId1.lastIndexOf("/") - (propertiesStr.length()-1));
                    }
                    schemaWrapper = new RefSchemaWrapper(propertyType, relativeId1);
                }
                else
                    schemaWrapper = new EmptySchemaWrapper();
            }
            if (schemaWrapper.isRefWrapper() && collectionType != null)
                this.schemaWrapper = SchemaWrapperFactory.createArrayRefWrapper((RefSchemaWrapper) schemaWrapper);
            else
                this.schemaWrapper = schemaWrapper;
        } else {
            if (getReferenceType() == ReferenceType.FORWARD) {
                ownerSchemaWrapper.pullReference(getManagedReference());
            }
            String relativeId1 = ownerSchemaWrapper.getRelativeId() + relativeId;
            if (collectionType != null) {
                this.schemaWrapper = SchemaWrapperFactory.createArrayWrapper(collectionType, propertyType, managedReferences, relativeId1);
            } else {
                this.schemaWrapper = SchemaWrapperFactory.createWrapper(propertyType, managedReferences, relativeId1);
            }
            processAttributes(getNode(), getAccessibleObject());
            processNullable();
        }
    }

    public Field getField() {
        return field;
    }

    public Method getMethod() {
        return method;
    }

    public SchemaWrapper getOwnerSchema() {
        return ownerSchemaWrapper;
    }

    public String getName() {
        if (name == null)
            name = processPropertyName();
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public ManagedReference getManagedReference() {
        return managedReference;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public boolean isReference() {
        return managedReference != null;
    }

    public String getRelativeId() {
        return relativeId;
    }

    @Override
    public JsonNode asJson() {
        return schemaWrapper.asJson();
    }

    @Override
    public String getDollarSchema() {
        return schemaWrapper.getDollarSchema();
    }

    @Override
    public String getId() {
        return schemaWrapper.getId();
    }

    @Override
    public String getRef() {
        return schemaWrapper.getRef();
    }

    @Override
    public String getType() {
        return schemaWrapper.getType();
    }

    @Override
    public Class<?> getJavaType() {
        return schemaWrapper.getJavaType();
    }

    @Override
    public boolean isEnumWrapper() {
        return schemaWrapper.isEnumWrapper();
    }

    @Override
    public boolean isSimpleWrapper() {
        return schemaWrapper.isSimpleWrapper();
    }

    @Override
    public boolean isCustomWrapper() {
        return schemaWrapper.isCustomWrapper();
    }

    @Override
    public boolean isRefWrapper() {
        return schemaWrapper.isRefWrapper();
    }

    @Override
    public boolean isArrayWrapper() {
        return schemaWrapper.isArrayWrapper();
    }

    @Override
    public boolean isNullWrapper() {
        return schemaWrapper.isNullWrapper();
    }

    @Override
    public boolean isEmptyWrapper() {
        return schemaWrapper.isEmptyWrapper();
    }

    @Override
    public boolean isPropertyWrapper() {
        return true;
    }

    @Override
    public <T extends SchemaWrapper> T cast() {
        return schemaWrapper.cast();
    }

    protected void addTokenToRelativeId(String token) {
        relativeId = relativeId + "/" + token;
    }

    protected void setRequired(boolean required) {
        this.required = required;
    }

    protected AccessibleObject getAccessibleObject() {
        return (field == null) ? method : field;
    }

    protected String processId(Class<?> accessibleObject) {
        final Attributes attributes = accessibleObject.getAnnotation(Attributes.class);
        if (attributes != null) {
            if (!attributes.id().isEmpty()) {
                return attributes.id();
            }
        }
        return null;
    }

    protected void processAttributes(ObjectNode node, AccessibleObject accessibleObject) {
        final Attributes attributes = accessibleObject.getAnnotation(Attributes.class);
        if (attributes != null) {
            //node.put("$schema", SchemaVersion.DRAFTV4.getLocation().toString());
            node.remove("$schema");
            if (!attributes.id().isEmpty()) {
                node.put("id", attributes.id());
            }
            if (!attributes.description().isEmpty()) {
                node.put("description", attributes.description());
            }
            if (!attributes.pattern().isEmpty()) {
                node.put("pattern", attributes.pattern());
            }
            if (!attributes.title().isEmpty()) {
                node.put("title", attributes.title());
            }
            if (attributes.maximum() > -1) {
                node.put("maximum", attributes.maximum());
            }
            if (attributes.exclusiveMaximum()) {
                node.put("exclusiveMaximum", true);
            }
            if (attributes.minimum() > -1) {
                node.put("minimum", attributes.minimum());
            }
            if (attributes.exclusiveMinimum()) {
                node.put("exclusiveMinimum", true);
            }
            if (attributes.enums().length > 0) {
                ArrayNode enumArray = node.putArray("enum");
                String[] enums = attributes.enums();
                for (String v : enums) {
                    enumArray.add(v);
                }
            }
            if (attributes.uniqueItems()) {
                node.put("uniqueItems", true);
            }
            if (attributes.minItems() > 0) {
                node.put("minItems", attributes.minItems());
            }
            if (attributes.maxItems() > -1) {
                node.put("maxItems", attributes.maxItems());
            }
            if (attributes.multipleOf() > 0) {
                node.put("multipleOf", attributes.multipleOf());
            }
            if (attributes.minLength() > 0) {
                node.put("minLength", attributes.minItems());
            }
            if (attributes.maxLength() > -1) {
                node.put("maxLength", attributes.maxItems());
            }
            if (attributes.required()) {
                setRequired(true);
            }
        }
    }

    protected void processReference(Class<?> propertyType) {
        boolean referenceExists = false;

        JsonManagedReference refAnn = getAccessibleObject().getAnnotation(JsonManagedReference.class);
        if (refAnn != null) {
            referenceExists = true;
            managedReference = new ManagedReference(getOwnerSchema().getJavaType(), refAnn.value(), propertyType);
            referenceType = ReferenceType.FORWARD;
        }

        JsonBackReference backRefAnn = getAccessibleObject().getAnnotation(JsonBackReference.class);
        if (backRefAnn != null) {
            if (referenceExists)
                throw new RuntimeException("Error at " + getOwnerSchema().getJavaType().getName() + ": Cannot reference " + propertyType.getName() + " both as Managed and Back Reference.");
            managedReference = new ManagedReference(propertyType, backRefAnn.value(), getOwnerSchema().getJavaType());
            referenceType = ReferenceType.BACKWARD;
        }
    }

    @Override
    protected ObjectNode getNode() {
        return schemaWrapper.getNode();
    }

    @Override
    protected void processNullable() {
        final Nullable nullable = getAccessibleObject().getAnnotation(Nullable.class);
        if (nullable != null) {
            if (isEnumWrapper()) {
                ((ArrayNode) getNode().get("enum")).add("null");
            } else {
                String oldType = getType();
                ArrayNode typeArray = getNode().putArray("type");
                typeArray.add(oldType);
                typeArray.add("null");
            }
        }
    }

    @Override
    protected String getNodeTextValue(JsonNode node) {
        return schemaWrapper.getNodeTextValue(node);
    }

    @Override
    protected void setType(String type) {
        schemaWrapper.setType(type);
    }

    private String processPropertyName() {
        String name = (field == null) ? firstToLowerCase(method.getName()
                .replace("get", "")) : field.getName();
        return name;
    }

    private String firstToLowerCase(String string) {
        return Character.toLowerCase(string.charAt(0))
                + (string.length() > 1 ? string.substring(1) : "");
    }
}
