package org.dambaron.mower2020.domain;

public record CartesianPosition(int x, int y) {

	public static final CartesianPosition ZERO_ZERO_POSITION = new CartesianPosition(0, 0);

	public CartesianPosition {
		if (x < 0) {
			throw new IllegalArgumentException("x must be greater or equal to zero");
		}

		if (y < 0) {
			throw new IllegalArgumentException("y must be greater or equal to zero");
		}
	}
}
