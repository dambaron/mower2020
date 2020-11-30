package org.dambaron.mower2020.domain.exception;

public class MowingException extends RuntimeException {
	public MowingException(String message) {
		super(message);
	}

	public MowingException(String message, Throwable cause) {
		super(message, cause);
	}
}
