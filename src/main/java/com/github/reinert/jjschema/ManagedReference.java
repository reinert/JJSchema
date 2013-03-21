package com.github.reinert.jjschema;

class ManagedReference {

	final Class<?> managedType;
	//Class<?> backType;
	final String name;
	//boolean processed;

	public ManagedReference(Class<?> managedType, String name) {
		this.managedType = managedType;
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((managedType == null) ? 0 : managedType.getName().hashCode());
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
		if (managedType == null) {
			if (other.managedType != null)
				return false;
		} else if (!managedType.getName().equals(other.managedType.getName()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
