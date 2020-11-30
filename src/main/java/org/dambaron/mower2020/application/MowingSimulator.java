package org.dambaron.mower2020.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.dambaron.mower2020.application.configuration.MowingSimulatorConfiguration;
import org.dambaron.mower2020.application.configuration.MowingSimulatorConfigurationParser;
import org.dambaron.mower2020.domain.WayPoint;
import org.dambaron.mower2020.domain.command.Command;
import org.dambaron.mower2020.domain.lawn.Lawn;
import org.dambaron.mower2020.domain.mower.Mower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static io.vavr.Predicates.instanceOf;

public class MowingSimulator {

	private static final Logger LOGGER = LoggerFactory.getLogger(MowingSimulator.class);
	private static final CommandLineParser COMMAND_LINE_PARSER = new DefaultParser();

	private final static Options appOptions = new Options();

	public MowingSimulator() {
		//DO NOTHING
	}

	public static void main(String[] args) {

		// init available options
		initCliOptions();

		// parse the command line arguments group by group
		var runSimulatorAttempt = Try.run(() -> {
			// create the parser
			var parsedCommandLine = COMMAND_LINE_PARSER.parse(appOptions, args);

			if (parsedCommandLine.hasOption("c")) {

				var configurationFile = parsedCommandLine.getOptionValue("c");

				LOGGER.info("Launching mowing simulator");
				var mowingSimulator = new MowingSimulator();
				mowingSimulator.runSimulator(configurationFile);

				LOGGER.info("Mowing simulator stopped");
				//mowingSimulator.displayJourneys();
			}
		});

		if (runSimulatorAttempt.isSuccess()) {
			System.exit(0);
		}

		Match(runSimulatorAttempt.getCause()).of(
			Case($(instanceOf(ParseException.class)), run(() -> {
				LOGGER.error("Parsing failed", runSimulatorAttempt.getCause());
				usage();
				System.exit(-1);
			})),
			Case($(), run(() -> {
				LOGGER.error("Simulator failed", runSimulatorAttempt.getCause());
				System.exit(-1);
			}))
		);
	}

	/**
	 * Init CLI options
	 */
	protected static void initCliOptions() {

		// create the Options
		appOptions.addRequiredOption("c", "config", true, "Use given file as config");
	}

	public Lawn runSimulator(String pathToConfigurationFile) {

		Objects.requireNonNull(pathToConfigurationFile, "pathToConfigurationFile must not be null");

		var configurationFile = new File(pathToConfigurationFile);
		if (!configurationFile.exists()) {
			LOGGER.error("Configuration file {} does not exist", configurationFile);
			throw new IllegalArgumentException("Configuration file does not exist");
		}

		var configuration = MowingSimulatorConfigurationParser.parseFile(configurationFile);

		return runSimulator(configuration);
	}

	protected static void usage() {
		// Generate help statement
		LOGGER.info("usage: java MowerApp -c,--config <file>");
	}

	public Lawn runSimulator(MowingSimulatorConfiguration configuration) {

		Objects.requireNonNull(configuration);

		var mowersAndCommands = new ArrayList<Tuple2<Mower, List<Command>>>();

		mowersAndCommands.add(configuration.mowerAndCommands());
		mowersAndCommands.addAll(configuration.optionalMowersAndCommands());

		Lawn lawn = configuration.lawn();
		Mower mower;
		for (var mowerAndCommands : mowersAndCommands) {
			mower = mowerAndCommands._1();
			for (var command : mowerAndCommands._2()) {
				var mowerWithNextMove = mower.prepareNextMove(command);
				var updatedMowerAndLawn = mowerWithNextMove.move(lawn);

				mower = updatedMowerAndLawn._1();
				lawn = updatedMowerAndLawn._2();
			}

			displayJourney(mower.journey());
			//LOGGER.info("{}", mower.journey());
		}

		return lawn;
	}

	public void displayJourney(List<WayPoint> journey) {

		Objects.requireNonNull("journey must not be null");

		var startPointTemplate = "{Start=[x=%s, y=%s, o=%s]}";
		var startPoint = journey.get(0);
		var startPointDisplay = String.format(startPointTemplate,
			startPoint.position().x(),
			startPoint.position().y(),
			startPoint.orientation().getCode());
		LOGGER.info("{}", startPointDisplay);

		if (journey.size() >= 3) {
			var intermediatePointTemplate = "{Intermediate=[x=%s, y=%s, o=%s]}";

			IntStream.rangeClosed(1, journey.size() - 2)
				.forEach(i -> {
					var intermediatePoint = journey.get(i);
					var intermediatePointDisplay = String.format(intermediatePointTemplate,
						intermediatePoint.position().x(),
						intermediatePoint.position().y(),
						intermediatePoint.orientation().getCode()
					);
					LOGGER.info("{}", intermediatePointDisplay);
				});
		}

		var stopPointTemplate = "{Stop=[x=%s, y=%s, o=%s]}";
		var stopPoint = journey.get(journey.size() -1);
		var stopPointDisplay = String.format(stopPointTemplate,
			stopPoint.position().x(),
			stopPoint.position().y(),
			stopPoint.orientation().getCode());

		LOGGER.info("{}", stopPointDisplay);
	}
}
