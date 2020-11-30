package org.dambaron.mower2020.application.configuration;

import java.util.List;
import java.util.stream.Stream;

import org.dambaron.mower2020.domain.command.Command;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class CommandConfigurationParserFixtures {

	static final class ParsableDefinitionsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			var commandsDefinition1 = "LFLFLFLFF";
			var expectedCommands1 = List.of(
				Command.L,
				Command.F,
				Command.L,
				Command.F,
				Command.L,
				Command.F,
				Command.L,
				Command.F,
				Command.F
			);

			var commandsDefinition2 = "FFRFFRFRRF";
			var expectedCommands2 = List.of(
				Command.F,
				Command.F,
				Command.R,
				Command.F,
				Command.F,
				Command.R,
				Command.F,
				Command.R,
				Command.R,
				Command.F
			);

			return Stream.of(
				Arguments.of(commandsDefinition1, expectedCommands1),
				Arguments.of(commandsDefinition2, expectedCommands2)
			);
		}
	}

	static final class UnparsableDefinitionsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			String nullCommands = null;

			var blankCommands1 = "";
			var blankCommands2 = " ";
			var blankCommands3 = "  ";

			var commandsWithUnknownCode1 = "X";
			var commandsWithUnknownCode2 = "LY";
			var commandsWithUnknownCode3 = "LFZ";

			return Stream.of(Arguments.of(nullCommands, new IllegalArgumentException("Commands definition must not be blank")),
				Arguments.of(blankCommands1, new IllegalArgumentException("Commands definition must not be blank")),
				Arguments.of(blankCommands2, new IllegalArgumentException("Commands definition must not be blank")),
				Arguments.of(blankCommands3, new IllegalArgumentException("Commands definition must not be blank")),
				Arguments.of(commandsWithUnknownCode1, new IllegalArgumentException("Unable to create command. Unknown code=X")),
				Arguments.of(commandsWithUnknownCode2, new IllegalArgumentException("Unable to create command. Unknown code=Y")),
				Arguments.of(commandsWithUnknownCode3, new IllegalArgumentException("Unable to create command. Unknown code=Z"))
			);
		}
	}
}
