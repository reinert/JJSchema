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

package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 27/05/13
 * Time: 14:46
 *
 * @author Danilo Reinert
 */

public class JsonSchemaV4Factory extends JsonSchemaFactory {

    @Override
    public JsonNode createSchema(Class<?> type) {
        SchemaWrapper schemaWrapper = SchemaWrapperFactory.createWrapper(type);
        if (isAutoPutDollarSchema())
            schemaWrapper.putDollarSchema();
        return schemaWrapper.asJson();
    }
}
