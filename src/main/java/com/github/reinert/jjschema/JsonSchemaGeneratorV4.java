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

import static com.github.reinert.jjschema.JJSchemaUtil.processCommonAttributes;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.v1.SchemaWrapper;


/**
 * Implements the JSON Schema generation according to draft v4
 *
 * @author reinert
 */
@Deprecated
public class JsonSchemaGeneratorV4 extends JsonSchemaGenerator {

    @Override
    protected void processSchemaProperty(ObjectNode schema, Attributes attributes) {
        if (!attributes.$ref().isEmpty()) {
            schema.put("$ref", attributes.$ref());
        }
        if (autoPutVersion) {
            schema.put("$schema", SchemaWrapper.DRAFT_04);
        }
        processCommonAttributes(schema, attributes);

        if (attributes.required()) {
            schema.put("selfRequired", true);
        }
    }

}
