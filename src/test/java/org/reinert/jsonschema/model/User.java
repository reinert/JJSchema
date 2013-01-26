package org.reinert.jsonschema.model;

import org.reinert.jsonschema.Media;
import org.reinert.jsonschema.Nullable;
import org.reinert.jsonschema.SchemaProperty;


public class User {

	@SchemaProperty(required=true, title="ID", minimum=100000, maximum=999999)
	private short id;
	
	@SchemaProperty(required=true, description="User's name")
	private String name;
	
	@SchemaProperty(description="User's sex", enums={"M","F"})
	@Nullable
	private char sex;
	
	@SchemaProperty(description="User's personal photo")
	@Media(type="image/jpg", binaryEncoding="base64")
	@Nullable
	private Byte[] photo;

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}

}
