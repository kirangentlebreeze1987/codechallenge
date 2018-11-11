package com.mobiquityinc.packer.exception;

/**
 * The Class ApiException.
 * All occuring Throwables are wrapped with this exception
 */
public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 3936100005856797335L;

	public ApiException() {
		super();
	}

	public ApiException(String errorMessage) {
		super(errorMessage);
	}

	public ApiException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

	public ApiException(String message, Throwable cause, Object... messageArgs) {

		super(String.format(message, messageArgs), cause);
	}
}
