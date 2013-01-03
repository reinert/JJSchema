package org.reinert.jsonschema.tests.model;

import java.util.Collection;
import java.util.Iterator;

public class Empresas implements Iterable<Empresa> {

	private Collection<Empresa> mEmpresas;
	
	public Empresas(Collection<Empresa> empresas) {
		mEmpresas = empresas;
	}

	public Iterator<Empresa> iterator() {
		return mEmpresas.iterator();
	}

}
