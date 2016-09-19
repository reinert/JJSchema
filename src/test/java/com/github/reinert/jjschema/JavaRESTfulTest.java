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

package com.github.reinert.jjschema;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.reinert.jjschema.exception.TypeException;
import com.github.reinert.jjschema.model.User;
import com.github.reinert.jjschema.rest.UserResource;
import junit.framework.TestCase;

public class JavaRESTfulTest extends TestCase {

    private ObjectWriter om = new ObjectMapper().writerWithDefaultPrettyPrinter();
    private JsonSchemaGenerator v4hyperGenerator = SchemaGeneratorBuilder.draftV4HyperSchema().setAutoPutSchemaVersion(false).build();

    public void testHyperSchema() throws JsonProcessingException, TypeException  {
        JsonNode userHyperSchema = v4hyperGenerator.generateSchema(User.class);

        //Testing property aliasing
        assertNotNull(userHyperSchema.get("properties").get("gender"));

        //Testing property values property serialized.
        assertEquals("image/jpg", userHyperSchema.get("properties").get("photo").get("mediaType").asText());
        assertEquals("base64", userHyperSchema.get("properties").get("photo").get("binaryEncoding").asText());

        JsonNode userResourceHyperSchema = v4hyperGenerator.generateSchema(UserResource.class);
        System.out.println(om.writeValueAsString(userResourceHyperSchema));
    }

}
