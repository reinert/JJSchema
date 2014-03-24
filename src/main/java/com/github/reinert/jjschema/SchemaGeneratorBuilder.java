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


/**
 * A SchemaGenerator builder for creating SchemaGenerators considering some options.
 *
 * @author reinert
 */
public class SchemaGeneratorBuilder {

    public static ConfigurationStep draftV4Schema() {
        return new ConfigurationStep(new JsonSchemaGeneratorV4());
    }

    public static ConfigurationStep draftV4HyperSchema() {
        return new ConfigurationStep(new HyperSchemaGeneratorV4(new JsonSchemaGeneratorV4()));
    }

    static public class ConfigurationStep {
        final JsonSchemaGenerator generator;

        ConfigurationStep(JsonSchemaGenerator generator) {
            this.generator = generator;
        }

        public ConfigurationStep setAutoPutSchemaVersion(boolean autoPutVersion) {
            generator.autoPutVersion = autoPutVersion;
            return this;
        }

        public final JsonSchemaGenerator build() {
            return generator;
        }
    }
}
