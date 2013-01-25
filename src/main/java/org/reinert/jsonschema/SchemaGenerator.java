package org.reinert.jsonschema;

import org.reinert.jsonschema.impl.SchemaGeneratorDraft4;
import org.reinert.jsonschema.exception.UnavailableVersion;

public abstract class  SchemaGenerator {
    
    public static final String DEFAULT_VERSION = "draft-4";
    
    public static SchemaGenerator getInstance() throws UnavailableVersion{
        return SchemaGenerator.getInstance(SchemaGenerator.DEFAULT_VERSION);
    }
    public static SchemaGenerator getInstance(String draftVersion)
        throws UnavailableVersion {
        if (draftVersion.equals("draft-4"))
            return new SchemaGeneratorDraft4();   
        
        throw new UnavailableVersion("versão não encontrada");
    }
    
    public abstract <T> JsonSchema from(Class<T> type);
}
