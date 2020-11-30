package org.dambaron.mower2020.domain;

import java.util.List;

public enum MowingStatus {
	MOWED,
	MOWING_IN_PROGRESS,
	MOWING_FAILED,
	WILD;

	public boolean isAllowedTransition(MowingStatus nextMowingStatus) {
		return this.allowedTransitions().contains(nextMowingStatus);
	}

	public List<MowingStatus> allowedTransitions() {
		return switch (this) {
			case WILD, MOWING_FAILED -> List.of(MOWING_IN_PROGRESS);
			case MOWED -> List.of(MOWED);
			case MOWING_IN_PROGRESS -> List.of(MOWING_FAILED, MOWED);
		};
	}
}
