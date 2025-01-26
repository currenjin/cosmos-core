package com.cosmoscore.common.coordinate;

/**
 * Represents a position in the equatorial coordinate system.
 * This system uses right ascension (RA) and declination (Dec) to specify positions on the celestial sphere.
 */
public record EquatorialCoordinate(double rightAscension, double declination) {

	/**
	 * Creates a new equatorial coordinate with the given right ascension and declination.
	 *
	 * @param rightAscension right ascension in degrees (0 to 360)
	 * @param declination declination in degrees (-90 to +90)
	 * @throws IllegalArgumentException if the coordinates are outside their valid ranges
	 */
	public EquatorialCoordinate {
		validateRightAscension(rightAscension);
		validateDeclination(declination);
	}

	private void validateRightAscension(double ra) {
		if (ra < 0 || ra > 360) {
			throw new IllegalArgumentException("Right ascension must be between 0 and 360 degrees");
		}
	}

	private void validateDeclination(double dec) {
		if (dec < -90 || dec > 90) {
			throw new IllegalArgumentException("Declination must be between -90 and +90 degrees");
		}
	}

	/**
	 * Converts right ascension from degrees to hours.
	 * In astronomy, right ascension is traditionally measured in hours (0 to 24).
	 *
	 * @return right ascension in hours
	 */
	public double rightAscensionHours() {
		return rightAscension / 15.0;
	}

	@Override
	public String toString() {
		return String.format("RA: %.2f°, Dec: %.2f°", rightAscension, declination);
	}
}
