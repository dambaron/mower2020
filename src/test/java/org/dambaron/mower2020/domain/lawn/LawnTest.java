package org.dambaron.mower2020.domain.lawn;

import java.util.Map;

import org.dambaron.mower2020.domain.CardinalOrientation;
import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.MowingStatus;
import org.dambaron.mower2020.domain.WayPoint;
import org.dambaron.mower2020.domain.mower.Mower;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.dambaron.mower2020.domain.CartesianPosition.ZERO_ZERO_POSITION;
import static org.dambaron.mower2020.domain.mower.Mower.ZERO_ZERO_NORTH_MOWER;

class LawnTest {

	private static final CartesianPosition ONE_ONE_POSITION = new CartesianPosition(1, 1);

	private static final Lawn ONE_BY_ONE_LAWN_WITHOUT_MOWER = new Lawn(ONE_ONE_POSITION);

	private static final Lawn ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER =
		new Lawn(ONE_ONE_POSITION).addMower(ZERO_ZERO_NORTH_MOWER);

	@Test
	void test_validateCorners_should_throw_exception_when_lowerLeftCorner_is_null() {
		// given

		// when
		var thrown = catchThrowable(() -> Lawn.validateCorners(null, ONE_ONE_POSITION));

		// then
		assertThat(thrown)
			.isInstanceOf(NullPointerException.class)
			.hasMessage("lowerLeftCorner must not be null");
	}

	@Test
	void test_validateCorners_should_throw_exception_when_upperRightCorner_is_null() {
		// given

		// when
		var thrown = catchThrowable(() -> Lawn.validateCorners(ZERO_ZERO_POSITION, null));

		// then
		assertThat(thrown)
			.isInstanceOf(NullPointerException.class)
			.hasMessage("upperRightCorner must not be null");
	}

	@Test
	void test_validateCorners_should_throw_error_when_lawn_is_an_horizontal_line() {
		// given
		var lowerLeftCorner = ZERO_ZERO_POSITION;
		var upperRightCorner = new CartesianPosition(1, lowerLeftCorner.y());

		// when
		var thrown = catchThrowable(() -> Lawn.validateCorners(lowerLeftCorner, upperRightCorner));

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Lawn must be at least 1 unit long on the Y-Axis");
	}

	@Test
	void test_validateCorners_should_throw_error_when_lawn_is_a_vertical_line() {
		// given
		var lowerLeftCorner = ZERO_ZERO_POSITION;
		var upperRightCorner = new CartesianPosition(lowerLeftCorner.x(), 1);

		// when
		var thrown = catchThrowable(() -> Lawn.validateCorners(lowerLeftCorner, upperRightCorner));

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Lawn must be at least 1 unit wide on the X-Axis");
	}

	@Test
	void test_addMower() {
		// given

		// when
		var actualLawn2 = ONE_BY_ONE_LAWN_WITHOUT_MOWER.addMower(ZERO_ZERO_NORTH_MOWER);

		// then
		assertThat(actualLawn2).isNotNull();

		var actualMowers = actualLawn2.mowersByPosition();
		var expectedMowers = Map.of(ZERO_ZERO_POSITION, ZERO_ZERO_NORTH_MOWER);

		assertThat(actualMowers)
			.isNotNull()
			.isEqualTo(expectedMowers);
	}

	@Test
	void test_addMower_should_have_no_side_effect() {
		// given

		// when
		ONE_BY_ONE_LAWN_WITHOUT_MOWER.addMower(ZERO_ZERO_NORTH_MOWER);

		// then
		assertThat(ONE_BY_ONE_LAWN_WITHOUT_MOWER.mowersByPosition()).isEmpty();
	}

	@Test
	void test_addMowerToPosition() {
		// given

		// when
		var actualLawn2 = ONE_BY_ONE_LAWN_WITHOUT_MOWER.addMowerToPosition(ZERO_ZERO_NORTH_MOWER, ZERO_ZERO_POSITION);

		// then
		assertThat(actualLawn2).isNotNull();

		var actualMowers = actualLawn2.mowersByPosition();
		var expectedMowers = Map.of(ZERO_ZERO_POSITION, ZERO_ZERO_NORTH_MOWER);

		assertThat(actualMowers)
			.isNotNull()
			.isEqualTo(expectedMowers);
	}

	@Test
	void test_addMowerToPosition_should_have_no_side_effect() {
		// given

		// when
		ONE_BY_ONE_LAWN_WITHOUT_MOWER.addMowerToPosition(ZERO_ZERO_NORTH_MOWER, ZERO_ZERO_POSITION);

		// then
		assertThat(ONE_BY_ONE_LAWN_WITHOUT_MOWER.mowersByPosition()).isEmpty();
	}

	@Test
	void test_addMowerToPosition_should_throw_exception_when_position_is_outside() {
		// given
		var outsidePosition = new CartesianPosition(12, 34);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITHOUT_MOWER.addMowerToPosition(ZERO_ZERO_NORTH_MOWER, outsidePosition)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Position(x=%s, y=%s) is out of field",
				outsidePosition.x(),
				outsidePosition.y());
	}

	@Test
	void test_addMowerToPosition_should_throw_exception_when_mower_position_is_outside() {
		// given
		var outsidePosition = new CartesianPosition(12, 34);
		var outsideWayPoint = new WayPoint(outsidePosition, CardinalOrientation.N);
		var outsideMower = new Mower(outsideWayPoint);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITHOUT_MOWER.addMowerToPosition(outsideMower, ZERO_ZERO_POSITION)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Mower position(x=%s, y=%s) is out of field", outsidePosition.x(), outsidePosition.y());
	}

	@Test
	void test_addMowerToPosition_should_throw_exception_when_position_and_mower_position_are_different() {
		// given
		var position = new CartesianPosition(1, 1);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITHOUT_MOWER.addMowerToPosition(ZERO_ZERO_NORTH_MOWER, position)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Mower should be at position(x=%s, y=%s) but was (x=%s, y=%s)",
				position.x(),
				position.y(),
				ZERO_ZERO_NORTH_MOWER.currentWayPoint().position().x(),
				ZERO_ZERO_NORTH_MOWER.currentWayPoint().position().y());
	}

	@Test
	void test_addMowerToPosition_should_throw_exception_when_position_is_occupied_by_another_mower() {
		// given
		var zeroZeroWestWaypoint = new WayPoint(ZERO_ZERO_POSITION, CardinalOrientation.W);
		var zeroZeroWestMower = new Mower(zeroZeroWestWaypoint);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.addMowerToPosition(zeroZeroWestMower,
				ZERO_ZERO_POSITION)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Position(x=%s, y=%s) is occupied by mower(uuid=%s)",
				ZERO_ZERO_POSITION.x(),
				ZERO_ZERO_POSITION.y(),
				ZERO_ZERO_NORTH_MOWER.uuid());
	}

	@Test
	void test_removeMowerFromPosition() {
		// given

		// when
		var actualLawn2 = ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.removeMowerFromPosition(ZERO_ZERO_NORTH_MOWER,
			ZERO_ZERO_POSITION);

		// then
		assertThat(actualLawn2).isNotNull();

		var actualMowers = actualLawn2.mowersByPosition();
		assertThat(actualMowers).isEmpty();
	}

	@Test
	void test_removeMowerFromPosition_should_have_no_side_effect() {
		// given

		// when
		ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.removeMowerFromPosition(ZERO_ZERO_NORTH_MOWER, ZERO_ZERO_POSITION);

		// then
		assertThat(ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.mowersByPosition()).isNotEmpty();
	}

	@Test
	void test_removeMowerFromPosition_should_throw_exception_when_position_is_outside() {
		// given
		var outsidePosition = new CartesianPosition(2, 2);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.removeMowerFromPosition(ZERO_ZERO_NORTH_MOWER,
				outsidePosition)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Position(x=%s, y=%s) is out of field",
				outsidePosition.x(),
				outsidePosition.y());
	}

	@Test
	void test_removeMowerFromPosition_should_throw_exception_when_mower_position_is_outside() {
		// given
		var outsidePosition = new CartesianPosition(12, 34);
		var outsideWayPoint = new WayPoint(outsidePosition, CardinalOrientation.N);
		var outsideMower = new Mower(outsideWayPoint);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.removeMowerFromPosition(outsideMower,
				ZERO_ZERO_POSITION)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Mower position(x=%s, y=%s) is out of field",
				outsidePosition.x(),
				outsidePosition.y());
	}

	@Test
	void test_removeMowerFromPosition_should_throw_exception_when_position_and_mower_position_are_different() {
		// given
		var position = new CartesianPosition(1, 1);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.removeMowerFromPosition(ZERO_ZERO_NORTH_MOWER,
				position)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Mower should be at position(x=%s, y=%s) but was (x=%s, y=%s)",
				position.x(),
				position.y(),
				ZERO_ZERO_NORTH_MOWER.currentWayPoint().position().x(),
				ZERO_ZERO_NORTH_MOWER.currentWayPoint().position().y());
	}

	@Test
	void test_removeMowerFromPosition_should_throw_exception_when_position_is_occupied_by_another_mower() {
		// given
		var position = new CartesianPosition(0, 0);
		var wayPoint = new WayPoint(position, CardinalOrientation.N);
		var mower = new Mower(wayPoint);

		// when
		var thrown = catchThrowable(
			() -> ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.removeMowerFromPosition(mower,
				ZERO_ZERO_POSITION)
		);

		// then
		assertThat(thrown)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Position(x=%s, y=%s) is occupied by mower(uuid=%s)",
				ZERO_ZERO_POSITION.x(),
				ZERO_ZERO_POSITION.y(),
				ZERO_ZERO_NORTH_MOWER.uuid());
	}

	@Test
	void test_addMowingStatus() {
		// given

		// when
		var actualLawn2 =
			ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.addMowingStatus(ZERO_ZERO_POSITION, MowingStatus.MOWED);

		// then
		assertThat(actualLawn2).isNotNull();

		var actualMowingStatutes = actualLawn2.mowingStatusesByPosition();
		var expectedMowingStatuses = Map.of(ZERO_ZERO_POSITION, MowingStatus.MOWED);
		assertThat(actualMowingStatutes)
			.isNotNull()
			.isEqualTo(expectedMowingStatuses);
	}

	@Test
	void test_addMowingStatus_should_have_no_side_effect() {
		// given

		// when
		ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.addMowingStatus(ZERO_ZERO_POSITION, MowingStatus.MOWED);

		// then
		assertThat(ONE_BY_ONE_LAWN_WITH_ZERO_ZERO_NORTH_MOWER.mowingStatusesByPosition()).isEmpty();
	}

	@ParameterizedTest
	@ArgumentsSource(LawnFixtures.PositionInsideFieldProvider.class)
	void test_isPositionInField_should_return_true(Lawn lawn, CartesianPosition position) {
		// given

		// when
		var isPositionInField = lawn.isPositionInField(position);

		// then
		assertThat(isPositionInField).isTrue();
	}

	@ParameterizedTest
	@ArgumentsSource(LawnFixtures.PositionOutsideFieldProvider.class)
	void test_isPositionInField_should_return_false(Lawn lawn, CartesianPosition position) {
		// given

		// when
		var isPositionInField = lawn.isPositionInField(position);

		// then
		assertThat(isPositionInField).isFalse();
	}
}
