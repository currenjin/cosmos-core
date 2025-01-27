package com.cosmoscore.common.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.withPrecision;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LocalSiderealTime class")
class LocalSiderealTimeTest {

	private static final double PRECISION = 1e-10;

	@Nested
	@DisplayName("calculation")
	class Calculation {
		@Test
		@DisplayName("calculates Greenwich Sidereal Time")
		void calculateGST() {
			LocalDateTime dateTime = LocalDateTime.of(2000, 1, 1, 12, 0);
			JulianDate jd = JulianDate.fromLocalDateTime(dateTime);

			double gst = LocalSiderealTime.calculateGST(jd);

			assertThat(gst).isEqualTo(18.697374558, withPrecision(1e-9));
		}

		@Test
		@DisplayName("calculates Local Sidereal Time")
		void calculateLST() {
			LocalDateTime dateTime = LocalDateTime.of(2000, 1, 1, 12, 0)
				.atZone(ZoneOffset.UTC)
				.toLocalDateTime();
			JulianDate jd = JulianDate.fromLocalDateTime(dateTime);
			double longitude = -77.0365; // Washington DC

			double lst = LocalSiderealTime.calculate(jd, longitude);

			assertThat(lst).isEqualTo(13.561607891333333, withPrecision(0.001));
		}
	}

	@Nested
	@DisplayName("format")
	class Format {
		@Test
		@DisplayName("formats hour angle")
		void formatHourAngle() {
			double hours = 12.5;

			String formatted = LocalSiderealTime.formatHourAngle(hours);

			assertThat(formatted).isEqualTo("12h 30m 00.00s");
		}
	}
}
