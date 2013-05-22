package com.github.reinert.jjschema.v1;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 22/05/13
 * Time: 15:31
 *
 * @author Danilo Reinert
 */

public class ProcessorsFactory {

    AttributesProcessor attributesProcessor = new AttributesProcessor();
    NullableProcessor nullableProcessor = new NullableProcessor();
    SchemaIgnoreProcessor schemaIgnoreProcessor = new SchemaIgnoreProcessor();

}
