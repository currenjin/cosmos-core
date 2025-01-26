package com.cosmoscore.common.coordinate;

/**
 * Represents a point on the celestial sphere using the equatorial coordinate system.
 * This system uses right ascension (RA) and declination (Dec) to specify positions.
 */
public record EquatorialCoordinate(double rightAscension, double declination) {

	/**
	 * Creates an equatorial coordinate with validation.
	 *
	 * @param rightAscension right ascension in degrees (0 to 360)
	 * @param declination declination in degrees (-90 to +90)
	 * @throws IllegalArgumentException if coordinates are outside valid ranges
	 */
	public EquatorialCoordinate {
		if (rightAscension < 0 || rightAscension >= 360) {
			throw new IllegalArgumentException("Right ascension must be between 0 and 360 degrees");
		}
		if (declination < -90 || declination > 90) {
			throw new IllegalArgumentException("Declination must be between -90 and +90 degrees");
		}
	}

	/**
	 * Returns the right ascension in hours (0 to 24).
	 *
	 * @return right ascension in hours
	 */
	public double rightAscensionHours() {
		return rightAscension / 15.0;
	}

	/**
	 * Returns the hour component of the right ascension.
	 *
	 * @return hours (0-23)
	 */
	public int rightAscensionHourPart() {
		return (int) (rightAscension / 15.0);
	}

	/**
	 * Returns the minute component of the right ascension.
	 *
	 * @return minutes (0-59)
	 */
	public int rightAscensionMinutePart() {
		double hours = rightAscension / 15.0;
		return (int) ((hours - rightAscensionHourPart()) * 60);
	}

	/**
	 * Returns the second component of the right ascension.
	 *
	 * @return seconds (0-59.99)
	 */
	public double rightAscensionSecondPart() {
		double hours = rightAscension / 15.0;
		double minutes = (hours - rightAscensionHourPart()) * 60;
		return (minutes - rightAscensionMinutePart()) * 60;
	}

	/**
	 * Returns the degree component of the declination.
	 *
	 * @return degrees (-90 to +90)
	 */
	public int declinationDegreePart() {
		return (int) declination;
	}

	/**
	 * Returns the arcminute component of the declination.
	 *
	 * @return arcminutes (0-59)
	 */
	public int declinationArcminutePart() {
		double absDecDegrees = Math.abs(declination);
		return (int) ((absDecDegrees - Math.abs(declinationDegreePart())) * 60);
	}

	/**
	 * Returns the arcsecond component of the declination.
	 *
	 * @return arcseconds (0-59.99)
	 */
	public double declinationArcsecondPart() {
		double absDecDegrees = Math.abs(declination);
		double arcminutes = (absDecDegrees - Math.abs(declinationDegreePart())) * 60;
		return (arcminutes - declinationArcminutePart()) * 60;
	}

	/**
	 * Calculates the angular separation between this coordinate and another coordinate.
	 * Uses the great circle distance formula.
	 *
	 * @param other the other coordinate
	 * @return angular separation in degrees
	 */
	public double angularSeparation(EquatorialCoordinate other) {
		double ra1 = Math.toRadians(this.rightAscension);
		double ra2 = Math.toRadians(other.rightAscension);
		double dec1 = Math.toRadians(this.declination);
		double dec2 = Math.toRadians(other.declination);

		double cosAngle = Math.sin(dec1) * Math.sin(dec2) +
			Math.cos(dec1) * Math.cos(dec2) * Math.cos(ra1 - ra2);

		return Math.toDegrees(Math.acos(Math.min(1.0, Math.max(-1.0, cosAngle))));
	}

	/**
	 * Formats the right ascension in hours, minutes, and seconds.
	 *
	 * @return formatted string in the format "XXh YYm ZZ.ZZs"
	 */
	public String formatRightAscension() {
		return String.format("%dh %02dm %05.2fs",
			rightAscensionHourPart(),
			rightAscensionMinutePart(),
			rightAscensionSecondPart());
	}

	/**
	 * Formats the declination in degrees, arcminutes, and arcseconds.
	 *
	 * @return formatted string in the format "±XX° YY' ZZ.ZZ""
	 */
	public String formatDeclination() {
		return String.format("%+d° %02d' %05.2f\"",
			declinationDegreePart(),
			declinationArcminutePart(),
			declinationArcsecondPart());
	}
}