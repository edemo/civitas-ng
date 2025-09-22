package civitas.bboard.server.controllers;

public class CommunicableException extends Exception {

	private static final long serialVersionUID = 1L;

	public CommunicableException(final String msg) {
		super(msg);
	}
}
