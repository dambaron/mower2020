package org.dambaron.mower2020.application.configuration;

import org.dambaron.mower2020.domain.lawn.Lawn;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LawnConfigurationParserTest {

	@ParameterizedTest
	@ArgumentsSource(LawnConfigurationParserFixtures.ParsableDefinitionsProvider.class)
	void test_parse(String lawnDefinition, Lawn expectedLawn) {
		// given

		// when
		var actualLawn = LawnConfigurationParser.parse(lawnDefinition);

		// then
		assertThat(actualLawn).isEqualTo(expectedLawn);
	}

	@ParameterizedTest
	@ArgumentsSource(LawnConfigurationParserFixtures.UnparsableDefinitionsProvider.class)
	void test_parse_should_throw_exception(String mowerDefinition, IllegalArgumentException expectedException) {
		// given

		// when
		var thrown = catchThrowable(() -> LawnConfigurationParser.parse(mowerDefinition));

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(expectedException.getMessage());
	}
}
