package org.dambaron.mower2020.domain;

import org.dambaron.mower2020.domain.command.Command;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;

class WayPointTest {

	@ParameterizedTest
	@ArgumentsSource(WayPointFixtures.RotationsProvider.class)
	void test_rotate(WayPoint originWayPoint, Command rotation, WayPoint expectedWayPoint) {
		// given

		// when
		var actualWayPoint = originWayPoint.handle(rotation);

		// then
		assertThat(actualWayPoint).isEqualTo(expectedWayPoint);
	}

	@ParameterizedTest
	@ArgumentsSource(WayPointFixtures.RotationsProvider.class)
	void test_rotate_should_have_no_side_effect(WayPoint originWayPoint,
												Command rotation,
												WayPoint expectedWayPoint) {
		// given
		var wayPointBeforeRotation = new WayPoint(originWayPoint.position(), originWayPoint.orientation());

		// when
		originWayPoint.handle(rotation);

		// then
		assertThat(wayPointBeforeRotation).isEqualTo(originWayPoint);
	}

	@ParameterizedTest
	@ArgumentsSource(WayPointFixtures.TranslationsProvider.class)
	void test_translate(WayPoint originWayPoint, Command translation, WayPoint expectedWayPoint) {
		// given

		// when
		var actualWayPoint = originWayPoint.handle(translation);

		// then
		assertThat(actualWayPoint).isEqualTo(expectedWayPoint);
	}

	@ParameterizedTest
	@ArgumentsSource(WayPointFixtures.TranslationsProvider.class)
	void test_translate_should_have_no_side_effect(WayPoint originWayPoint,
												   Command rotation,
												   WayPoint expectedWayPoint) {
		// given
		var wayPointBeforeTranslation = new WayPoint(originWayPoint.position(), originWayPoint.orientation());

		// when
		originWayPoint.handle(rotation);

		// then
		assertThat(wayPointBeforeTranslation).isEqualTo(originWayPoint);
	}
}
