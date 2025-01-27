package com.cosmoscore.common.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.withPrecision;

@DisplayName("HorizontalCoordinate class")
class HorizontalCoordinateTest {

	private static final double PRECISION = 1e-10;

	@Nested
	@DisplayName("creation")
	class Creation {
		@Test
		@DisplayName("creates with azimuth and altitude")
		void createWithValues() {
			HorizontalCoordinate coordinate = new HorizontalCoordinate(180.0, 45.0);

			assertThat(coordinate.azimuth()).isEqualTo(180.0, withPrecision(PRECISION));
			assertThat(coordinate.altitude()).isEqualTo(45.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("validates azimuth range")
		void validateAzimuth() {
			assertThatThrownBy(() -> new HorizontalCoordinate(-1.0, 0))
				.isInstanceOf(IllegalArgumentException.class);

			assertThatThrownBy(() -> new HorizontalCoordinate(360.0, 0))
				.isInstanceOf(IllegalArgumentException.class);
		}

		@Test
		@DisplayName("validates altitude range")
		void validateAltitude() {
			assertThatThrownBy(() -> new HorizontalCoordinate(0, -90.1))
				.isInstanceOf(IllegalArgumentException.class);

			assertThatThrownBy(() -> new HorizontalCoordinate(0, 90.1))
				.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	@DisplayName("formatting")
	class Formatting {
		@Test
		@DisplayName("formats azimuth in degree-minute-second")
		void formatAzimuth() {
			HorizontalCoordinate coordinate = new HorizontalCoordinate(123.45, 45.0);

			assertThat(coordinate.formatAzimuth())
				.isEqualTo("123° 27' 00.00\"");
		}

		@Test
		@DisplayName("formats altitude in degree-minute-second")
		void formatAltitude() {
			HorizontalCoordinate coordinate = new HorizontalCoordinate(180.0, 45.5);

			assertThat(coordinate.formatAltitude())
				.isEqualTo("+45° 30' 00.00\"");
		}
	}

	@Nested
	@DisplayName("parts calculation")
	class PartsCalculation {
		@Test
		@DisplayName("calculates azimuth parts")
		void calculateAzimuthParts() {
			HorizontalCoordinate coordinate = new HorizontalCoordinate(123.45, 45.0);

			assertThat(coordinate.azimuthDegreePart()).isEqualTo(123);
			assertThat(coordinate.azimuthMinutePart()).isEqualTo(27);
			assertThat(coordinate.azimuthSecondPart()).isEqualTo(0.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("calculates altitude parts")
		void calculateAltitudeParts() {
			HorizontalCoordinate coordinate = new HorizontalCoordinate(180.0, 45.5);

			assertThat(coordinate.altitudeDegreePart()).isEqualTo(45);
			assertThat(coordinate.altitudeMinutePart()).isEqualTo(30);
			assertThat(coordinate.altitudeSecondPart()).isEqualTo(0.0, withPrecision(PRECISION));
		}
	}
}
