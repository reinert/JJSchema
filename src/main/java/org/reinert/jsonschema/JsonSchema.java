package org.reinert.jsonschema;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class JsonSchema {

	private String mId = null;
	private String mTitle = null;
	private String mDescription = null;
	private Object mDefault = null;
	private Integer mMultipleOf = null;
	private Integer mMaximum = null;
	private Integer mMinimum = null;
	private JsonSchema mItens = null;
	private Boolean mRequired = null;
	private Object[] mEnum = null;
	private String mType = null;
	private HashMap<String, JsonSchema> mProperties = null;
	public String getId() {
		return mId;
	}
	public void setId(String id) {
		mId = id;
	}
	public String getTitle() {
		return mTitle;
	}
	public void setTitle(String title) {
		mTitle = title;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		mDescription = description;
	}
	public Object getDefault() {
		return mDefault;
	}
	public void setDefault(Object default1) {
		mDefault = default1;
	}
	public Integer getMultipleOf() {
		return mMultipleOf;
	}
	public void setMultipleOf(Integer multipleOf) {
		mMultipleOf = multipleOf;
	}
	public Integer getMaximum() {
		return mMaximum;
	}
	public void setMaximum(Integer maximum) {
		mMaximum = maximum;
	}
	public Integer getMinimum() {
		return mMinimum;
	}
	public void setMinimum(Integer minimum) {
		mMinimum = minimum;
	}
	public JsonSchema getItens() {
		return mItens;
	}
	public void setItens(JsonSchema itens) {
		mItens = itens;
	}
	public Boolean getRequired() {
		return mRequired;
	}
	public void setRequired(Boolean required) {
		mRequired = required;
	}
	public Object[] getEnum() {
		return mEnum;
	}
	public void setEnum(Object... values) {
		mEnum = values;
	}
	public String getType() {
		return mType;
	}
	public void setType(String type) {
		mType = type;
	}
	public HashMap<String, JsonSchema> getProperties() {
		return mProperties;
	}
	public void addProperty(String propertyName, JsonSchema propertyValue) {
		if (mProperties == null) mProperties = new HashMap<String, JsonSchema>();
		mProperties.put(propertyName, propertyValue);
	}
	
}
