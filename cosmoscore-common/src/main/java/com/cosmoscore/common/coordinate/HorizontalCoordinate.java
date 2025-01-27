package com.cosmoscore.common.coordinate;

/**
 * Represents a point on the celestial sphere using the horizontal coordinate system.
 * This system uses azimuth (Az) and altitude (Alt) to specify positions relative to the observer's horizon.
 */
public record HorizontalCoordinate(double azimuth, double altitude) {

	/**
	 * Creates a horizontal coordinate with validation.
	 *
	 * @param azimuth  azimuth in degrees (0 to 360), measured clockwise from north
	 * @param altitude altitude in degrees (-90 to +90), measured from the horizon
	 * @throws IllegalArgumentException if coordinates are outside valid ranges
	 */
	public HorizontalCoordinate {
		if (azimuth < 0 || azimuth >= 360) {
			throw new IllegalArgumentException("Azimuth must be between 0 and 360 degrees");
		}
		if (altitude < -90 || altitude > 90) {
			throw new IllegalArgumentException("Altitude must be between -90 and +90 degrees");
		}
	}

	/**
	 * Returns the degree component of the azimuth.
	 *
	 * @return degree component (0-359)
	 */
	public int azimuthDegreePart() {
		return (int) azimuth;
	}

	/**
	 * Returns the arcminute component of the azimuth.
	 *
	 * @return arcminute component (0-59)
	 */
	public int azimuthMinutePart() {
		double absAzimuth = Math.abs(azimuth);
		return (int) ((absAzimuth - Math.abs(azimuthDegreePart())) * 60);
	}

	/**
	 * Returns the arcsecond component of the azimuth.
	 *
	 * @return arcsecond component (0-59.99)
	 */
	public double azimuthSecondPart() {
		double absAzimuth = Math.abs(azimuth);
		double minutes = (absAzimuth - Math.abs(azimuthDegreePart())) * 60;
		return (minutes - azimuthMinutePart()) * 60;
	}

	/**
	 * Returns the degree component of the altitude.
	 *
	 * @return degree component (-90 to +90)
	 */
	public int altitudeDegreePart() {
		return (int) altitude;
	}

	/**
	 * Returns the arcminute component of the altitude.
	 *
	 * @return arcminute component (0-59)
	 */
	public int altitudeMinutePart() {
		double absAltitude = Math.abs(altitude);
		return (int) ((absAltitude - Math.abs(altitudeDegreePart())) * 60);
	}

	/**
	 * Returns the arcsecond component of the altitude.
	 *
	 * @return arcsecond component (0-59.99)
	 */
	public double altitudeSecondPart() {
		double absAltitude = Math.abs(altitude);
		double minutes = (absAltitude - Math.abs(altitudeDegreePart())) * 60;
		return (minutes - altitudeMinutePart()) * 60;
	}

	/**
	 * Formats the azimuth in degrees, arcminutes, and arcseconds.
	 *
	 * @return formatted string in the format "XXX° YY' ZZ.ZZ""
	 */
	public String formatAzimuth() {
		return String.format("%d° %02d' %05.2f\"",
			azimuthDegreePart(),
			azimuthMinutePart(),
			azimuthSecondPart());
	}

	/**
	 * Formats the altitude in degrees, arcminutes, and arcseconds.
	 *
	 * @return formatted string in the format "±XX° YY' ZZ.ZZ""
	 */
	public String formatAltitude() {
		return String.format("%+d° %02d' %05.2f\"",
			altitudeDegreePart(),
			altitudeMinutePart(),
			altitudeSecondPart());
	}
}
