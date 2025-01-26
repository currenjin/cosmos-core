package com.cosmoscore.common.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.JulianFields;
import java.util.Objects;

/**
 * Represents a Julian Date, which is the number of days that have elapsed since
 * the beginning of the Julian Period (noon on January 1, 4713 BCE on the Julian calendar).
 */
public record JulianDate(double value) {

	/**
	 * The Julian Date for the J2000 epoch (2000 January 1, 12:00 TT)
	 */
	public static final JulianDate J2000 = new JulianDate(2451545.0);

	/**
	 * Constructor with validation
	 */
	public JulianDate {
		if (value < 0) {
			throw new IllegalArgumentException("Julian Date cannot be negative");
		}
	}

	/**
	 * Creates a JulianDate from a LocalDateTime
	 *
	 * @param dateTime the LocalDateTime to convert
	 * @return the corresponding JulianDate
	 * @throws NullPointerException if dateTime is null
	 */
	public static JulianDate fromLocalDateTime(LocalDateTime dateTime) {
		Objects.requireNonNull(dateTime, "LocalDateTime must not be null");

		long julianDay = dateTime.getLong(JulianFields.JULIAN_DAY);

		double fractionalDay = (dateTime.getHour() - 12 +
			dateTime.getMinute() / 60.0 +
			dateTime.getSecond() / 3600.0 +
			dateTime.getNano() / 3600e9) / 24.0;

		return new JulianDate(julianDay + fractionalDay);
	}

	/**
	 * Converts this JulianDate to a LocalDateTime
	 *
	 * @return the corresponding LocalDateTime
	 */
	public LocalDateTime toLocalDateTime() {
		long jdn = (long) value;

		double fractionalDay = value - jdn;

		double hours = (fractionalDay * 24.0) + 12.0;

		int hour = (int) hours;
		double fractionalHour = hours - hour;

		int minute = (int) (fractionalHour * 60);
		double fractionalMinute = (fractionalHour * 60) - minute;

		int second = (int) (fractionalMinute * 60);
		int nano = (int) ((fractionalMinute * 60 - second) * 1e9);

		LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
			(jdn - 2440588) * 86400,
			0,
			ZoneOffset.UTC
		);

		return dateTime
			.withHour(hour)
			.withMinute(minute)
			.withSecond(second)
			.withNano(nano);
	}

	/**
	 * Calculate Julian centuries since J2000.0
	 *
	 * @return number of Julian centuries since J2000.0
	 */
	public double julianCenturies() {
		return (value - J2000.value()) / 36525.0;
	}

	/**
	 * Returns a new JulianDate that is a specified number of days after this one
	 *
	 * @param days number of days to add
	 * @return new JulianDate
	 */
	public JulianDate plusDays(double days) {
		return new JulianDate(value + days);
	}

	/**
	 * Returns a new JulianDate that is a specified number of days before this one
	 *
	 * @param days number of days to subtract
	 * @return new JulianDate
	 */
	public JulianDate minusDays(double days) {
		return new JulianDate(value - days);
	}
}
