package com.cosmoscore.common.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.withPrecision;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("JulianDate class")
class JulianDateTest {

	private static final double PRECISION = 1e-10;

	@Nested
	@DisplayName("creation")
	class Creation {
		@Test
		@DisplayName("creates from LocalDateTime")
		void fromLocalDateTime() {
			LocalDateTime dateTime = LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0);

			JulianDate jd = JulianDate.fromLocalDateTime(dateTime);

			assertThat(jd.value()).isEqualTo(2451545.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("creates J2000 epoch")
		void createJ2000() {
			JulianDate j2000 = JulianDate.J2000;

			assertThat(j2000.value()).isEqualTo(2451545.0, withPrecision(PRECISION));
		}
	}

	@Nested
	@DisplayName("conversions")
	class Conversions {
		@Test
		@DisplayName("converts to LocalDateTime")
		void toLocalDateTime() {
			JulianDate jd = new JulianDate(2451545.0);

			LocalDateTime dateTime = jd.toLocalDateTime();

			assertThat(dateTime.getYear()).isEqualTo(2000);
			assertThat(dateTime.getMonth()).isEqualTo(Month.JANUARY);
			assertThat(dateTime.getDayOfMonth()).isEqualTo(1);
			assertThat(dateTime.getHour()).isEqualTo(12);
			assertThat(dateTime.getMinute()).isEqualTo(0);
		}
	}

	@Nested
	@DisplayName("calculations")
	class Calculations {
		@Test
		@DisplayName("calculates Julian centuries since J2000")
		void calculateJulianCenturies() {
			JulianDate jd = new JulianDate(2451545.0 + 36525.0);

			double centuries = jd.julianCenturies();

			assertThat(centuries).isEqualTo(1.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("adds days")
		void addDays() {
			JulianDate jd = new JulianDate(2451545.0);

			JulianDate later = jd.plusDays(10.0);

			assertThat(later.value()).isEqualTo(2451555.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("subtracts days")
		void subtractDays() {
			JulianDate jd = new JulianDate(2451545.0);

			JulianDate earlier = jd.minusDays(10.0);

			assertThat(earlier.value()).isEqualTo(2451535.0, withPrecision(PRECISION));
		}
	}

	@Nested
	@DisplayName("validation")
	class Validation {
		@Test
		@DisplayName("throws exception for dates before Julian Period start")
		void validateBeforeJulianPeriod() {
			assertThatThrownBy(() -> new JulianDate(-1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Julian Date cannot be negative");
		}

		@Test
		@DisplayName("throws exception for null LocalDateTime")
		void validateNullLocalDateTime() {
			assertThatThrownBy(() -> JulianDate.fromLocalDateTime(null))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("LocalDateTime must not be null");
		}

		@Test
		@DisplayName("handles LocalDateTime at date boundaries")
		void handleDateBoundaries() {
			LocalDateTime midnight = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
			LocalDateTime nextMidnight = LocalDateTime.of(2000, Month.JANUARY, 2, 0, 0);

			JulianDate jdMidnight = JulianDate.fromLocalDateTime(midnight);
			JulianDate jdNextMidnight = JulianDate.fromLocalDateTime(nextMidnight);

			assertThat(jdNextMidnight.value() - jdMidnight.value())
				.isEqualTo(1.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("handles high precision conversions within acceptable margin")
		void handleHighPrecision() {
			LocalDateTime preciseTime = LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0, 0, 123456789);

			JulianDate jd = JulianDate.fromLocalDateTime(preciseTime);
			LocalDateTime converted = jd.toLocalDateTime();

			long diff = Math.abs(converted.getNano() - preciseTime.getNano());
			assertThat(diff).isLessThan(20000);
		}
	}

	@Nested
	@DisplayName("edge cases")
	class EdgeCases {
		@Test
		@DisplayName("handles leap years correctly")
		void handleLeapYears() {
			LocalDateTime leapDay = LocalDateTime.of(2000, Month.FEBRUARY, 29, 12, 0);
			LocalDateTime nextDay = LocalDateTime.of(2000, Month.MARCH, 1, 12, 0);

			JulianDate jdLeapDay = JulianDate.fromLocalDateTime(leapDay);
			JulianDate jdNextDay = JulianDate.fromLocalDateTime(nextDay);

			assertThat(jdNextDay.value() - jdLeapDay.value())
				.isEqualTo(1.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("handles date far in the future")
		void handleFutureDates() {
			LocalDateTime futureDate = LocalDateTime.of(2100, Month.JANUARY, 1, 12, 0);

			JulianDate jd = JulianDate.fromLocalDateTime(futureDate);
			LocalDateTime converted = jd.toLocalDateTime();

			assertThat(converted).isEqualTo(futureDate);
		}
	}
}