package org.pt.bet.exception;

public class CountryNotFoundException extends DataTransferException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountryNotFoundException(String country) {
		super("Country not found: " + country);
	}

}
