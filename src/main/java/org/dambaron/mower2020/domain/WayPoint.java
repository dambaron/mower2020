package org.dambaron.mower2020.domain;

import java.util.Objects;

import org.dambaron.mower2020.domain.command.Command;

import static org.dambaron.mower2020.domain.CardinalOrientation.N;
import static org.dambaron.mower2020.domain.CartesianPosition.ZERO_ZERO_POSITION;

public record WayPoint(CartesianPosition position, CardinalOrientation orientation) {

	public static final WayPoint ZERO_ZERO_NORTH_WAY_POINT = new WayPoint(ZERO_ZERO_POSITION, N);

	public WayPoint handle(Command command) {
		Objects.requireNonNull(command, "command must not be null");

		return switch (command) {
			case L -> rotateLeft();
			case R -> rotateRight();
			case B -> translateBackward();
			case F -> translateForward();
		};
	}

	private WayPoint rotateLeft() {
		return new WayPoint(position, orientation.getLeftOrientation());
	}

	private WayPoint rotateRight() {
		return new WayPoint(position, orientation.getRightOrientation());
	}

//    public WayPoint rotate(Rotation rotation) {
//        var nextOrientation = switch (rotation) {
//            case L -> orientation.getLeftOrientation();
//            case R -> orientation.getRightOrientation();
//        };
//        return new WayPoint(position, nextOrientation);
//    }

//    public WayPoint translate(Translation translation) {
//        return switch (translation) {
//            case F -> translateForward();
//            case B -> translateBackward();
//        };
//    }

	private WayPoint translateBackward() {
		var nextPosition = switch (orientation) {
			case N -> new CartesianPosition(position.x(), position.y() - 1);
			case S -> new CartesianPosition(position.x(), position.y() + 1);
			case W -> new CartesianPosition(position.x() + 1, position.y());
			case E -> new CartesianPosition(position.x() - 1, position.y());
		};
		return new WayPoint(nextPosition, this.orientation);
	}

	private WayPoint translateForward() {
		var nextPosition = switch (orientation) {
			case N -> new CartesianPosition(position.x(), position.y() + 1);
			case S -> new CartesianPosition(position.x(), position.y() - 1);
			case W -> new CartesianPosition(position.x() - 1, position.y());
			case E -> new CartesianPosition(position.x() + 1, position.y());
		};
		return new WayPoint(nextPosition, this.orientation);
	}
}
