package com.cosmoscore.common.time;

/**
 * Calculates Local Sidereal Time (LST).
 * LST is the hour angle of the vernal equinox at a given location and time.
 */
public class LocalSiderealTime {

	private static final double SIDEREAL_DAY = 23.9344696;
	private static final double HOURS_TO_DEGREES = 15.0;
	private static final double DEGREES_TO_HOURS = 1.0 / HOURS_TO_DEGREES;

	/**
	 * Calculates Greenwich Sidereal Time for a given Julian Date.
	 *
	 * @param jd Julian Date
	 * @return Greenwich Sidereal Time in hours
	 */
	public static double calculateGST(JulianDate jd) {
		double T = jd.julianCenturies();

		double theta = 280.46061837 +
			360.98564736629 * (jd.value() - 2451545.0) +
			0.000387933 * T * T -
			T * T * T / 38710000.0;

		theta = theta % 360.0;
		if (theta < 0) {
			theta += 360.0;
		}

		return theta * DEGREES_TO_HOURS;
	}

	/**
	 * Calculates Local Sidereal Time for a given Julian Date and longitude.
	 *
	 * @param jd Julian Date
	 * @param longitude observer's longitude in degrees (positive east)
	 * @return Local Sidereal Time in hours
	 */
	public static double calculate(JulianDate jd, double longitude) {
		double gst = calculateGST(jd);
		double lst = gst + (longitude * DEGREES_TO_HOURS);

		lst = lst % 24.0;
		if (lst < 0) {
			lst += 24.0;
		}

		return lst;
	}

	/**
	 * Formats hour angle in astronomical notation (HH:MM:SS.ss).
	 *
	 * @param hours hour angle in decimal hours
	 * @return formatted string
	 */
	public static String formatHourAngle(double hours) {
		hours = hours % 24.0;
		if (hours < 0) {
			hours += 24.0;
		}

		int h = (int) hours;
		double remainingMinutes = (hours - h) * 60.0;
		int m = (int) remainingMinutes;
		double s = (remainingMinutes - m) * 60.0;

		return String.format("%dh %02dm %05.2fs", h, m, s);
	}
}
