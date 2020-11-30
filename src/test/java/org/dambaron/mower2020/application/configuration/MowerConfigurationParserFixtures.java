package org.dambaron.mower2020.application.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.dambaron.mower2020.domain.CardinalOrientation;
import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.WayPoint;
import org.dambaron.mower2020.domain.mower.Mower;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class MowerConfigurationParserFixtures {

	static final class ParsableDefinitionsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			var parsableDefinitions = new ArrayList<Arguments>();
			// Cartesian product between X coordinates, Y coordinates and cardinal orientations
			var xCoordinates = List.of(1, 23, 456);
			var yCoordinates = List.of(987, 65, 4);

			var mowerDefinitionTemplate = "%s %s %s";
			for (var x : xCoordinates) {
				for (var y : yCoordinates) {
					for (var o : CardinalOrientation.values()) {
						var mowerDefinition = String.format(mowerDefinitionTemplate, x, y, o.getCode());
						var wayPoint = new WayPoint(new CartesianPosition(x, y), o);
						var expectedMower = new Mower(wayPoint);

						parsableDefinitions.add(Arguments.of(mowerDefinition, expectedMower));
					}
				}
			}

			return parsableDefinitions.stream();
		}
	}

	static final class UnparsableDefinitionsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			String nullMower = null;

			var blankMower1 = "";
			var blankMower2 = " ";
			var blankMower3 = "  ";

			var mowerWithoutXCoordinate = "  2 N";
			var mowerWithoutYCoordinate = "1  N";
			var mowerWithoutOrientation = "1 2";

			var mowerWithTwoCoordinates = "1 2 N 3 4 S";

			var mowerWithoutSpace = "12N";

			var mowerWithUnknownOrientation = "1 2 Z";
			var mowerWithNonNumericX = "A 2 N";
			var mowerWithNonNumericY = "1 A N";

			var mowerWithNegativeX = "-1 2 N";
			var mowerWithNegativeY = "1 -2 N";

			var mowerWithFractionalX = "1.23 2 N";
			var mowerWithFractionalY = "1 2.34 N";

			var mustNotBeBlankException =
				new IllegalArgumentException("Mower definition must not be blank");

			var mustContainThreeElementsException =
				new IllegalArgumentException("Mower definition must contain 3 elements exactly");

			var orientationNotFoundException =
				new IllegalArgumentException("No enum constant org.dambaron.mower2020.domain.CardinalOrientation.Z");

			var xIsNotNumericException =
				new IllegalArgumentException("X coordinate must be numeric");

			var yIsNotNumericException =
				new IllegalArgumentException("Y coordinate must be numeric");

			return Stream.of(
				Arguments.of(nullMower, mustNotBeBlankException),
				Arguments.of(blankMower1, mustNotBeBlankException),
				Arguments.of(blankMower2, mustNotBeBlankException),
				Arguments.of(blankMower3, mustNotBeBlankException),
				Arguments.of(mowerWithTwoCoordinates, mustContainThreeElementsException),
				Arguments.of(mowerWithoutXCoordinate, mustContainThreeElementsException),
				Arguments.of(mowerWithoutYCoordinate, mustContainThreeElementsException),
				Arguments.of(mowerWithoutOrientation, mustContainThreeElementsException),
				Arguments.of(mowerWithoutSpace, mustContainThreeElementsException),
				Arguments.of(mowerWithUnknownOrientation, orientationNotFoundException),
				Arguments.of(mowerWithNonNumericX, xIsNotNumericException),
				Arguments.of(mowerWithNonNumericY, yIsNotNumericException),
				Arguments.of(mowerWithNegativeX, xIsNotNumericException),
				Arguments.of(mowerWithNegativeY, yIsNotNumericException),
				Arguments.of(mowerWithNegativeX, xIsNotNumericException),
				Arguments.of(mowerWithNegativeY, yIsNotNumericException),
				Arguments.of(mowerWithFractionalX, xIsNotNumericException),
				Arguments.of(mowerWithFractionalY, yIsNotNumericException)
			);
		}
	}
}
