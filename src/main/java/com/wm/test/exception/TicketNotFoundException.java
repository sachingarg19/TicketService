package com.wm.test.exception;

// TODO: Auto-generated Javadoc
/**
 * Thrown when performing an operatoin on a ticket that does not exist.
 * 
 * @author Sachin Garg
 */
public class TicketNotFoundException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new ticket not found exception.
	 */
	public TicketNotFoundException() {
		super();
	}

	/**
	 * Instantiates a new ticket not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public TicketNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new ticket not found exception.
	 *
	 * @param message the message
	 */
	public TicketNotFoundException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new ticket not found exception.
	 *
	 * @param cause the cause
	 */
	public TicketNotFoundException(Throwable cause) {
		super(cause);
	}

}
