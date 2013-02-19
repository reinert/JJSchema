/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.reinert.jjschema.deprecated;

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
