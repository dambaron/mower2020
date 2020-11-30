package org.dambaron.mower2020.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;

class CardinalOrientationTest {

	@ParameterizedTest
	@ArgumentsSource(CardinalOrientationFixtures.LeftOrientationsProvider.class)
	void test_getLeftOrientation(CardinalOrientation currentOrientation, CardinalOrientation expectedOrientation) {
		// given

		// when
		var actualOrientation = currentOrientation.getLeftOrientation();

		// then
		assertThat(actualOrientation).isEqualTo(expectedOrientation);
	}

	@ParameterizedTest
	@ArgumentsSource(CardinalOrientationFixtures.RightOrientationsProvider.class)
	void test_getRightOrientation(CardinalOrientation currentOrientation, CardinalOrientation expectedOrientation) {
		// given

		// when
		var actualOrientation = currentOrientation.getRightOrientation();

		// then
		assertThat(actualOrientation).isEqualTo(expectedOrientation);
	}
}
