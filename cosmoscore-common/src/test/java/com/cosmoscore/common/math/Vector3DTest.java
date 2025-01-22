package com.cosmoscore.common.math;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Vector3D class")
class Vector3DTest {

	@Nested
	@DisplayName("creation")
	class Creation {
		@Test
		@DisplayName("creates a vector with given coordinates")
		void createVector() {
			double x = 1.0;
			double y = 2.0;
			double z = 3.0;

			Vector3D vector = new Vector3D(x, y, z);

			assertThat(vector.x()).isEqualTo(x);
			assertThat(vector.y()).isEqualTo(y);
			assertThat(vector.z()).isEqualTo(z);
		}

		@Test
		@DisplayName("creates a zero vector")
		void createZeroVector() {
			Vector3D zero = Vector3D.zero();

			assertThat(zero.x()).isZero();
			assertThat(zero.y()).isZero();
			assertThat(zero.z()).isZero();
		}
	}

	@Nested
	@DisplayName("basic operations")
	class BasicOperations {
		@Test
		@DisplayName("adds two vectors")
		void addVectors() {
			Vector3D vector1 = new Vector3D(1.0, 2.0, 3.0);
			Vector3D vector2 = new Vector3D(2.0, 3.0, 4.0);

			Vector3D actual = vector1.add(vector2);

			assertThat(actual.x()).isEqualTo(3.0);
			assertThat(actual.y()).isEqualTo(5.0);
			assertThat(actual.z()).isEqualTo(7.0);
		}

		@Test
		@DisplayName("subtracts two vectors")
		void subtractVectors() {
			Vector3D vector1 = new Vector3D(3.0, 5.0, 7.0);
			Vector3D vector2 = new Vector3D(1.0, 2.0, 3.0);

			Vector3D result = vector1.subtract(vector2);

			assertThat(result.x()).isEqualTo(2.0);
			assertThat(result.y()).isEqualTo(3.0);
			assertThat(result.z()).isEqualTo(4.0);
		}

		@Test
		@DisplayName("multiplies vector by scalar")
		void multiplyByScalar() {
			Vector3D vector = new Vector3D(1.0, 2.0, 3.0);
			double scalar = 2.0;

			Vector3D result = vector.multiply(scalar);

			assertThat(result.x()).isEqualTo(2.0);
			assertThat(result.y()).isEqualTo(4.0);
			assertThat(result.z()).isEqualTo(6.0);
		}
	}

	@Nested
	@DisplayName("vector operations")
	class VectorOperations {
		private static final Double PRECISION = 1e-10;

		@Test
		@DisplayName("calculates magnitude")
		void magnitude() {
			Vector3D vector = new Vector3D(3.0, 4.0, 0.0);

			double magnitude = vector.magnitude();

			assertThat(magnitude).isEqualTo(5.0);
		}

		@Test
		@DisplayName("normalizes vector")
		void normalize() {
			Vector3D vector = new Vector3D(3.0, 4.0, 0.0);

			Vector3D normalized = vector.normalize();

			assertThat(normalized.x()).isEqualTo(0.6, withPrecision(PRECISION));
			assertThat(normalized.y()).isEqualTo(0.8, withPrecision(PRECISION));
			assertThat(normalized.z()).isEqualTo(0.0, withPrecision(PRECISION));
		}

		@Test
		@DisplayName("calculates dot product")
		void dotProduct() {
			Vector3D vector1 = new Vector3D(1.0, 2.0, 3.0);
			Vector3D vector2 = new Vector3D(4.0, 5.0, 6.0);

			double actual = vector1.dot(vector2);

			assertThat(actual).isEqualTo(32.0);
		}

		@Test
		@DisplayName("calculates cross product")
		void crossProduct() {
			Vector3D vector1 = new Vector3D(1.0, 0.0, 0.0);
			Vector3D vector2 = new Vector3D(0.0, 1.0, 0.0);

			Vector3D result = vector1.cross(vector2);

			assertThat(result.x()).isEqualTo(0.0);
			assertThat(result.y()).isEqualTo(0.0);
			assertThat(result.z()).isEqualTo(1.0);
		}
	}
}