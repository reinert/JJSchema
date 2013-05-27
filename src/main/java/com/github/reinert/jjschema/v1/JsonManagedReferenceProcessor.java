package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.AccessibleObject;

/**
 * @author Danilo Reinert
 */

public class JsonManagedReferenceProcessor implements AnnotationProcessor {

    public boolean processAnnotation(ObjectNode schema, AccessibleObject accessibleObject, SchemaContext schemaContext) {
        JsonManagedReference refAnn = accessibleObject.getAnnotation(JsonManagedReference.class);

        if (refAnn == null)
            return false;


//        Method propertyMethod = schemaContext.getPropertyMethod(name);
//        Class<?> returnType = propertyMethod.getReturnType();
//        ManagedReference fowardReference = null;
//        Class<?> genericClass = null;
//        if (Iterable.class.isAssignableFrom(returnType)) {
//            ParameterizedType genericType = (ParameterizedType) propertyMethod
//                    .getGenericReturnType();
//            genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
//        } else {
//            genericClass = returnType;
//        }
//        fowardReference = new ManagedReference(type, refAnn.value(), genericClass);
//
//        if (!isFowardReferencePiled(fowardReference)) {
//            pushFowardReference(fowardReference);
//        } else
////        	if (isBackwardReferencePiled(fowardReference))
//        {
//            boolean a = pullFowardReference(fowardReference);
//            boolean b = pullBackwardReference(fowardReference);
//            //return null;
//            return createRefSchema("#");
//        }

        return true;
    }
}
