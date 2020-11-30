package org.dambaron.mower2020.domain.lawn;

import java.util.stream.Stream;

import org.dambaron.mower2020.domain.CartesianPosition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class LawnFixtures {

	private static final CartesianPosition ONE_ONE_POSITION = new CartesianPosition(1, 1);
	private static final Lawn ONE_BY_ONE_LAWN = new Lawn(ONE_ONE_POSITION);

	static final class PositionInsideFieldProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
			return Stream.of(
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(0, 0)),
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(0, 1)),
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(1, 1)),
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(1, 0))
			);
		}
	}

	static final class PositionOutsideFieldProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			return Stream.of(
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(0, 2)),
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(1, 2)),
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(2, 2)),
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(2, 1)),
				Arguments.of(ONE_BY_ONE_LAWN, new CartesianPosition(2, 0))
			);
		}
	}
}
