package simulator.animals;

import simulator.Vec2d;
import simulator.EcosystemSimulator;
import simulator.drawing.DrawingPanel;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Animal {
	private Vec2d position;
	protected double visionRadius;
	protected double health = 100.0;
	protected double movementSpeed = 50.0;
	protected DrawingPanel panel;

	protected double lastMovementAngle = EcosystemSimulator.rng.nextDouble() * 2 * Math.PI;

	// for painting 
	public Color getDrawColor() { return Color.GRAY; }
	public int getCircleRadius() { return 20; }

	public Animal() {
		this(new Vec2d(0, 0), 300.0f);	
	}

	public Animal(Vec2d position, double visionRadius) {
		this.position = position;
		this.visionRadius = visionRadius;
	}

	public void registerDrawingPanel(DrawingPanel panel) {
		this.panel = panel;
	}

	public abstract void doTick();

	public Vec2d getPosition() {
		return new Vec2d(position);
	}

	public void setPosition(Vec2d newPosition) {
		position = new Vec2d(newPosition);
	}

	public void moveTowards(Animal other, double dt) {
		moveTowards(other.position, dt);
	}

	public void moveTowards(Vec2d point, double dt) {
		Vec2d dx = point.subtract(position);
		dx.capMagnitude(movementSpeed * dt);
		relativeMove(dx);
	}

	public void moveAwayFrom(Animal other, double dt) {
		moveAwayFrom(other.position, dt);
	}

	public void moveAwayFrom(Vec2d point, double dt) {
		Vec2d dx = position.subtract(point);
		dx.capMagnitude(movementSpeed * dt);
		relativeMove(dx);
	}

	public void moveWithAngle(double ang, double dt) {
		Vec2d dx = new Vec2d(
			Math.cos(ang) * movementSpeed * dt,
			Math.sin(ang) * movementSpeed * dt
		);
		relativeMove(dx);
	}

	public final void relativeMove(Vec2d dx) {
		position.x += dx.x;
		position.y += dx.y;
		lastMovementAngle = Math.atan(dx.y / dx.x);
	}

	public double getHealth() {
		return health;
	}

	public void applyDamage(double damage) {
		health -= damage;
		if(health < 0) {
			health = 0;
		}
	}

	public void draw(Graphics2D g) {
		Color oldc = g.getColor();
		int circleRadius = getCircleRadius();
		g.setColor(getDrawColor());
		g.fillOval((int)(position.x - circleRadius), (int)(position.y - circleRadius), 2*circleRadius, 2*circleRadius);
		g.setColor(oldc);
	}

	public static double calculateDistance(Animal a, Animal b) {
		return a.getPosition().distanceTo(b.getPosition());
	}

	protected Animal verifyMaxDistance(Animal other, double maxDistance) {
		if(other == null) return null;
		if(Animal.calculateDistance(this, other) > maxDistance) return null;
		return other;
	}

	@Override
	public String toString() {
		return String.format("{%s at position %s}", this.getClass().getSimpleName(), this.position.toString());
	}
}
