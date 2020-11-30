package org.dambaron.mower2020.domain.command;

public enum Command {
	L("L", "LEFT_ROTATION"),
	R("R", "RIGHT_ROTATION"),
	F("F", "FORWARD_TRANSLATION"),
	B("B", "BACKWARD_TRANSLATION");

	private final String code;
	private final String label;

	Command(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
}
