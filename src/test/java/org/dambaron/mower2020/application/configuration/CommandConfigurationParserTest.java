package org.dambaron.mower2020.application.configuration;

import java.util.List;

import org.dambaron.mower2020.domain.command.Command;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CommandConfigurationParserTest {

	@ParameterizedTest
	@ArgumentsSource(CommandConfigurationParserFixtures.ParsableDefinitionsProvider.class)
	void test_parse(String moveCommandsDefinition, List<Command> expectedCommands) {
		// given

		// when
		var actualCommands = CommandConfigurationParser.parse(moveCommandsDefinition);

		// then
		assertThat(actualCommands)
			.hasSameSizeAs(expectedCommands)
			.containsAll(expectedCommands);
	}

	@ParameterizedTest
	@ArgumentsSource(CommandConfigurationParserFixtures.UnparsableDefinitionsProvider.class)
	void test_parse_should_throw_exception(String moveCommandsDefinition,
										   IllegalArgumentException expectedException) {
		// given

		// when
		var thrown = catchThrowable(() -> CommandConfigurationParser.parse(moveCommandsDefinition));

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(expectedException.getMessage());
	}
}
