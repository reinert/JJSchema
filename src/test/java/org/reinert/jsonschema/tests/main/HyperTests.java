package org.reinert.jsonschema.tests.main;

import java.lang.annotation.Retention;

import javax.ws.rs.Path;

import org.reinert.jsonschema.HyperSchema;
import org.reinert.jsonschema.LinkProperty;
import org.reinert.jsonschema.tests.resource.EmpresaResource;

public class HyperTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Class<EmpresaResource> type = EmpresaResource.class;
		System.out.println(type);
		System.out.println(type.isAnonymousClass());
		System.out.println(type.isAnnotationPresent(Path.class));
		
		System.out.println(LinkProperty.class.isAnnotation());
		System.out.println(LinkProperty.class.isAnnotationPresent(Retention.class));
	}

}
