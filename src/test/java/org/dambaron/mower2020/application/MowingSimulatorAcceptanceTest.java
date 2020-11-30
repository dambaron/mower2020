package org.dambaron.mower2020.application;

import java.util.List;

import org.dambaron.mower2020.application.configuration.MowingSimulatorConfigurationParser;
import org.dambaron.mower2020.domain.CardinalOrientation;
import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.WayPoint;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MowingSimulatorAcceptanceTest {

	@Test
	void test_runSimulator() {
		// given
		var unparsedInputs = List.of(
			"5 5",
			"1 2 N",
			"LFLFLFLFF",
			"3 3 E",
			"FFRFFRFRRF"
		);

		var mowingSimulator = new MowingSimulator();

		// when
		var configuration = MowingSimulatorConfigurationParser.parseLines(unparsedInputs);

		var actualLawn = mowingSimulator.runSimulator(configuration);

		// then
		var actualMowersByPosition = actualLawn.mowersByPosition();

		var expectedPosition1 = new CartesianPosition(1, 3);
		var expectedPosition2 = new CartesianPosition(5, 1);

		assertThat(actualMowersByPosition).containsOnlyKeys(
			expectedPosition1,
			expectedPosition2
		);

		var actualMower1 = actualMowersByPosition.get(expectedPosition1);
		var actualMower2 = actualMowersByPosition.get(expectedPosition2);

		var expectedOriginalWayPoint1 = new WayPoint(new CartesianPosition(1, 2), CardinalOrientation.N);
		var expectedOriginalWayPoint2 = new WayPoint(new CartesianPosition(3, 3), CardinalOrientation.E);

		assertThat(actualMower1.originalWayPoint()).isEqualTo(expectedOriginalWayPoint1);
		assertThat(actualMower2.originalWayPoint()).isEqualTo(expectedOriginalWayPoint2);

		var actualJourney1 = actualMower1.journey();
		assertThat(actualJourney1).containsExactly(
			expectedOriginalWayPoint1,
			new WayPoint(new CartesianPosition(1, 2), CardinalOrientation.W),
			new WayPoint(new CartesianPosition(0, 2), CardinalOrientation.W),
			new WayPoint(new CartesianPosition(0, 2), CardinalOrientation.S),
			new WayPoint(new CartesianPosition(0, 1), CardinalOrientation.S),
			new WayPoint(new CartesianPosition(0, 1), CardinalOrientation.E),
			new WayPoint(new CartesianPosition(1, 1), CardinalOrientation.E),
			new WayPoint(new CartesianPosition(1, 1), CardinalOrientation.N),
			new WayPoint(new CartesianPosition(1, 2), CardinalOrientation.N),
			new WayPoint(new CartesianPosition(1, 3), CardinalOrientation.N)
		);

		var actualJourney2 = actualMower2.journey();
		assertThat(actualJourney2).containsExactly(
			expectedOriginalWayPoint2,
			new WayPoint(new CartesianPosition(4, 3), CardinalOrientation.E),
			new WayPoint(new CartesianPosition(5, 3), CardinalOrientation.E),
			new WayPoint(new CartesianPosition(5, 3), CardinalOrientation.S),
			new WayPoint(new CartesianPosition(5, 2), CardinalOrientation.S),
			new WayPoint(new CartesianPosition(5, 1), CardinalOrientation.S),
			new WayPoint(new CartesianPosition(5, 1), CardinalOrientation.W),
			new WayPoint(new CartesianPosition(4, 1), CardinalOrientation.W),
			new WayPoint(new CartesianPosition(4, 1), CardinalOrientation.N),
			new WayPoint(new CartesianPosition(4, 1), CardinalOrientation.E),
			new WayPoint(new CartesianPosition(5, 1), CardinalOrientation.E)
		);
	}
}
