/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.nilportugues.jjschema.model;

import com.github.nilportugues.jjschema.annotation.JsonSchema;
import com.github.nilportugues.jjschema.annotation.Media;
import com.github.nilportugues.jjschema.annotation.Nullable;

public class User {

    @JsonSchema(required = true, title = "ID", minimum = 100000, maximum = 999999)
    private short id;

    @JsonSchema(required = true, description = "User's name")
    private String name;

    @JsonSchema(alias = "gender", description = "User's sex", enums = {"M", "F"})
    @Nullable
    private char sex;

    @JsonSchema(description = "User's personal photo")
    @Media(type = "image/jpg", binaryEncoding = "base64")
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
