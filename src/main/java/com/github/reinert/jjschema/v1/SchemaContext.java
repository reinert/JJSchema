package com.github.reinert.jjschema.v1;

import com.github.reinert.jjschema.ManagedReference;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 22/05/13
 * Time: 10:33
 *
 * @author Danilo Reinert
 */

public interface SchemaContext {

    boolean getAutoPutVersion();

    void pushForwardReference(ManagedReference forwardReference);

    boolean isForwardReferencePiled(ManagedReference forwardReference);

    boolean pullForwardReference(ManagedReference forwardReference);

    void pushBackwardReference(ManagedReference backReference);

    boolean isBackwardReferencePiled(ManagedReference backReference);

    boolean pullBackwardReference(ManagedReference backReference);

    Map<String, Method> getPropertiesMethodMap();

    Method getPropertyMethod(String property);
}
