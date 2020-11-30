package org.dambaron.mower2020.application.configuration;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.vavr.Tuple2;
import io.vavr.control.Try;

public final class MowingSimulatorConfigurationParser {

	public static MowingSimulatorConfiguration parseFile(File configurationFile) {

		Objects.requireNonNull(configurationFile,
			"Error parsing configuration: configurationFile must not be null");

		var configurationFilePath = configurationFile.toPath();
		var readLines = Try.of(() -> Files.readAllLines(configurationFilePath));
		if (readLines.isFailure()) {
			throw new RuntimeException(readLines.getCause());
		}

		return parseLines(readLines.get());
	}

	public static MowingSimulatorConfiguration parseLines(List<String> lines) {

		Objects.requireNonNull(lines, "Error parsing configuration: lines must not be null");

		if (lines.size() < 3) {
			throw new IllegalArgumentException("Error parsing configuration: lines should contain 3 elements at least");
		}

		var lawnDefinition = lines.get(0);
		var mowerAndCommandsDefinition = new Tuple2<>(lines.get(1), lines.get(2));
		var optionalMowersAndCommandsDefinitions = new ArrayList<Tuple2<String, String>>();

		if (lines.size() > 3) {
			var optionalLines = lines.subList(3, lines.size());
			if (optionalLines.size() < 2) {
				throw new IllegalArgumentException("Error parsing configuration: missing mowers or moves definitions");
			}

			for (var i = 0; i < optionalLines.size() - 1; i = i + 2) {
				var optionalMowerDefinition = optionalLines.get(i);
				var optionalCommandsDefinition = optionalLines.get(i + 1);

				var optionalMowerAndCommandsDefinition =
					new Tuple2<>(optionalMowerDefinition, optionalCommandsDefinition);

				optionalMowersAndCommandsDefinitions.add(optionalMowerAndCommandsDefinition);
			}
		}

		return parseItems(lawnDefinition, mowerAndCommandsDefinition, optionalMowersAndCommandsDefinitions);
	}

	public static MowingSimulatorConfiguration parseItems(String lawnDefinition,
														  Tuple2<String, String> mowerAndCommandsDefinition,
														  List<Tuple2<String, String>> optionalMowerAndCommandsDefinitions) {

		Objects.requireNonNull(lawnDefinition,
			"Error parsing configuration. Lawn definition must not be null");

		Objects.requireNonNull(mowerAndCommandsDefinition,
			"Error parsing configuration. Mower and moves definition must not be null");

		var lawn = LawnConfigurationParser.parse(lawnDefinition);
		var mower = MowerConfigurationParser.parse(mowerAndCommandsDefinition._1());
		var commands = CommandConfigurationParser.parse(mowerAndCommandsDefinition._2());

		var mowerAndCommands = new Tuple2<>(mower, commands);

		if (optionalMowerAndCommandsDefinitions == null) {
			return new MowingSimulatorConfiguration(lawn, mowerAndCommands, Collections.emptyList());
		}

		var optionalMowersAndCommands = optionalMowerAndCommandsDefinitions.stream()
			.map(optionalMowerAndCommandsDefinition -> {
				var optionalMowerDefinition = optionalMowerAndCommandsDefinition._1();
				var optionalCommandsDefinition = optionalMowerAndCommandsDefinition._2();

				var optionalMower = MowerConfigurationParser.parse(optionalMowerDefinition);
				var optionalCommands = CommandConfigurationParser.parse(optionalCommandsDefinition);

				return new Tuple2<>(optionalMower, optionalCommands);
			}).collect(Collectors.toList());

		return new MowingSimulatorConfiguration(lawn, mowerAndCommands, optionalMowersAndCommands);
	}
}
