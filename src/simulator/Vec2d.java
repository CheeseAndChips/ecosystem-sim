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
		return String.format("{%f %f}", x, y);
	}
	
	public double getDistSqr(Vec2d other) {
		double dx = x - other.x;
		double dy = y - other.y;
		return dx*dx + dy*dy;
	}

	public double getDist(Vec2d other) {
		return Math.sqrt(getDistSqr(other));
	}
}
