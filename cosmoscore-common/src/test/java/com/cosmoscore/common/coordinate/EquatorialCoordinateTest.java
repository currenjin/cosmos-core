package com.cosmoscore.common.coordinate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.withPrecision;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("EquatorialCoordinate class")
class EquatorialCoordinateTest {

	private static final double PRECISION = 1e-10;

	@Nested
	@DisplayName("creation")
	class Creation {
		@Test
		@DisplayName("creates with right ascension and declination in degrees")
		void createWithDegrees() {
			double rightAscension = 123.45;
			double declination = 67.89;

			EquatorialCoordinate actual = new EquatorialCoordinate(rightAscension, declination);

			assertThat(actual.rightAscension()).isEqualTo(rightAscension, withPrecision(PRECISION));
			assertThat(actual.declination()).isEqualTo(declination, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("validates right ascension range")
		void validateRightAscension() {
			assertThatThrownBy(() -> new EquatorialCoordinate(-1.0, 0))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Right ascension must be between 0 and 360 degrees");

			assertThatThrownBy(() -> new EquatorialCoordinate(360.1, 0))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Right ascension must be between 0 and 360 degrees");
		}

		@Test
		@DisplayName("validates declination range")
		void validateDeclination() {
			assertThatThrownBy(() -> new EquatorialCoordinate(0, -90.1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Declination must be between -90 and +90 degrees");

			assertThatThrownBy(() -> new EquatorialCoordinate(0, 90.1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Declination must be between -90 and +90 degrees");
		}
	}

	@Nested
	@DisplayName("conversions")
	class Conversions {
		@Test
		@DisplayName("converts between degrees and hours for right ascension")
		void convertRightAscension() {
			double raDegrees = 180.0;

			EquatorialCoordinate actual = new EquatorialCoordinate(raDegrees, 0);

			assertThat(actual.rightAscensionHours()).isEqualTo(12.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("formats coordinates as string")
		void formatAsString() {
			EquatorialCoordinate coordinate = new EquatorialCoordinate(123.45, 67.89);

			String actual = coordinate.toString();

			assertThat(actual).contains("123.45").contains("67.89");
		}
	}

	@Nested
	@DisplayName("time unit conversions")
	class TimeUnitConversions {
		@Test
		@DisplayName("converts right ascension to hours, minutes, and seconds")
		void convertRightAscensionToHMS() {
			EquatorialCoordinate coordinate = new EquatorialCoordinate(152.75, 0);

			int hours = coordinate.rightAscensionHourPart();
			int minutes = coordinate.rightAscensionMinutePart();
			double seconds = coordinate.rightAscensionSecondPart();

			assertThat(hours).isEqualTo(10);
			assertThat(minutes).isEqualTo(11);
			assertThat(seconds).isEqualTo(0.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("converts declination to degrees, arcminutes, and arcseconds")
		void convertDeclinationToDMS() {
			EquatorialCoordinate coordinate = new EquatorialCoordinate(0, 42.5);

			int degrees = coordinate.declinationDegreePart();
			int arcminutes = coordinate.declinationArcminutePart();
			double arcseconds = coordinate.declinationArcsecondPart();

			assertThat(degrees).isEqualTo(42);
			assertThat(arcminutes).isEqualTo(30);
			assertThat(arcseconds).isEqualTo(0.0, withPrecision(PRECISION));
		}
	}

	@Nested
	@DisplayName("angle calculations")
	class AngleCalculations {
		@Test
		@DisplayName("calculates angular separation between two coordinates")
		void calculateAngularSeparation() {
			EquatorialCoordinate coord1 = new EquatorialCoordinate(0, 0);
			EquatorialCoordinate coord2 = new EquatorialCoordinate(0, 90);

			double separation = coord1.angularSeparation(coord2);

			assertThat(separation).isEqualTo(90.0, withPrecision(PRECISION));
		}
	}

	@Nested
	@DisplayName("formatting")
	class Formatting {
		@Test
		@DisplayName("formats right ascension in HMS format")
		void formatRightAscensionHMS() {
			EquatorialCoordinate coordinate = new EquatorialCoordinate(152.75, 0);

			String actual = coordinate.formatRightAscension();

			assertThat(actual).isEqualTo("10h 11m 00.00s");
		}

		@Test
		@DisplayName("formats declination in DMS format")
		void formatDeclinationDMS() {
			EquatorialCoordinate coordinate = new EquatorialCoordinate(0, 42.5);

			String actual = coordinate.formatDeclination();

			assertThat(actual).isEqualTo("+42° 30' 00.00\"");
		}
	}
}