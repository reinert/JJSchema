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

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@JsonInclude(Include.NON_DEFAULT)
public abstract class AbstractJsonSchema implements JsonSchema {

	protected String m$schema;
	protected String m$ref;
	protected String mId;
	protected String mTitle;
	protected String mDescription;
	protected Object mDefault;
	protected Integer mMultipleOf;
	protected Long mMaximum;
	protected Boolean mExclusiveMaximum = false;
	protected Integer mMinimum;
	protected Boolean mExclusiveMinimum = false;
	protected Integer mMaxLength;
	protected Integer mMinLength;
	protected String mPattern;
	protected Integer mMaxItems;
	protected Integer mMinItems;
	protected Boolean mUniqueItems = false;
	protected JsonSchema mItems;
	@JsonIgnore
	protected Boolean mSelfRequired = false;
	protected String[] mEnum;
	protected Object mType;
//	protected Map<String,JsonBaseSchema> mProperties;
	protected Map<String,JsonSchema> mProperties;

	public AbstractJsonSchema() {
	}

	public String get$schema() {
		return m$schema;
	}

	public void set$schema(String $schema) {
		m$schema = $schema;
	}

	public String get$ref() {
		return m$ref;
	}

	public void set$ref(String $ref) {
		m$ref = $ref;
	}

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

    public Long getMaximum() {
        return mMaximum;
    }

    public void setMaximum(Long maximum) {
        mMaximum = maximum;
    }

    public Integer getMinimum() {
        return mMinimum;
    }

    public void setMinimum(Integer minimum) {
        mMinimum = minimum;
    }

    public JsonSchema getItems() {
        return mItems;
    }

    public void setItems(JsonSchema items) {
        mItems = items;
    }

    protected Boolean getSelfRequired() {
        return mSelfRequired;
    }

    protected void setSelfRequired(Boolean selfRequired) {
        mSelfRequired = selfRequired;
    }

    public String[] getEnum() {
        return mEnum;
    }

    public void setEnum(String[] values) {
        mEnum = values;
    }
    
    public Object getType() {
        return mType;
    }

    public void setType(Object type) {
        mType = type;
    }

    public Map<String, JsonSchema> getProperties() {
//        return new JsonSchemaPropertiesMapDecorator(mProperties);
    	return mProperties;
    }
    
//    protected Map<String, JsonBaseSchema> getProtectedProperties() {
//        return mProperties;
//    }

    public JsonSchema getProperty(String name) {
        return mProperties.get(name);
    }

    public boolean hasProperties() {
        return mProperties != null;
    }

    public void addProperty(String propertyName, JsonSchema propertyValue) {
        if (mProperties == null) {
            mProperties = new HashMap<String, JsonSchema>();
        }
        mProperties.put(propertyName, (AbstractJsonSchema) propertyValue);
    }

    public Boolean getExclusiveMaximum() {
        return mExclusiveMaximum;
    }

    public void setExclusiveMaximum(Boolean exclusiveMaximum) {
        mExclusiveMaximum = exclusiveMaximum;
    }

    public Boolean getExclusiveMinimum() {
        return mExclusiveMinimum;
    }

    public void setExclusiveMinimum(Boolean exclusiveMinimum) {
        mExclusiveMinimum = exclusiveMinimum;
    }

    public Integer getMaxLength() {
        return mMaxLength;
    }

    public void setMaxLength(Integer maxLength) {
        mMaxLength = maxLength;
    }

    public Integer getMinLength() {
        return mMinLength;
    }

    public void setMinLength(Integer minLength) {
        mMinLength = minLength;
    }

    public String getPattern() {
        return mPattern;
    }

    public void setPattern(String pattern) {
        mPattern = pattern;
    }

    public Integer getMaxItems() {
        return mMaxItems;
    }

    public void setMaxItems(Integer maxItems) {
        mMaxItems = maxItems;
    }

    public Integer getMinItems() {
        return mMinItems;
    }

    public void setMinItems(Integer minItems) {
        mMinItems = minItems;
    }

    public Boolean getUniqueItems() {
        return mUniqueItems;
    }

    public void setUniqueItems(Boolean uniqueItems) {
        mUniqueItems = uniqueItems;
    }

    protected void setProperties(Map<String, JsonSchema> properties) {
        mProperties = properties;
    }

	@Override
	public Object getRequired() {
		throw new UnsupportedOperationException("Not supported in this version.");
	}

	@Override
	public void addRequired(String field) {
		throw new UnsupportedOperationException("Not supported in this version.");	
	}
	
	@Override
	public void setRequired(Object required) {
		throw new UnsupportedOperationException("Not supported in this version.");	
	}
	
	@Override
    public String toString() {
		// Of course this won't remain like this
		ObjectWriter m = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            return m.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
	/*
	class JsonSchemaPropertiesMapDecorator implements Map<String, JsonSchema> {
		
		Map<String, JsonBaseSchema> mProperties;
		
		public JsonSchemaPropertiesMapDecorator(Map<String, JsonBaseSchema> properties) {
			mProperties = properties;
		}

		@Override
		public void clear() {
			mProperties.clear();
		}

		@Override
		public boolean containsKey(Object key) {
			return mProperties.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return mProperties.containsValue(value);
		}

		@Override
		public Set<java.util.Map.Entry<String, JsonSchema>> entrySet() {
			return new JsonSchemaEntrySet(mProperties.entrySet());
		}

		@Override
		public JsonSchema get(Object key) {
			return mProperties.get(key);
		}

		@Override
		public boolean isEmpty() {
			return mProperties.isEmpty();
		}

		@Override
		public Set<String> keySet() {
			return mProperties.keySet();
		}

		@Override
		public JsonSchema put(String key, JsonSchema value) {
			return mProperties.put(key, (JsonBaseSchema) value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void putAll(Map<? extends String, ? extends JsonSchema> m) {
			mProperties.putAll((Map<? extends String, ? extends JsonBaseSchema>) m);
		}

		@Override
		public JsonSchema remove(Object key) {
			return mProperties.remove(key);
		}

		@Override
		public int size() {
			return mProperties.size();
		}

		@Override
		public Collection<JsonSchema> values() {
			return new JsonSchemaCollectionDecorator(mProperties.values());
		}
		
		class JsonSchemaCollectionDecorator implements Collection<JsonSchema> {

			private Collection<JsonBaseSchema> mCol;
			
			public JsonSchemaCollectionDecorator(Collection<JsonBaseSchema> c) {
				this.mCol = c;
			}

			@Override
			public boolean add(JsonSchema arg0) {
				return mCol.add((JsonBaseSchema) arg0);
			}

			@Override
			public boolean addAll(Collection<? extends JsonSchema> arg0) {
//				return false;
				throw new UnsupportedOperationException("Not supported by this decorator.");
			}

			@Override
			public void clear() {
				mCol.clear();
			}

			@Override
			public boolean contains(Object arg0) {
				return mCol.contains(arg0);
			}

			@Override
			public boolean containsAll(Collection<?> arg0) {
				return mCol.containsAll(arg0);
			}

			@Override
			public boolean isEmpty() {
				return mCol.isEmpty();
			}

			@Override
			public Iterator<JsonSchema> iterator() {
				return new JsonSchemaIteratorDecorator(mCol.iterator());
			}

			@Override
			public boolean remove(Object arg0) {
				return mCol.remove(arg0);
			}

			@Override
			public boolean removeAll(Collection<?> arg0) {
				return mCol.removeAll(arg0);
			}

			@Override
			public boolean retainAll(Collection<?> arg0) {
				return mCol.retainAll(arg0);
			}

			@Override
			public int size() {
				return mCol.size();
			}

			@Override
			public Object[] toArray() {
				return mCol.toArray();
			}

			@Override
			public <T> T[] toArray(T[] arg0) {
				return mCol.toArray(arg0);
			}
			
			class JsonSchemaIteratorDecorator implements Iterator<JsonSchema> {

				private Iterator<JsonBaseSchema> mIterator;
				
				public JsonSchemaIteratorDecorator(Iterator<JsonBaseSchema> iterator) {
					this.mIterator = iterator;
				}

				@Override
				public boolean hasNext() {
					return mIterator.hasNext();
				}

				@Override
				public JsonSchema next() {
					return mIterator.next();
				}

				@Override
				public void remove() {
					mIterator.remove();
				}
				
			}
			
		}
	
		class JsonSchemaEntrySet implements Set<java.util.Map.Entry<String, JsonSchema>> {

			Set<java.util.Map.Entry<String, JsonBaseSchema>> mSet;
			
			public JsonSchemaEntrySet(Set<java.util.Map.Entry<String, JsonBaseSchema>> jsonBaseSchemaEntrySet) {
				mSet = jsonBaseSchemaEntrySet;
			}
			
			@Override
			public boolean add(java.util.Map.Entry<String, JsonSchema> e) {
				return mSet.add(new JsonBaseSchemaEntry(e));
			}

			@Override
			public boolean addAll(Collection<? extends java.util.Map.Entry<String, JsonSchema>> c) {
				//return mSet.addAll(c);
				//return false;
				throw new UnsupportedOperationException("Do not use this method.");
			}

			@Override
			public void clear() {
				mSet.clear();
			}

			@Override
			public boolean contains(Object o) {
				return mSet.contains(o);
			}

			@Override
			public boolean containsAll(Collection<?> c) {
				return mSet.containsAll(c);
			}

			@Override
			public boolean isEmpty() {
				return mSet.isEmpty();
			}

			@Override
			public Iterator<java.util.Map.Entry<String, JsonSchema>> iterator() {
				return new JsonSchemaMapIterator(mSet.iterator());
			}

			@Override
			public boolean remove(Object o) {
				return mSet.remove(o);
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				return mSet.removeAll(c);
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				return mSet.retainAll(c);
			}

			@Override
			public int size() {
				return mSet.size();
			}

			@Override
			public Object[] toArray() {
				return mSet.toArray();
			}

			@Override
			public <T> T[] toArray(T[] a) {
				return mSet.toArray(a);
			}

			class JsonSchemaMapIterator implements Iterator<java.util.Map.Entry<String, JsonSchema>> {
				
				Iterator<java.util.Map.Entry<String, JsonBaseSchema>> mIterator;
				
				public JsonSchemaMapIterator(Iterator<java.util.Map.Entry<String, JsonBaseSchema>> iterator) {
					mIterator = iterator;
				}
				
				@Override
				public boolean hasNext() {
					return mIterator.hasNext();
				}

				@Override
				public java.util.Map.Entry<String, JsonSchema> next() {
					return new JsonSchemaEntry(mIterator.next());
				}

				@Override
				public void remove() {
					mIterator.remove();
				}
				
			}
			
			class JsonSchemaEntry implements java.util.Map.Entry<String, JsonSchema> {
				
				java.util.Map.Entry<String, JsonBaseSchema> mEntry;
				
				public JsonSchemaEntry(java.util.Map.Entry<String, JsonBaseSchema> entry) {
					mEntry = entry;
				}
				
				@Override
				public String getKey() {
					return mEntry.getKey();
				}

				@Override
				public JsonSchema getValue() {
					return mEntry.getValue();
				}

				@Override
				public JsonSchema setValue(JsonSchema value) {
					return mEntry.setValue((JsonBaseSchema) value);
				}
				
			}
			
			class JsonBaseSchemaEntry implements java.util.Map.Entry<String, JsonBaseSchema> {
				
				java.util.Map.Entry<String, JsonSchema> mEntry;
				
				public JsonBaseSchemaEntry(java.util.Map.Entry<String, JsonSchema> entry) {
					mEntry = entry;
				}
				
				@Override
				public String getKey() {
					return mEntry.getKey();
				}

				@Override
				public JsonBaseSchema getValue() {
					return (JsonBaseSchema)mEntry.getValue();
				}

				@Override
				public JsonBaseSchema setValue(JsonBaseSchema value) {
					return (JsonBaseSchema) mEntry.setValue((JsonSchema) value);
				}
				
			}

		}
	}
	*/
}