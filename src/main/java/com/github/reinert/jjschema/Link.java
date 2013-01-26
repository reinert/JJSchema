package com.github.reinert.jjschema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Link {

	private String mHref = "#";
	private String mRel;
	private HyperSchemaV4 mTargetSchema = null;
	private String mMethod = "GET";
	private String mEncType = null;
	private HyperSchemaV4 mSchema = null;
	
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

	public HyperSchemaV4 getTargetSchema() {
		return mTargetSchema;
	}

	public void setTargetSchema(HyperSchemaV4 targetSchema) {
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

	public HyperSchemaV4 getSchema() {
		return mSchema;
	}

	public void setSchema(HyperSchemaV4 schema) {
		this.mSchema = schema;
	}

}
