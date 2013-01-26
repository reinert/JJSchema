package com.github.reinert.jjschema.exception;

public class UnavailableVersion extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7146537640173260075L;

	public UnavailableVersion() {
    }

    public UnavailableVersion(String msg) {
        super(msg);
    }
}
