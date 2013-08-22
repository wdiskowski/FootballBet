package org.pt.bet.exception;

public class TournamentNotFoundException extends DataTransferException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TournamentNotFoundException(String tournament) {
		super("Tournament not found: " + tournament);
	}

}
