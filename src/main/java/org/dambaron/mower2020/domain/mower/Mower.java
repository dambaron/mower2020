package org.dambaron.mower2020.domain.mower;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import io.vavr.Tuple2;
import org.dambaron.mower2020.domain.MowingStatus;
import org.dambaron.mower2020.domain.WayPoint;
import org.dambaron.mower2020.domain.command.Command;
import org.dambaron.mower2020.domain.lawn.Lawn;

public record Mower(String uuid,
					WayPoint originalWayPoint,
					WayPoint currentWayPoint,
					WayPoint nextWayPoint,
					List<WayPoint> journey) //implements Movable<Mower>
{

	public static final Mower ZERO_ZERO_NORTH_MOWER = new Mower(WayPoint.ZERO_ZERO_NORTH_WAY_POINT);

	public Mower(WayPoint originalWayPoint) {
		this(UUID.randomUUID().toString(), originalWayPoint, originalWayPoint, null, List.of(originalWayPoint));
	}

	public Mower {
		Objects.requireNonNull(uuid, "uuid must not be null");
		Objects.requireNonNull(originalWayPoint, "originalWayPoint must not be null");
		Objects.requireNonNull(currentWayPoint, "currentWayPoint must not be null");
		Objects.requireNonNull(journey, "journey must not be null");

		if (journey.isEmpty()) {
			throw new IllegalArgumentException("journey must not be empty");
		}

		boolean isJourneyStartingWithOriginalWayPoint = originalWayPoint.equals(journey.get(0));
		if (!isJourneyStartingWithOriginalWayPoint) {
			throw new IllegalArgumentException("journey must start with originalWayPoint");
		}
	}

	public Lawn mow(Lawn lawn2) {
		var mowerPosition = this.currentWayPoint.position();
		return lawn2.addMowingStatus(mowerPosition, MowingStatus.MOWED);
	}

	public Mower prepareNextMove(Command command) {
		var nextWayPoint = this.currentWayPoint().handle(command);
		return new Mower(this.uuid, this.originalWayPoint, this.currentWayPoint, nextWayPoint, this.journey);
	}

	public Tuple2<Mower, Lawn> move(Lawn lawn2) {

		var nextJourney = new ArrayList<>(this.journey);
		nextJourney.add(this.nextWayPoint);

		var nextMower = new Mower(this.uuid, this.originalWayPoint, nextWayPoint, null, nextJourney);
		var lawnWithoutCurrentMower = lawn2.removeMower(this);
		var lawnWithNextMower = lawnWithoutCurrentMower.addMower(nextMower);

		return new Tuple2<>(nextMower, lawnWithNextMower);
	}
}
