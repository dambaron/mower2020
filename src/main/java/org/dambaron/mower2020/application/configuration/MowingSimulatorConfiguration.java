package org.dambaron.mower2020.application.configuration;

import java.util.List;
import java.util.Objects;

import io.vavr.Tuple2;
import org.dambaron.mower2020.domain.command.Command;
import org.dambaron.mower2020.domain.lawn.Lawn;
import org.dambaron.mower2020.domain.mower.Mower;

public final record MowingSimulatorConfiguration(Lawn lawn,
												 Tuple2<Mower, List<Command>> mowerAndCommands,
												 List<Tuple2<Mower, List<Command>>> optionalMowersAndCommands) {

	public MowingSimulatorConfiguration {
		Objects.requireNonNull(lawn, "lawn must not be null");
		Objects.requireNonNull(mowerAndCommands, "mowerAndCommands must not be null");
	}
}
