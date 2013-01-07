package org.reinert.jsonschema;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class HyperSchema extends JsonSchema {

	private ArrayList<Link> mLinks = null;
	private String mFragmentResolution = null;
	private boolean mReadonly = false;
	private String mContentEncoding = null;
	private String mPathStart = null;
	private String mMediaType = null;
//	private ArrayList<String> mRequiredProperties = null;
	public ArrayList<Link> getLinks() {
		return mLinks;
	}
	public void setLinks(ArrayList<Link> links) {
		this.mLinks = links;
	}
	public void addLink(Link link) {
		if (mLinks == null)
			mLinks = new ArrayList<Link>();
		mLinks.add(link);
	}
	public String getFragmentResolution() {
		return mFragmentResolution;
	}
	public void setFragmentResolution(String fragmentResolution) {
		this.mFragmentResolution = fragmentResolution;
	}
	public boolean isReadonly() {
		return mReadonly;
	}
	public void setReadonly(boolean readonly) {
		this.mReadonly = readonly;
	}
	public String getContentEncoding() {
		return mContentEncoding;
	}
	public void setContentEncoding(String contentEncoding) {
		this.mContentEncoding = contentEncoding;
	}
	public String getPathStart() {
		return mPathStart;
	}
	public void setPathStart(String pathStart) {
		this.mPathStart = pathStart;
	}
	public String getMediaType() {
		return mMediaType;
	}
	public void setMediaType(String mediaType) {
		this.mMediaType = mediaType;
	}
//	public ArrayList<String> getRequiredProperties() {
//		return mRequiredProperties;
//	}
//	public void setRequiredProperties(ArrayList<String> requiredProperties) {
//		this.mRequiredProperties = requiredProperties;
//	}
	
}
