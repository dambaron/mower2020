package org.dambaron.mower2020.domain;

import java.util.stream.Stream;

import org.dambaron.mower2020.domain.command.Command;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

class WayPointFixtures {

	private static final CartesianPosition ZERO_ONE = new CartesianPosition(0, 1);

	private static final CartesianPosition ONE_ZERO = new CartesianPosition(1, 0);
	private static final CartesianPosition ONE_ONE = new CartesianPosition(1, 1);
	private static final CartesianPosition ONE_TWO = new CartesianPosition(1, 2);

	private static final CartesianPosition TWO_ONE = new CartesianPosition(2, 1);

	private static final WayPoint ONE_ONE_NORTH = new WayPoint(ONE_ONE, CardinalOrientation.N);
	private static final WayPoint ONE_ONE_SOUTH = new WayPoint(ONE_ONE, CardinalOrientation.S);
	private static final WayPoint ONE_ONE_EAST = new WayPoint(ONE_ONE, CardinalOrientation.E);
	private static final WayPoint ONE_ONE_WEST = new WayPoint(ONE_ONE, CardinalOrientation.W);

	static final class RotationsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
			return Stream.of(
				Arguments.of(ONE_ONE_NORTH, Command.L, ONE_ONE_WEST),
				Arguments.of(ONE_ONE_WEST, Command.L, ONE_ONE_SOUTH),
				Arguments.of(ONE_ONE_SOUTH, Command.L, ONE_ONE_EAST),
				Arguments.of(ONE_ONE_EAST, Command.L, ONE_ONE_NORTH),
				Arguments.of(ONE_ONE_NORTH, Command.R, ONE_ONE_EAST),
				Arguments.of(ONE_ONE_EAST, Command.R, ONE_ONE_SOUTH),
				Arguments.of(ONE_ONE_SOUTH, Command.R, ONE_ONE_WEST),
				Arguments.of(ONE_ONE_WEST, Command.R, ONE_ONE_NORTH)
			);
		}
	}

	static final class TranslationsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			var shouldMoveToOneTwoNorth = Arguments.of(ONE_ONE_NORTH,
				Command.F,
				new WayPoint(new CartesianPosition(1, 2), CardinalOrientation.N));

			var shouldMoveToOneZeroNorth = Arguments.of(ONE_ONE_NORTH,
				Command.B,
				new WayPoint(ONE_ZERO, CardinalOrientation.N));

			var shouldMoveToOneZeroSouth = Arguments.of(ONE_ONE_SOUTH,
				Command.F,
				new WayPoint(ONE_ZERO, CardinalOrientation.S));

			var shouldMoveToOneTwoSouth = Arguments.of(ONE_ONE_SOUTH,
				Command.B,
				new WayPoint(ONE_TWO, CardinalOrientation.S));

			var shouldMoveToZeroOneWest = Arguments.of(ONE_ONE_WEST,
				Command.F,
				new WayPoint(ZERO_ONE, CardinalOrientation.W));

			var shouldMoveToTwoOneWest = Arguments.of(ONE_ONE_WEST,
				Command.B,
				new WayPoint(TWO_ONE, CardinalOrientation.W));

			var shouldMoveToTwoOneEast = Arguments.of(ONE_ONE_EAST,
				Command.F,
				new WayPoint(TWO_ONE, CardinalOrientation.E));

			var shouldMoveToZeroOneEast = Arguments.of(ONE_ONE_EAST,
				Command.B,
				new WayPoint(ZERO_ONE, CardinalOrientation.E));

			return Stream.of(
				shouldMoveToOneTwoNorth,
				shouldMoveToOneZeroNorth,
				shouldMoveToOneZeroSouth,
				shouldMoveToOneTwoSouth,
				shouldMoveToZeroOneWest,
				shouldMoveToTwoOneWest,
				shouldMoveToTwoOneEast,
				shouldMoveToZeroOneEast
			);
		}
	}
}
