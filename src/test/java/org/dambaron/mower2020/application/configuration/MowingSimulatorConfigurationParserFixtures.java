package org.dambaron.mower2020.application.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import io.vavr.Tuple2;
import org.dambaron.mower2020.domain.CardinalOrientation;
import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.WayPoint;
import org.dambaron.mower2020.domain.command.Command;
import org.dambaron.mower2020.domain.lawn.Lawn;
import org.dambaron.mower2020.domain.mower.Mower;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class MowingSimulatorConfigurationParserFixtures {

	private static final CartesianPosition ONE_ZERO = new CartesianPosition(1, 0);
	private static final CartesianPosition ONE_ONE = new CartesianPosition(1, 1);

	private static final CartesianPosition TWO_ZERO = new CartesianPosition(2, 0);
	private static final CartesianPosition TWO_TWO = new CartesianPosition(2, 2);

	private static final CartesianPosition THREE_THREE = new CartesianPosition(3, 3);

	private static final Lawn ONE_BY_ONE_LAWN = new Lawn(ONE_ONE);
	private static final Lawn TWO_BY_TWO_LAWN = new Lawn(TWO_TWO);
	private static final Lawn THREE_BY_THREE_LAWN = new Lawn(THREE_THREE);

	private static final String ZERO_ZERO_NORTH_DEF = "0 0 N";
	private static final String ONE_ZERO_NORTH_DEF = "1 0 N";
	private static final String TWO_ZERO_NORTH_DEF = "2 0 N";
	private static final String THREE_ZERO_NORTH_DEF = "3 0 N";

	private static final String SINGLE_FORWARD_MOVE = "F";

	static final class ParsableConfigurationProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			var lines10 = List.of("1 1",
				ZERO_ZERO_NORTH_DEF, "FRF",
				ONE_ZERO_NORTH_DEF, "R");

			var position10 = new CartesianPosition(0, 0);
			var orientation10 = CardinalOrientation.N;
			var wayPoint10 = new WayPoint(position10, orientation10);
			var mower10 = new Mower(wayPoint10);

			var commands10 = List.of(Command.F, Command.R, Command.F);
			var mowerAndCommands10 = new Tuple2<>(mower10, commands10);

			var position11 = new CartesianPosition(1, 0);
			var orientation11 = CardinalOrientation.N;
			var wayPoint11 = new WayPoint(position11, orientation11);
			var mower11 = new Mower(wayPoint11);

			var commands11 = List.of(Command.R);
			var mowerAndCommands11 = new Tuple2<>(mower11, commands11);

			var expectedConfiguration10 = new MowingSimulatorConfiguration(ONE_BY_ONE_LAWN,
				mowerAndCommands10,
				List.of(mowerAndCommands11));

			var lines20 = List.of("2 2",
				ZERO_ZERO_NORTH_DEF, "FFRFF",
				ONE_ZERO_NORTH_DEF, "FRF",
				TWO_ZERO_NORTH_DEF, "R");

			var position20 = new CartesianPosition(0, 0);
			var orientation20 = CardinalOrientation.N;
			var wayPoint20 = new WayPoint(position20, orientation20);
			var mower20 = new Mower(wayPoint20);

			var commands20 = List.of(Command.F, Command.F, Command.R, Command.F, Command.F);
			var mowerAndCommands20 = new Tuple2<>(mower20, commands20);

			var position21 = new CartesianPosition(1, 0);
			var orientation21 = CardinalOrientation.N;
			var wayPoint21 = new WayPoint(position21, orientation21);
			var mower21 = new Mower(wayPoint21);

			var commands21 = List.of(Command.F, Command.R, Command.F);
			var mowerAndCommands21 = new Tuple2<>(mower21, commands21);

			var position22 = new CartesianPosition(2, 0);
			var orientation22 = CardinalOrientation.N;
			var wayPoint22 = new WayPoint(position22, orientation22);
			var mower22 = new Mower(wayPoint22);

			var commands22 = List.of(Command.R);
			var mowerAndCommands22 = new Tuple2<>(mower22, commands22);

			var expectedConfiguration20 = new MowingSimulatorConfiguration(TWO_BY_TWO_LAWN,
				mowerAndCommands20,
				List.of(mowerAndCommands21, mowerAndCommands22));

			var lines30 = List.of("3 3",
				ZERO_ZERO_NORTH_DEF, "FFFRFFF",
				ONE_ZERO_NORTH_DEF, "FFRFF",
				TWO_ZERO_NORTH_DEF, "FRF",
				THREE_ZERO_NORTH_DEF, "R");

			var position30 = new CartesianPosition(0, 0);
			var orientation30 = CardinalOrientation.N;
			var wayPoint30 = new WayPoint(position30, orientation30);

			var mower30 = new Mower(wayPoint30);
			var commands30 = List.of(Command.F, Command.F, Command.F, Command.R, Command.F, Command.F, Command.F);
			var mowerAndCommands30 = new Tuple2<>(mower30, commands30);

			var position31 = new CartesianPosition(1, 0);
			var orientation31 = CardinalOrientation.N;
			var wayPoint31 = new WayPoint(position31, orientation31);

			var mower31 = new Mower(wayPoint31);
			var commands31 = List.of(Command.F, Command.F, Command.R, Command.F, Command.F);
			var mowerAndCommands31 = new Tuple2<>(mower31, commands31);

			var position32 = new CartesianPosition(2, 0);
			var orientation32 = CardinalOrientation.N;
			var wayPoint32 = new WayPoint(position32, orientation32);

			var mower32 = new Mower(wayPoint32);
			var commands32 = List.of(Command.F, Command.R, Command.F);
			var mowerAndCommands32 = new Tuple2<>(mower32, commands32);

			var position33 = new CartesianPosition(3, 0);
			var orientation33 = CardinalOrientation.N;
			var wayPoint33 = new WayPoint(position33, orientation33);

			var mower33 = new Mower(wayPoint33);
			var commands33 = List.of(Command.R);
			var mowerAndCommands33 = new Tuple2<>(mower33, commands33);

			var expectedConfiguration30 = new MowingSimulatorConfiguration(THREE_BY_THREE_LAWN,
				mowerAndCommands30,
				List.of(mowerAndCommands31, mowerAndCommands32, mowerAndCommands33));

			return Stream.of(
				Arguments.of(lines10, expectedConfiguration10),
				Arguments.of(lines20, expectedConfiguration20),
				Arguments.of(lines30, expectedConfiguration30)
			);
		}
	}

	static final class UnparsableConfigurationProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {

			var arguments = new ArrayList<Arguments>();

			var blankLawnDefinitions = Arrays.asList(null, "", " ", "  ");

			for (var blankLawnDefinition : blankLawnDefinitions) {

				var blankLawnConfiguration = Arguments.of(blankLawnDefinition, new Tuple2<>(ZERO_ZERO_NORTH_DEF, SINGLE_FORWARD_MOVE));
				var expectedException = new RuntimeException("Error parsing configuration. Lawn definition must not be blank");

				arguments.add(Arguments.of(blankLawnConfiguration, expectedException));
			}

			return arguments.stream();
		}
	}
}
