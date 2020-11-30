package org.dambaron.mower2020.application.configuration;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dambaron.mower2020.domain.CardinalOrientation;
import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.WayPoint;
import org.dambaron.mower2020.domain.mower.Mower;

public final class MowerConfigurationParser {

	public static Mower parse(String mowerDefinition) {

		var isMowerDefinitionBlank = StringUtils.isBlank(mowerDefinition);
		if (isMowerDefinitionBlank) {
			throw new IllegalArgumentException("Mower definition must not be blank");
		}

		var mowerDefinitionItems = List.of(StringUtils.split(mowerDefinition));
		if (mowerDefinitionItems.size() != 3) {
			throw new IllegalArgumentException("Mower definition must contain 3 elements exactly");
		}

		boolean isNumericX = StringUtils.isNumeric(mowerDefinitionItems.get(0));
		if (!isNumericX) {
			throw new IllegalArgumentException("X coordinate must be numeric");
		}

		boolean isNumericY = StringUtils.isNumeric(mowerDefinitionItems.get(1));
		if (!isNumericY) {
			throw new IllegalArgumentException("Y coordinate must be numeric");
		}

		var x = Integer.parseInt(mowerDefinitionItems.get(0));
		var y = Integer.parseInt(mowerDefinitionItems.get(1));
		var o = mowerDefinitionItems.get(2);

		var position = new CartesianPosition(x, y);
		var orientation = CardinalOrientation.valueOf(o);
		var originalWayPoint = new WayPoint(position, orientation);

		return new Mower(originalWayPoint);
	}
}
