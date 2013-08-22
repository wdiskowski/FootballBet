package org.pt.bet.exception;

public class ScoreValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9153141062975511715L;

	public ScoreValidationException() {
		super();
	}

	public ScoreValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScoreValidationException(String message) {
		super(message);
	}

	public ScoreValidationException(Throwable cause) {
		super(cause);
	}


}
