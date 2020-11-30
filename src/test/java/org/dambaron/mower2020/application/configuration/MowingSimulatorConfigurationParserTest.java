package org.dambaron.mower2020.application.configuration;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MowingSimulatorConfigurationParserTest {

	@ParameterizedTest
	@ArgumentsSource(MowingSimulatorConfigurationParserFixtures.ParsableConfigurationProvider.class)
	void test_parse_lines(List<String> lines, MowingSimulatorConfiguration expectedConfiguration) {
		// given

		// when
		var actualConfiguration = MowingSimulatorConfigurationParser.parseLines(lines);

		// then
		assertThat(actualConfiguration)
			.usingRecursiveComparison()
			.ignoringFields("mowerAndCommands._1.uuid", "optionalMowersAndCommands._1.uuid")
			.isEqualTo(expectedConfiguration);
	}

	@Test
	void test_parse_file_should_throw_exception_when_configuration_is_null() {
		// given

		// when
		var thrown = catchThrowable(() -> MowingSimulatorConfigurationParser.parseFile(null));

		// then
		assertThat(thrown)
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Error parsing configuration: configurationFile must not be null");
	}

	@Test
	void test_parse_lines_should_throw_exception_when_configuration_is_null() {
		// given

		// when
		var thrown = catchThrowable(() -> {
			MowingSimulatorConfigurationParser.parseLines(null);
		});

		// then
		assertThat(thrown)
			.isInstanceOf(NullPointerException.class)
			.hasMessage("Error parsing configuration: lines must not be null");
	}

	@Test
	void test_parse_lines_should_throw_exception_when_configuration_has_strictly_less_than_three_lines() {
		// given
		var configurations = List.of(
			Collections.<String>emptyList(),
			List.of(""),
			List.of("", "")
		);

		// when
		for (var configuration : configurations) {
			var thrown = catchThrowable(() -> MowingSimulatorConfigurationParser.parseLines(configuration));

			// then
			assertThat(thrown)
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Error parsing configuration: lines should contain 3 elements at least");
		}
	}
}
