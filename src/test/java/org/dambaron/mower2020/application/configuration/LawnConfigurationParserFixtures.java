package org.dambaron.mower2020.application.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.lawn.Lawn;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

class LawnConfigurationParserFixtures {

	static final class ParsableDefinitionsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			var parsableDefinitions = new ArrayList<Arguments>();
			// Cartesian product between X coordinates, Y coordinates
			var xCoordinates = List.of(1, 23, 456);
			var yCoordinates = List.of(987, 65, 4);

			var lawnDefinitionTemplate = "%s %s";
			for (var x : xCoordinates) {
				for (var y : yCoordinates) {

					var lawnDefinition = String.format(lawnDefinitionTemplate, x, y);
					var upperRightCorner = new CartesianPosition(x, y);
					var expectedLawn = new Lawn(upperRightCorner);

					parsableDefinitions.add(Arguments.of(lawnDefinition, expectedLawn));
				}
			}

			return parsableDefinitions.stream();
		}
	}

	static final class UnparsableDefinitionsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			String nullLawn = null;

			var blankLawn1 = "";
			var blankLawn2 = " ";
			var blankLawn3 = "  ";

			var lawnWithoutXCoordinate = "  2";
			var lawnWithoutYCoordinate = "1  ";

			var lawnWithTwoCoordinates = "1 2 3 4";

			var lawnWithoutSpace = "12";

			var lawnWithNonNumericX = "A 2";
			var lawnWithNonNumericY = "1 A";

			var lawnWithNegativeX = "-1 2";
			var lawnWithNegativeY = "1 -2";

			var lawnWithFractionalX = "1.23 2";
			var lawnWithFractionalY = "1 2.34";

			var mustNotBeBlankException =
				new IllegalArgumentException("Upper right corner definition must not be blank");

			var mustContainTwoElementsException =
				new IllegalArgumentException("Lawn definition must contain 2 elements exactly");

			var xIsNotNumericException =
				new IllegalArgumentException("X coordinate must be numeric");

			var yIsNotNumericException =
				new IllegalArgumentException("Y coordinate must be numeric");

			return Stream.of(
				Arguments.of(nullLawn, mustNotBeBlankException),
				Arguments.of(blankLawn1, mustNotBeBlankException),
				Arguments.of(blankLawn2, mustNotBeBlankException),
				Arguments.of(blankLawn3, mustNotBeBlankException),
				Arguments.of(lawnWithTwoCoordinates, mustContainTwoElementsException),
				Arguments.of(lawnWithoutXCoordinate, mustContainTwoElementsException),
				Arguments.of(lawnWithoutYCoordinate, mustContainTwoElementsException),
				Arguments.of(lawnWithoutSpace, mustContainTwoElementsException),
				Arguments.of(lawnWithNonNumericX, xIsNotNumericException),
				Arguments.of(lawnWithNonNumericY, yIsNotNumericException),
				Arguments.of(lawnWithNegativeX, xIsNotNumericException),
				Arguments.of(lawnWithNegativeY, yIsNotNumericException),
				Arguments.of(lawnWithNegativeX, xIsNotNumericException),
				Arguments.of(lawnWithNegativeY, yIsNotNumericException),
				Arguments.of(lawnWithFractionalX, xIsNotNumericException),
				Arguments.of(lawnWithFractionalY, yIsNotNumericException)
			);
		}
	}
}
