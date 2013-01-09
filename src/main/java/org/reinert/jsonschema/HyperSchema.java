package org.reinert.jsonschema;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import org.reinert.jsonschema.exception.InvalidLinkMethod;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class HyperSchema extends JsonSchema {

	private ArrayList<Link> mLinks = null;
	private String mFragmentResolution = null;
	private boolean mReadonly = false;
	private String mPathStart = null;
	private Media mMedia = null;

	public HyperSchema() {
	}
	public HyperSchema(JsonSchema schema) {
		incorporateSchema(schema);
	}
	
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
	public void setBinaryEncoding(String binaryEncoding) {
		if (mMedia == null) {
			mMedia = new Media();
		}
		mMedia.mBinaryEncoding = binaryEncoding;
	}
	public String getPathStart() {
		return mPathStart;
	}
	public void setPathStart(String pathStart) {
		this.mPathStart = pathStart;
	}
	public void setMediaType(String mediaType) {
		if (mMedia == null) {
			mMedia = new Media();
		}
		mMedia.mType = mediaType;
	}
	public Media getMedia() {
		return mMedia;
	}
//	public HashMap<String, JsonSchema> getProperties() {
//		return mProperties;
//	}
//	@Override
//	public HyperSchema getProperty(String name) {
//		return mProperties.get(name);
//	}
//	public void addProperty(String propertyName, HyperSchema propertyValue) {
//		if (mProperties == null) mProperties = new HashMap<String, HyperSchema>();
//		mProperties.put(propertyName, propertyValue);
//	}
	
	@JsonInclude(Include.NON_EMPTY)
	class Media {
		String mType = null;
		String mBinaryEncoding = null;
		public String getType() {
			return mType;
		}
		public String getBinaryEncoding() {
			return mBinaryEncoding;
		}
	}
	
	protected void incorporateSchema(JsonSchema schema) {
		if (schema != null) {
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
	}
	
	public static <T> HyperSchema generateHyperSchema(Class<T> type) {
		HyperSchema hyperSchema = null;
		Annotation path = type.getAnnotation(Path.class);
		if (path != null) {
			hyperSchema = generateHyperSchemaFromResource(type);
		} else {
			JsonSchema jsonSchema = generateSchema(type);
			if (jsonSchema != null) {
				if (jsonSchema.getType().equals("array")) {
					JsonSchema items = jsonSchema.getItems();
					// NOTE: Customized Iterable Class must declare the Collection object at first
					Field field = type.getDeclaredFields()[0];
					ParameterizedType genericType = (ParameterizedType) field.getGenericType();
			        Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
			        HyperSchema hyperItems = transformJsonToHyperSchema(genericClass, items);
			        jsonSchema.setItems(hyperItems);
			        hyperSchema = new HyperSchema(jsonSchema);
				}
				else if (jsonSchema.hasProperties()) {
					hyperSchema = transformJsonToHyperSchema(type, jsonSchema);
				}
			}
		}
		return hyperSchema;
	}
	
	private static <T> HyperSchema transformJsonToHyperSchema(Class<T> type, JsonSchema jsonSchema) {
		HyperSchema hyperSchema = new HyperSchema(jsonSchema);
		for (String prop : jsonSchema.getProperties().keySet()) {
			try {
				Field field = type.getDeclaredField(prop);
				org.reinert.jsonschema.Media media = field.getAnnotation(org.reinert.jsonschema.Media.class);
				if (media != null) {
					HyperSchema hyperProp = new HyperSchema(jsonSchema.getProperty(prop));
					hyperProp.setMediaType(media.type());
					hyperProp.setBinaryEncoding(media.binaryEncoding());
					jsonSchema.addProperty(prop, hyperProp);
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return hyperSchema;
	}
	
	public static <T> HyperSchema generateHyperSchemaFromResource(Class<T> type) {
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
		String href = null, rel = null, httpMethod = null;
		boolean isLink = false;
		
		Annotation[] ans = method.getAnnotations();
		for (Annotation a : ans) {
			if (a.annotationType().equals(GET.class)) {
				httpMethod = "GET";
				isLink = true;
			}
			else if (a.annotationType().equals(POST.class)) {
				httpMethod = "POST";
				isLink = true;
			}
			else if (a.annotationType().equals(PUT.class)) {
				httpMethod = "PUT";
				isLink = true;
			}
			else if (a.annotationType().equals(DELETE.class)) {
				httpMethod = "DELETE";
				isLink = true;
			}
			else if (a.annotationType().equals(HEAD.class)) {
				throw new RuntimeException("HEAD not yet supported.");
			}
			else if (a.annotationType().equals(Path.class)) {
				Path p = (Path) a;
				href = p.value();
			}
			else if (a.annotationType().equals(Rel.class)) {
				Rel l = (Rel) a;
				rel = l.value();
			}
		}
		
		// Check if the method is actually a link
		if (!isLink) {
			throw new InvalidLinkMethod("Method is not a link. Must use a HTTP METHOD annotation.");
		}
		
		Link link = new Link(href, rel);
		link.setMethod(httpMethod);
		
		// If the rel was not informed than assume the method name
		if (link.getRel() == null || link.getRel().isEmpty()) {
			link.setRel(method.getName());
		}
		
		HyperSchema tgtSchema = generateHyperSchema(method.getReturnType());
		if (tgtSchema != null)
			link.setTargetSchema(tgtSchema);
		
		// Check possible params and form schema attribute.
		// If it has QueryParam or FormParam than the schema must have these params as properties.
		// If it has none of the above and has some ordinary object than the schema must be this
		// object and it is passed by the body.
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length > 0) {
			HyperSchema schema = null;
			boolean hasParam = false;
			boolean hasBodyParam = false;
			for (int i = 0; i < paramTypes.length; i++) {
				Annotation[] paramAns = method.getParameterAnnotations()[i];
				org.reinert.jsonschema.Media media = null;
				String prop = null;
				boolean isBodyParam = true;
				boolean isParam = false;
				for (int j = 0; j < paramAns.length; j++) {
					Annotation a = paramAns[j];
					if (a instanceof QueryParam) {
						if (schema == null) {
							schema = new HyperSchema();
							schema.setType("object");
						}
						QueryParam q = (QueryParam) a;
						schema.addProperty(q.value(), new HyperSchema(JsonSchema.generateSchema(paramTypes[i])));
						prop = q.value();
						hasParam = true;
						isBodyParam = false;
						isParam = true;
					}
					else if (a instanceof FormParam) {
						if (schema == null) {
							schema = new HyperSchema();
							schema.setType("object");
						}
						FormParam q = (FormParam) a;
						schema.addProperty(q.value(), new HyperSchema(JsonSchema.generateSchema(paramTypes[i])));
						prop = q.value();
						hasParam = true;
						isBodyParam = false;
						isParam = true;
					}
					else if (a instanceof PathParam) {
						if (media != null) {
							throw new RuntimeException("Media cannot be declared along with PathParam.");
						}
						for (int k = j+1; k < paramAns.length; k++) {
							Annotation a2 = paramAns[k];
							if (a2 instanceof org.reinert.jsonschema.Media)
								throw new RuntimeException("Media cannot be declared along with PathParam.");
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof CookieParam) {
						if (media != null) {
							media = null;
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof HeaderParam) {
						if (media != null) {
							media = null;
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof MatrixParam) {
						if (media != null) {
							throw new RuntimeException("Media cannot be declared along with MatrixParam.");
						}
						for (int k = j+1; k < paramAns.length; k++) {
							Annotation a2 = paramAns[k];
							if (a2 instanceof org.reinert.jsonschema.Media)
								throw new RuntimeException("Media cannot be declared along with MatrixParam.");
						}
						isBodyParam = false;
						continue;
					}
					else if (a instanceof Context) {
						if (media != null) {
							throw new RuntimeException("Media cannot be declared along with Context.");
						}
						isBodyParam = false;
						continue;
					} 
					else if (a instanceof org.reinert.jsonschema.Media) {
						media = (org.reinert.jsonschema.Media) a;
					}
				}
				if (isBodyParam) {
					hasBodyParam = true;
					schema = new HyperSchema(generateHyperSchema(paramTypes[i]));
					if (media != null) {
						schema.setMediaType(media.type());
						schema.setBinaryEncoding(media.binaryEncoding());
					}
				} else if (isParam) {
					hasParam = true;
					if (media != null) {
						HyperSchema hs = (HyperSchema) schema.getProperty(prop);
						hs.setMediaType(media.type());
						hs.setBinaryEncoding(media.binaryEncoding());
						schema.addProperty(prop, hs);
					}
				}
			}
			
			if (hasBodyParam && hasParam)
				throw new RuntimeException("JsonSchema does not support both FormParam or QueryParam and BodyParam at the same time.");
			
			link.setSchema(schema);
		}
		
		return link;
	}
}
