package com.github.reinert.jjschema.inheritance;

//TODO: Extend BaseItem to demonstrate the problem
public class WarrantyItem extends BaseItem {

	private String type;
	private long contractTermInMonths = 60;
	private boolean termsAndConditionsAccepted;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTermsAndConditionsAccepted() {
		return termsAndConditionsAccepted;
	}

	public void setTermsAndConditionsAccepted(boolean termsAndConditionsAccepted) {
		this.termsAndConditionsAccepted = termsAndConditionsAccepted;
	}

	public long getContractTermInMonths() {
		return contractTermInMonths;
	}

	public void setContractTermInMonths(long contractTermInMonths) {
		this.contractTermInMonths = contractTermInMonths;
	}

}