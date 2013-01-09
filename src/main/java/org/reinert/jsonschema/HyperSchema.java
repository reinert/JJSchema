package org.reinert.jsonschema;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.reinert.jsonschema.exception.InvalidLinkMethod;

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
	
	protected void incorporateSchema(JsonSchema schema) {
		setDefault(schema.getDefault());
		setDescription(schema.getDescription());
		setEnum(schema.getEnum());
		setExclusiveMaximum(schema.getExclusiveMaximum());
		setExclusiveMinimum(schema.getExclusiveMinimum());
		setId(schema.getId());
		setItems(schema.getItems());
		setMaximum(schema.getMaximum());
		setMinimum(schema.getMinimum());
		setMinItems(schema.getMinItems());
		setMaxItems(schema.getMaxItems());
		setMinLength(schema.getMinLength());
		setMultipleOf(schema.getMultipleOf());
		setPattern(schema.getPattern());
		setProperties(schema.getProperties());
		setRequired(schema.getRequired());
		setTitle(schema.getTitle());
		setType(schema.getType());
	}
	
	
	
	public static <T> HyperSchema generateHyperSchema(Class<T> type) {
		HyperSchema schema = null;
		
		Annotation[] ans = type.getAnnotations();
		boolean hasPath = false;
		for (Annotation a : ans) {
			if (a instanceof Path) {
				hasPath = true;
				Path p = (Path) a;
				if (schema == null) {
					schema = new HyperSchema();
				}
				schema.setPathStart(p.value());
			}
			if (a instanceof Produces) {
				Produces p = (Produces) a;
				if (schema == null) {
					schema = new HyperSchema();
				}
				schema.setMediaType(p.value()[0]);
			}
		}
		if (!hasPath) {
			throw new RuntimeException("Invalid Resource class. Must use Path annotation.");
		}
		
		for (Method method : type.getMethods()) {
			try {
				Link link = generateLink(method);
				if (link.getMethod().equals("GET") && link.getHref().equals("#")) {
					schema.incorporateSchema(link.getTargetSchema());
				} else {
					schema.addLink(link);
				}
			} catch(InvalidLinkMethod e){}
		}
		
		return schema;
	}
	
	
	static Link generateLink(Method method) throws InvalidLinkMethod {
		Link link = new Link();
		boolean isLink = false;
		
		Annotation[] ans = method.getAnnotations();
		for (Annotation a : ans) {
			if (a.annotationType().equals(GET.class)) {
				link.setMethod("GET");
				isLink = true;
			}
			else if (a.annotationType().equals(POST.class)) {
				link.setMethod("POST");
				isLink = true;
			}
			else if (a.annotationType().equals(PUT.class)) {
				link.setMethod("PUT");
				isLink = true;
			}
			else if (a.annotationType().equals(DELETE.class)) {
				link.setMethod("DELETE");
				isLink = true;
			}
			else if (a.annotationType().equals(Path.class)) {
				Path p = (Path) a;
				link.setHref(p.value());
			}
			else if (a.annotationType().equals(LinkProperty.class)) {
				LinkProperty l = (LinkProperty) a;
				link.setRel(l.rel());
			}
		}
		
		if (!isLink) {
			throw new InvalidLinkMethod("Method is not a link. Must use a HTTP METHOD annotation.");
		}
		
		link.setTargetSchema(JsonSchema.generateSchema(method.getReturnType()));
		
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length == 1) {
			JsonSchema schema = null;
			Annotation[] paramAns = method.getParameterAnnotations()[0];
			boolean isParam = false;
			for (Annotation a : paramAns) {
				if (a.annotationType().equals(QueryParam.class)) {
					schema = new JsonSchema();
					QueryParam q = (QueryParam) a;
					schema.setType("object");
					schema.addProperty(q.value(), JsonSchema.generateSchema(paramTypes[0]));
					isParam = true;
				}
				else if (a.annotationType().equals(PathParam.class)) {
					isParam = true;
				}
				else if (a.annotationType().equals(CookieParam.class)) {
					isParam = true;
				}
				else if (a.annotationType().equals(FormParam.class)) {
					isParam = true;
				}
				else if (a.annotationType().equals(HeaderParam.class)) {
					isParam = true;
				}
				else if (a.annotationType().equals(MatrixParam.class)) {
					isParam = true;
				}
			}
			if (!isParam) {
				schema = JsonSchema.generateSchema(paramTypes[0]);
			}
			link.setSchema(schema);
		} 
		else if (paramTypes.length > 1) {
			JsonSchema schema = new JsonSchema();
			schema.setType("object");
			for (int i = 0; i < paramTypes.length; i++) {
				Annotation[] paramAns = method.getParameterAnnotations()[i];
				boolean isParam = false;
				for (Annotation a : paramAns) {
					if (a.annotationType().equals(QueryParam.class)) {
						QueryParam q = (QueryParam) a;
						schema.addProperty(q.value(), JsonSchema.generateSchema(paramTypes[i]));
						isParam = true;
					}
					else if (a.annotationType().equals(PathParam.class)) {
						isParam = true;
					}
					else if (a.annotationType().equals(CookieParam.class)) {
						isParam = true;
					}
					else if (a.annotationType().equals(FormParam.class)) {
						isParam = true;
					}
					else if (a.annotationType().equals(HeaderParam.class)) {
						isParam = true;
					}
					else if (a.annotationType().equals(MatrixParam.class)) {
						isParam = true;
					}
				}
				if (!isParam) {
					schema.addProperty("body", JsonSchema.generateSchema(paramTypes[i]));
				}
			}
			link.setSchema(schema);
		}
		
		return link;
	}
}
