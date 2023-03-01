package simulator;

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

	public void capMagnitude(double maxMagnitude) {
		double magnitude = getMagnitude();
		if(magnitude > maxMagnitude) {
			x *= maxMagnitude / magnitude;
			y *= maxMagnitude / magnitude;
		}
	}

	public double distanceTo(Vec2d other) {
		return other.subtract(this).getMagnitude();
	}
}
