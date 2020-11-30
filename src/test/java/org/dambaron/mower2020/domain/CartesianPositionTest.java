package org.dambaron.mower2020.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CartesianPositionTest {

	@Test
	void test_constructor_should_throw_exception_when_x_is_negative() {
		// given
		var negativeX = -1;
		var positiveY = 1;

		// when
		var thrown = catchThrowable(() -> new CartesianPosition(negativeX, positiveY));

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("x must be greater or equal to zero");
	}

	@Test
	void test_constructor_should_throw_exception_when_y_is_negative() {
		// given
		var positiveX = 1;
		var negativeY = -1;

		// when
		var thrown = catchThrowable(() -> new CartesianPosition(positiveX, negativeY));

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("y must be greater or equal to zero");
	}
}
