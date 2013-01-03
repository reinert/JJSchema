package org.reinert.jsonschema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class LinkSchema {

	private String mHref;
	private String mRel;
	private JsonSchema mTargetSchema = null;
	private String mMethod = "GET";
	private String mEncType = null;
	private JsonSchema mSchema = null;
	
	public LinkSchema(String href, String rel) {
		super();
		this.mHref = href;
		this.mRel = rel;
	}

	public String getHref() {
		return mHref;
	}

	public void setHref(String href) {
		this.mHref = href;
	}

	public String getRel() {
		return mRel;
	}

	public void setRel(String rel) {
		this.mRel = rel;
	}

	public JsonSchema getTargetSchema() {
		return mTargetSchema;
	}

	public void setTargetSchema(JsonSchema targetSchema) {
		this.mTargetSchema = targetSchema;
	}

	public String getMethod() {
		return mMethod;
	}

	public void setMethod(String method) {
		this.mMethod = method;
	}

	public String getEncType() {
		return mEncType;
	}

	public void setEncType(String encType) {
		if (mMethod != null)
			this.mEncType = encType;
	}

	public JsonSchema getSchema() {
		return mSchema;
	}

	public void setSchema(JsonSchema schema) {
		this.mSchema = schema;
	}

}
