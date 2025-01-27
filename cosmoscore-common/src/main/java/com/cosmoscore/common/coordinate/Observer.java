package com.cosmoscore.common.coordinate;

import java.time.LocalDateTime;

import com.cosmoscore.common.time.JulianDate;
import com.cosmoscore.common.time.LocalSiderealTime;

/**
 * Represents an observer's position on Earth's surface.
 * Used for coordinate transformations between different astronomical coordinate systems.
 */
public record Observer(double latitude, double longitude) {

	/**
	 * Creates an observer position with validation.
	 *
	 * @param latitude latitude in degrees (-90 to +90)
	 * @param longitude longitude in degrees (-180 to +180)
	 * @throws IllegalArgumentException if coordinates are outside valid ranges
	 */
	public Observer {
		if (latitude < -90 || latitude > 90) {
			throw new IllegalArgumentException("Latitude must be between -90 and +90 degrees");
		}
		if (longitude < -180 || longitude > 180) {
			throw new IllegalArgumentException("Longitude must be between -180 and +180 degrees");
		}
	}

	/**
	 * Converts equatorial coordinates to horizontal coordinates for this observer's position.
	 * Note: This is a simplified conversion that doesn't account for time.
	 * For precise calculations, use a full conversion that includes time and other corrections.
	 *
	 * @param equatorial the equatorial coordinates to convert
	 * @return the equivalent horizontal coordinates
	 */
	public HorizontalCoordinate toHorizontal(EquatorialCoordinate equatorial, LocalDateTime observationTime) {
		JulianDate jd = JulianDate.fromLocalDateTime(observationTime);
		double lst = LocalSiderealTime.calculate(jd, longitude);

		double ha = (lst * 15.0) - equatorial.rightAscension();
		if (ha < 0) ha += 360.0;

		double haRad = Math.toRadians(ha);
		double latRad = Math.toRadians(latitude);
		double decRad = Math.toRadians(equatorial.declination());

		double sinAlt = Math.sin(decRad) * Math.sin(latRad) +
			Math.cos(decRad) * Math.cos(latRad) * Math.cos(haRad);
		double altitude = Math.toDegrees(Math.asin(sinAlt));

		double cosAz = (Math.sin(decRad) - sinAlt * Math.sin(latRad)) /
			(Math.cos(Math.asin(sinAlt)) * Math.cos(latRad));
		double sinAz = -Math.cos(decRad) * Math.sin(haRad) / Math.cos(Math.asin(sinAlt));

		double azimuth = Math.toDegrees(Math.atan2(sinAz, cosAz));
		azimuth = (azimuth + 360.0) % 360.0;

		return new HorizontalCoordinate(azimuth, altitude);
	}
}
