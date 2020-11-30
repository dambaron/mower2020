package org.dambaron.mower2020.application.configuration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.dambaron.mower2020.domain.CartesianPosition;
import org.dambaron.mower2020.domain.lawn.Lawn;

public final class LawnConfigurationParser {

	public static Lawn parse(String upperRightCornerDefinition) {

		boolean isUpperRightCornerDefinitionBlank = StringUtils.isBlank(upperRightCornerDefinition);
		if (isUpperRightCornerDefinitionBlank) {
			throw new IllegalArgumentException("Upper right corner definition must not be blank");
		}

		var coordinates = Arrays.asList(StringUtils.split(upperRightCornerDefinition));
		if (coordinates.size() != 2) {
			throw new IllegalArgumentException("Lawn definition must contain 2 elements exactly");
		}

		boolean isNumericX = StringUtils.isNumeric(coordinates.get(0));
		if (!isNumericX) {
			throw new IllegalArgumentException("X coordinate must be numeric");
		}

		boolean isNumericY = StringUtils.isNumeric(coordinates.get(1));
		if (!isNumericY) {
			throw new IllegalArgumentException("Y coordinate must be numeric");
		}

		var x = Integer.parseInt(coordinates.get(0));
		var y = Integer.parseInt(coordinates.get(1));

		var upperRightCorner = new CartesianPosition(x, y);
		return new Lawn(upperRightCorner);
	}
}
