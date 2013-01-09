package org.reinert.jsonschema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Link {

	private String mHref = "#";
	private String mRel;
	private HyperSchema mTargetSchema = null;
	private String mMethod = "GET";
	private String mEncType = null;
	private HyperSchema mSchema = null;
	
	public Link(String href, String rel) {
		mHref = href != null ? href : mHref;
		mRel = rel;
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

	public HyperSchema getTargetSchema() {
		return mTargetSchema;
	}

	public void setTargetSchema(HyperSchema targetSchema) {
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

	public HyperSchema getSchema() {
		return mSchema;
	}

	public void setSchema(HyperSchema schema) {
		this.mSchema = schema;
	}

}
