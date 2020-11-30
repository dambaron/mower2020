package org.dambaron.mower2020.domain.mower;

import java.util.List;
import java.util.Map;

import org.dambaron.mower2020.domain.CardinalOrientation;
import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.MowingStatus;
import org.dambaron.mower2020.domain.WayPoint;
import org.dambaron.mower2020.domain.command.Command;
import org.dambaron.mower2020.domain.lawn.Lawn;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dambaron.mower2020.domain.mower.Mower.ZERO_ZERO_NORTH_MOWER;

class MowerTest {

	@Test
	void test_mow() {
		// given
		var mower = new Mower(new WayPoint(CartesianPosition.ZERO_ZERO_POSITION, CardinalOrientation.N));

		var lawn = new Lawn(new CartesianPosition(1, 1));
		lawn = lawn.addMower(mower);

		// when
		var actualLawn = mower.mow(lawn);

		// then
		var actualMowingStatus = actualLawn.mowingStatusesByPosition();
		var expectedMowingStatus = Map.of(mower.currentWayPoint().position(), MowingStatus.MOWED);

		assertThat(actualMowingStatus).isEqualTo(expectedMowingStatus);
	}

	@Test
	void test_mow_should_have_no_side_effect() {
		// given
		var mower = new Mower(new WayPoint(CartesianPosition.ZERO_ZERO_POSITION, CardinalOrientation.N));

		var lawn = new Lawn(new CartesianPosition(1, 1));
		lawn = lawn.addMower(mower);

		// when
		mower.mow(lawn);

		// then
		assertThat(lawn.mowingStatusesByPosition()).isEmpty();
	}

	@Test
	void test_prepareNextMove() {
		// given
		var leftRotation = Command.L;
		var expectedNextWayPoint = new WayPoint(CartesianPosition.ZERO_ZERO_POSITION, CardinalOrientation.W);

		// when
		var actualMower = ZERO_ZERO_NORTH_MOWER.prepareNextMove(leftRotation);

		// then
		assertThat(actualMower.nextWayPoint()).isEqualTo(expectedNextWayPoint);

		assertThat(actualMower)
			.usingRecursiveComparison()
			.ignoringFields("nextWayPoint")
			.isEqualTo(ZERO_ZERO_NORTH_MOWER);
	}

	@Test
	void test_prepareNextMove_should_have_no_side_effect() {
		// given
		var lefRotation = Command.L;

		// when
		ZERO_ZERO_NORTH_MOWER.prepareNextMove(lefRotation);

		// then
		assertThat(ZERO_ZERO_NORTH_MOWER.nextWayPoint()).isNull();
	}

	@Test
	void test_move() {
		// given
		var initialLawn = new Lawn(new CartesianPosition(1, 1));
		initialLawn = initialLawn.addMower(ZERO_ZERO_NORTH_MOWER);

		var commands = List.of(Command.F, Command.L, Command.L, Command.F);
		var mower = ZERO_ZERO_NORTH_MOWER;

		// when
		var actualMower = ZERO_ZERO_NORTH_MOWER;
		var actualLawn = initialLawn;
		for (var command : commands) {
			var mowerWithNextWayPoint = actualMower.prepareNextMove(command);
			var updateMowerAndLawn = mowerWithNextWayPoint.move(actualLawn);

			actualMower = updateMowerAndLawn._1();
			actualLawn = updateMowerAndLawn._2();
		}

		// then
		assertThat(actualMower.uuid()).isEqualTo(ZERO_ZERO_NORTH_MOWER.uuid());
		assertThat(actualMower.originalWayPoint()).isEqualTo(ZERO_ZERO_NORTH_MOWER.originalWayPoint());
		assertThat(actualMower.nextWayPoint()).isNull();

		var expectedCurrentWayPoint = new WayPoint(CartesianPosition.ZERO_ZERO_POSITION, CardinalOrientation.S);
		assertThat(actualMower.currentWayPoint()).isEqualTo(expectedCurrentWayPoint);

		assertThat(actualMower.journey()).hasSize(5);
		assertThat(actualMower.journey()).containsExactly(
			new WayPoint(new CartesianPosition(0, 0), CardinalOrientation.N),
			new WayPoint(new CartesianPosition(0, 1), CardinalOrientation.N),
			new WayPoint(new CartesianPosition(0, 1), CardinalOrientation.W),
			new WayPoint(new CartesianPosition(0, 1), CardinalOrientation.S),
			new WayPoint(new CartesianPosition(0, 0), CardinalOrientation.S)
		);

		assertThat(actualLawn.mowersByPosition()).hasSize(1);
	}
}
