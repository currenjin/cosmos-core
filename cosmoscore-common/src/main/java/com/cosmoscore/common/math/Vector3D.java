package com.cosmoscore.common.math;

/**
 * AN immutable 3D vector class for astronomical calculations.
 */
public record Vector3D(double x, double y, double z) {

	/**
	 * Creates a zero vector (0, 0, 0)
	 */
	public static Vector3D zero() {
		return new Vector3D(0, 0, 0);
	}

	/**
	 * Adds this vector with another vector
	 */
	public Vector3D add(Vector3D other) {
		return new Vector3D(
			this.x + other.x(),
			this.y + other.y(),
			this.z + other.z()
		);
	}

	/**
	 * Subtracts another vector from this vector
	 */
	public Vector3D subtract(Vector3D vector) {
		return new Vector3D(
			this.x - vector.x(),
			this.y - vector.y(),
			this.z - vector.z()
		);
	}

	/**
	 * Multiplies this vector by a scalar
	 */
	public Vector3D multiply(double scalar) {
		return new Vector3D(
			this.x * scalar,
			this.y * scalar,
			this.z * scalar
		);
	}

	/**
	 * Calculates the magnitude (length) of this vector
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns a normalized version of this vector (unit vector)
	 */
	public Vector3D normalize() {
		double mag = magnitude();
		if (mag == 0.0) {
			throw new IllegalStateException("Cannot normalize a zero vector");
		}
		return this.multiply(1.0 / mag);
	}

	/**
	 * Calculates the dot product with another vector
	 */
	public double dot(Vector3D other) {
		return this.x * other.x +
			   this.y * other.y +
			   this.z * other.z;
	}

	/**
	 * Calculates the cross product with another vector
	 */
	public Vector3D cross(Vector3D other) {
		return new Vector3D(
			this.y * other.z - this.z * other.y,
			this.z * other.x - this.x * other.z,
			this.x * other.y - this.y * other.x
		);
	}
}
