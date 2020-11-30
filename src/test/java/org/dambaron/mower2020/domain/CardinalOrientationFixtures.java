package org.dambaron.mower2020.domain;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class CardinalOrientationFixtures {

	static final class LeftOrientationsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
			return Stream.of(
				Arguments.of(CardinalOrientation.N, CardinalOrientation.W),
				Arguments.of(CardinalOrientation.W, CardinalOrientation.S),
				Arguments.of(CardinalOrientation.S, CardinalOrientation.E),
				Arguments.of(CardinalOrientation.E, CardinalOrientation.N)
			);
		}
	}

	static final class RightOrientationsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
			return Stream.of(
				Arguments.of(CardinalOrientation.N, CardinalOrientation.E),
				Arguments.of(CardinalOrientation.E, CardinalOrientation.S),
				Arguments.of(CardinalOrientation.S, CardinalOrientation.W),
				Arguments.of(CardinalOrientation.W, CardinalOrientation.N)
			);
		}
	}
}
