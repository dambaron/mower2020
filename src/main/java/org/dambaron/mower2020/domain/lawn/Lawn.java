package org.dambaron.mower2020.domain.lawn;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.MowingStatus;
import org.dambaron.mower2020.domain.mower.Mower;

public record Lawn(CartesianPosition lowerLeftCorner,
				   CartesianPosition upperRightCorner,
				   Map<CartesianPosition, Mower> mowersByPosition,
				   Map<CartesianPosition, MowingStatus> mowingStatusesByPosition) {

	public Lawn(CartesianPosition upperRightCorner) {
		this(CartesianPosition.ZERO_ZERO_POSITION, upperRightCorner, new HashMap<>(), new HashMap<>());
	}

	public Lawn {
		validateCorners(lowerLeftCorner, upperRightCorner);
	}

	public static void validateCorners(CartesianPosition lowerLeftCorner,
									   CartesianPosition upperRightCorner) {

		Objects.requireNonNull(lowerLeftCorner, "lowerLeftCorner must not be null");
		Objects.requireNonNull(upperRightCorner, "upperRightCorner must not be null");

		if (upperRightCorner.x() == lowerLeftCorner.x()) {
			throw new IllegalArgumentException("Lawn must be at least 1 unit wide on the X-Axis");
		}

		if (upperRightCorner.y() == lowerLeftCorner.y()) {
			throw new IllegalArgumentException("Lawn must be at least 1 unit long on the Y-Axis");
		}
	}

	public Lawn addMowingStatus(CartesianPosition position, MowingStatus mowingStatus) {
		var mowingStatusesByPosition = new HashMap<>(this.mowingStatusesByPosition);
		mowingStatusesByPosition.put(position, mowingStatus);
		return new Lawn(this.lowerLeftCorner, this.upperRightCorner, this.mowersByPosition, mowingStatusesByPosition);
	}

	public Lawn addMower(Mower mower) {
		return addMowerToPosition(mower, mower.currentWayPoint().position());
	}

	public Lawn addMowerToPosition(Mower mower, CartesianPosition position) {

		checkPositionIsInField(position);
		checkMowerPositionIsInField(mower.currentWayPoint().position());
		checkMowerIsInPosition(mower.currentWayPoint().position(), position);
		checkPositionIsFreeOrOccupiedByMower(mower.uuid(), position);

		var mowersByPosition = new HashMap<>(this.mowersByPosition);
		mowersByPosition.put(position, mower);
		return new Lawn(this.lowerLeftCorner, this.upperRightCorner, mowersByPosition, this.mowingStatusesByPosition);
	}

	private void checkPositionIsInField(CartesianPosition position) {
		boolean isPositionInField = this.isPositionInField(position);
		if (!isPositionInField) {
			var errorMessageTemplate = "Position(x=%s, y=%s) is out of field";
			var errorMessage = String.format(errorMessageTemplate, position.x(), position.y());
			throw new IllegalArgumentException(errorMessage);
		}
	}

	private void checkMowerPositionIsInField(CartesianPosition mowerPosition) {
		boolean isMowerInField = this.isPositionInField(mowerPosition);
		if (isMowerInField) {
			return;
		}

		var errorMessageTemplate = "Mower position(x=%s, y=%s) is out of field";
		var errorMessage = String.format(errorMessageTemplate, mowerPosition.x(), mowerPosition.y());
		throw new IllegalArgumentException(errorMessage);
	}

	private void checkMowerIsInPosition(CartesianPosition mowerPosition,
										CartesianPosition position) {
		boolean isMowerInPosition = position.equals(mowerPosition);
		if (isMowerInPosition) {
			return;
		}

		var errorMessageTemplate = "Mower should be at position(x=%s, y=%s) but was (x=%s, y=%s)";
		var errorMessage = String.format(errorMessageTemplate,
			position.x(),
			position.y(),
			mowerPosition.x(),
			mowerPosition.y());
		throw new IllegalArgumentException(errorMessage);
	}

	private void checkPositionIsFreeOrOccupiedByMower(String mowerUUID, CartesianPosition position) {
		var mowerAtPosition = this.mowersByPosition().get(position);
		if (mowerAtPosition == null) {
			return; // Free position
		}

		var mowerAtPositionUUID = mowerAtPosition.uuid();
		boolean isSameMower = mowerAtPositionUUID.equals(mowerUUID);
		if (isSameMower) {
			return; // Occupied by self
		}

		var errorMessageTemplate = "Position(x=%s, y=%s) is occupied by mower(uuid=%s)";
		var errorMessage = String.format(errorMessageTemplate,
			position.x(),
			position.y(),
			mowerAtPositionUUID);
		throw new IllegalArgumentException(errorMessage);
	}

	public boolean isPositionInField(CartesianPosition position) {
		if (position == null) {
			return false;
		}

		var minX = this.lowerLeftCorner.x();
		var minY = this.lowerLeftCorner.y();

		var maxX = this.upperRightCorner.x();
		var maxY = this.upperRightCorner.y();

		var currentX = position.x();
		var currentY = position.y();

		return currentX >= minX
			&& currentX <= maxX
			&& currentY >= minY
			&& currentY <= maxY;
	}

	public Lawn removeMower(Mower mower) {
		return removeMowerFromPosition(mower, mower.currentWayPoint().position());
	}

	public Lawn removeMowerFromPosition(Mower mower, CartesianPosition position) {

		checkPositionIsInField(position);
		checkMowerPositionIsInField(mower.currentWayPoint().position());
		checkMowerIsInPosition(mower.currentWayPoint().position(), position);
		checkPositionIsFreeOrOccupiedByMower(mower.uuid(), position);

		var mowersByPosition = new HashMap<>(this.mowersByPosition);
		mowersByPosition.remove(position);
		return new Lawn(this.lowerLeftCorner, this.upperRightCorner, mowersByPosition, this.mowingStatusesByPosition);
	}
}
