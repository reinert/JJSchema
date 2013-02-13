package com.github.reinert.jjschema.deprecated;

import java.util.Map;

public interface JsonSchema {

	String get$schema();

	void set$schema(String $schema);

	String get$ref();

	void set$ref(String $ref);

	String getId();

	void setId(String id);

	String getTitle();

	void setTitle(String title);

	String getDescription();

	void setDescription(String description);

	Object getDefault();

	void setDefault(Object default1);

	Integer getMultipleOf();

	void setMultipleOf(Integer multipleOf);

	Long getMaximum();

	void setMaximum(Long maximum);

	Integer getMinimum();

	void setMinimum(Integer minimum);

	JsonSchema getItems();

	void setItems(JsonSchema items);

	Object getRequired();

	void addRequired(String field);
	
	void setRequired(Object required);

	String[] getEnum();

	void setEnum(String[] values);

	Object getType();

	void setType(Object type);

	Map<String, JsonSchema> getProperties();

	JsonSchema getProperty(String name);

	boolean hasProperties();

	void addProperty(String propertyName, JsonSchema propertyValue);

	Boolean getExclusiveMaximum();

	void setExclusiveMaximum(Boolean exclusiveMaximum);

	Boolean getExclusiveMinimum();

	void setExclusiveMinimum(Boolean exclusiveMinimum);

	Integer getMaxLength();

	void setMaxLength(Integer maxLength);

	Integer getMinLength();

	void setMinLength(Integer minLength);

	String getPattern();

	void setPattern(String pattern);

	Integer getMaxItems();

	void setMaxItems(Integer maxItems);

	Integer getMinItems();

	void setMinItems(Integer minItems);

	Boolean getUniqueItems();

	void setUniqueItems(Boolean uniqueItems);

	String toString();

}