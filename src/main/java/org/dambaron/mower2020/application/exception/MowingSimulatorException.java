package org.dambaron.mower2020.application.exception;

public class MowingSimulatorException extends RuntimeException {
	public MowingSimulatorException(String message) {
		super(message);
	}

	public MowingSimulatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MowingSimulatorException(Throwable cause) {
		super(cause);
	}
}
