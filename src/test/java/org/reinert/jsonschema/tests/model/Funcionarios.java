package org.reinert.jsonschema.tests.model;

import java.util.Collection;
import java.util.Iterator;

public class Funcionarios implements Iterable<Funcionario> {

	private Collection<Funcionario> mFuncionarios;
	
	public Funcionarios(Collection<Funcionario> funcionarios) {
		mFuncionarios = funcionarios;
	}

	public Iterator<Funcionario> iterator() {
		return mFuncionarios.iterator();
	}

}
