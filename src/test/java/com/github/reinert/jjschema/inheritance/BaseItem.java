package com.github.reinert.jjschema.inheritance;

import java.util.Set;

public abstract class BaseItem {

	private long id;
	private Set<WarrantyItem> availableWarrantyItems;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Set<WarrantyItem> getAvailableWarrantyItems() {
		return availableWarrantyItems;
	}
	
	public void setAvailableWarrantyItems(Set<WarrantyItem> availableWarrantyItems) {
		this.availableWarrantyItems = availableWarrantyItems;
	}
	
}