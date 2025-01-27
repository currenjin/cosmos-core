package com.cosmoscore.common.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

	@Test
	@DisplayName("converts equatorial to horizontal coordinates")
	void convertEquatorialToHorizontal() {
		Observer observer = new Observer(37.5665, 126.9780);
		EquatorialCoordinate equatorial = new EquatorialCoordinate(37.95, 89.26);
		LocalDateTime observationTime = LocalDateTime.of(2025, Month.JANUARY, 1, 12, 0)
			.atZone(ZoneOffset.UTC)
			.toLocalDateTime();

		HorizontalCoordinate horizontal = observer.toHorizontal(equatorial, observationTime);

		double azimuth = horizontal.azimuth();
		boolean isNearNorth = azimuth > 359 || azimuth < 1;
		assertThat(isNearNorth).isTrue();
		assertThat(horizontal.altitude()).isEqualTo(37.5, withPrecision(1.0));
	}
}