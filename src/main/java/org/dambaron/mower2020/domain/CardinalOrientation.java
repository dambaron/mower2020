package org.dambaron.mower2020.domain;

public enum CardinalOrientation {

	N("N", "NORTH"),
	S("S", "SOUTH"),
	W("W", "WEST"),
	E("E", "EAST");

	private final String code;
	private final String label;

	CardinalOrientation(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "CardinalOrientation{" +
			"code='" + code + '\'' +
			", label='" + label + '\'' +
			'}';
	}

	public CardinalOrientation getLeftOrientation() {
		return switch (this) {
			case N -> W;
			case S -> E;
			case W -> S;
			case E -> N;
		};
	}

	public CardinalOrientation getRightOrientation() {
		return switch (this) {
			case N -> E;
			case S -> W;
			case W -> N;
			case E -> S;
		};
	}
}
