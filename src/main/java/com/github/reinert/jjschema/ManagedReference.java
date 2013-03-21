package com.github.reinert.jjschema;

class ManagedReference {

	final Class<?> type;
	//Class<?> backType;
	final String name;
	//boolean processed;

	public ManagedReference(Class<?> type, String name) {
		this.type = type;
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((type == null) ? 0 : type.getName().hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagedReference other = (ManagedReference) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.getName().equals(other.type.getName()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
