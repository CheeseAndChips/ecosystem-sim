package simulator;

import java.util.Random;

public class Vec2d {
	public double x, y;

	public Vec2d() {
		this(0, 0);	
	}

	public Vec2d(Vec2d x) {
		this(x.x, x.y);
	}

	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return String.format("{%.2f; %.2f}", x, y);
	}

	public Vec2d subtract(Vec2d other) {
		return new Vec2d(x - other.x, y - other.y);
	}

	public double getMagnitudeSqr() {
		return x*x + y*y; 
	}

	public double getMagnitude() {
		return Math.sqrt(getMagnitudeSqr());
	}

	public Vec2d capMagnitude(double maxMagnitude) {
		double x = this.x, y = this.y;
		double magnitude = getMagnitude();
		if(magnitude > maxMagnitude) {
			x *= maxMagnitude / magnitude;
			y *= maxMagnitude / magnitude;
		}
		return new Vec2d(x, y);
	}

	public static Vec2d generateRandomUnitVector(Random rng) {
		double angle = 2 * Math.PI * rng.nextDouble();
		return new Vec2d(Math.sin(angle), Math.cos(angle));
	}

	public Vec2d toUnitVector() {
		double magnitude = getMagnitude();
		return new Vec2d(x / magnitude, y / magnitude);
	}

	public double distanceTo(Vec2d other) {
		return other.subtract(this).getMagnitude();
	}
}
