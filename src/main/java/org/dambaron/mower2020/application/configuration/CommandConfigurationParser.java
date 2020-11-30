package org.dambaron.mower2020.application.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.dambaron.mower2020.domain.command.Command;

public final class CommandConfigurationParser {

	public static List<Command> parse(String commandsDefinition) {
		boolean isCommandsDefinitionBlank = StringUtils.isBlank(commandsDefinition);
		if (isCommandsDefinitionBlank) {
			throw new IllegalArgumentException("Commands definition must not be blank");
		}

		var commandCodes = Arrays.stream(commandsDefinition.split("")).collect(Collectors.toUnmodifiableList());
		List<Command> commands = new ArrayList<>();
		for (var commandCode : commandCodes) {

			var command = Try.of(() -> Command.valueOf(commandCode));
			if (command.isFailure()) {
				throw new IllegalArgumentException("Unable to create command. Unknown code=" + commandCode);
			}

			commands.add(command.get());
		}
		return commands;
	}
}
