package com.cosmoscore.common.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.offset;
import static org.assertj.core.api.Assertions.withPrecision;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Observer class")
class ObserverTest {

	private static final double PRECISION = 1e-10;

	@Nested
	@DisplayName("creation")
	class Creation {
		@Test
		@DisplayName("creates with latitude and longitude")
		void createWithValues() {
			Observer observer = new Observer(37.5665, 126.9780);

			assertThat(observer.latitude()).isEqualTo(37.5665, withPrecision(PRECISION));
			assertThat(observer.longitude()).isEqualTo(126.9780, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("validates latitude range")
		void validateLatitude() {
			assertThatThrownBy(() -> new Observer(-90.1, 0))
				.isInstanceOf(IllegalArgumentException.class);

			assertThatThrownBy(() -> new Observer(90.1, 0))
				.isInstanceOf(IllegalArgumentException.class);
		}

		@Test
		@DisplayName("validates longitude range")
		void validateLongitude() {
			assertThatThrownBy(() -> new Observer(0, -180.1))
				.isInstanceOf(IllegalArgumentException.class);

			assertThatThrownBy(() -> new Observer(0, 180.1))
				.isInstanceOf(IllegalArgumentException.class);
		}
	}

	@Nested
	@DisplayName("coordinate conversion")
	class CoordinateConversion {
		@Test
		@DisplayName("converts equatorial to horizontal coordinate - Polaris")
		void convertPolaris() {
			Observer observer = new Observer(37.5665, 126.9780);
			EquatorialCoordinate polaris = new EquatorialCoordinate(37.95, 89.26);

			LocalDateTime observationTime = LocalDateTime.of(2025, 1, 1, 12, 0)
				.atZone(ZoneOffset.UTC)
				.toLocalDateTime();

			HorizontalCoordinate horizontal = observer.toHorizontal(polaris, observationTime);

			double azimuth = horizontal.azimuth();
			boolean isNearNorth = Math.abs(azimuth) <= 1.0 || Math.abs(azimuth - 360.0) <= 1.0;
			assertThat(isNearNorth).isTrue();

			assertThat(horizontal.altitude()).isCloseTo(37.5665, offset(1.0));
		}

		@Test
		@DisplayName("converts equatorial to horizontal coordinate - Celestial Equator")
		void convertCelestialEquator() {
			Observer observer = new Observer(37.5665, 126.9780);

			LocalDateTime observationTime = LocalDateTime.of(2025, 1, 1, 4, 0)
				.atZone(ZoneOffset.UTC)
				.toLocalDateTime();

			EquatorialCoordinate equatorial = new EquatorialCoordinate(180.0, 0.0);

			HorizontalCoordinate horizontal = observer.toHorizontal(equatorial, observationTime);

			assertThat(horizontal.altitude()).isCloseTo(-14.2108, offset(1.0));
		}
	}
}