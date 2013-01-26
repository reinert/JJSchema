package com.github.reinert.jjschema.model;

import java.util.Collection;
import java.util.Iterator;

public class Users implements Iterable<User> {

	private Collection<User> mUsers;
	
	public Users(Collection<User> empresas) {
		mUsers = empresas;
	}

	public Iterator<User> iterator() {
		return mUsers.iterator();
	}

}
