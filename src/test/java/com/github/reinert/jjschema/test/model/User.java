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

package com.github.reinert.jjschema.test.model;

import com.github.reinert.jjschema.Media;
import com.github.reinert.jjschema.Nullable;
import com.github.reinert.jjschema.SchemaProperty;


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
