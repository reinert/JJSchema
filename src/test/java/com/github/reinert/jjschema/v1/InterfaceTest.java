/*
 * Copyright (c) 2017, Danilo Reinert (daniloreinert@growbit.com)
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

package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.Nullable;
import com.github.reinert.jjschema.exception.UnavailableVersion;
import junit.framework.TestCase;

import java.io.IOException;
import java.time.Instant;

/**
 * @author reinert
 */
public class InterfaceTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public InterfaceTest(String testName) {
        super(testName);
    }

    /**
     * Test the scheme generate following a scheme source, avaliable at
     * http://json-schema.org/examples.html the output should match the example.
     *
     * @throws IOException
     * @throws JsonProcessingException
     *
     */
    public void testGenerateSchema() throws UnavailableVersion, JsonProcessingException, IOException {

        JsonNode fromJavaType = schemaFactory.createSchema(UserInterface.class);
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(fromJavaType));
    }

    interface UserInterface {

        @Attributes(required = true, title = "ID", minimum = 100000, maximum = 999999)
        short getId();

        void setId(short id);

        @Attributes(required = true, description = "User's name")
        String getName();

        void setName(String name);

        @Attributes(description = "User's sex", enums = {"M", "F"})
        @Nullable
        char getSex();

        void setSex(char sex);

        @Attributes(description = "User's personal photo")
        @Nullable
        Byte[] getPhoto();

        void setPhoto(Byte[] photo);

        @Attributes(format = "date-time")
        Instant getBirthday();
    }
}
