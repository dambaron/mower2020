package org.dambaron.mower2020.application.configuration;

import org.dambaron.mower2020.domain.mower.Mower;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MowerConfigurationParserTest {

	@ParameterizedTest
	@ArgumentsSource(MowerConfigurationParserFixtures.ParsableDefinitionsProvider.class)
	void test_parse(String mowerDefinition, Mower expectedMower) {
		// given

		// when
		var actualMower = MowerConfigurationParser.parse(mowerDefinition);

		// then
		assertThat(actualMower)
			.usingRecursiveComparison()
			.ignoringFields("uuid")
			.isEqualTo(expectedMower);
	}

	@ParameterizedTest
	@ArgumentsSource(MowerConfigurationParserFixtures.UnparsableDefinitionsProvider.class)
	void test_parse_should_throw_exception(String mowerDefinition, IllegalArgumentException expectedException) {
		// given

		// when
		var thrown = catchThrowable(() -> MowerConfigurationParser.parse(mowerDefinition));

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(expectedException.getMessage());
	}
}
