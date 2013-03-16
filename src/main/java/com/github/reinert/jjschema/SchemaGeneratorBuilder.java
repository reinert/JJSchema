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

package com.github.reinert.jjschema;


/**
 * A SchemaGenerator builder for creating SchemaGenerators considering some options.
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
