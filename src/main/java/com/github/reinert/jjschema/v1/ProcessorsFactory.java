package com.github.reinert.jjschema.v1;

/**
 * @author Danilo Reinert
 */

public class ProcessorsFactory {

    AttributesProcessor attributesProcessor = new AttributesProcessor();
    NullableProcessor nullableProcessor = new NullableProcessor();
    SchemaIgnoreProcessor schemaIgnoreProcessor = new SchemaIgnoreProcessor();

}
