package simulator;

import java.awt.Color;
import java.awt.Graphics2D;

public class Entity {
	private Vec2d position;
	private double health = 100.0;
	private double movementSpeed = 10.0;

	private static final int circleRadius = 20;
	private static final Color circleColor = Color.GRAY;

	public Entity() {
		this(new Vec2d(0, 0));	
	}

	public Entity(Vec2d position) {
		this.position = position;
	}

	public Vec2d getPosition() {
		return new Vec2d(position);
	}

	public void moveTowards(Entity other) {
		Vec2d dx = other.position.subtract(position);
		dx.capMagnitude(movementSpeed / EcosystemSimulator.framerate);
		relativeMove(dx);
	}

	public void relativeMove(Vec2d dx) {
		position.x += dx.x;
		position.y += dx.y;
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

	public String toString() {
		return String.format("{Entity at %s with health %.1f}", this.position, this.health);
	}

	public void println() {
		System.out.println(this);
	}

	public void draw(Graphics2D g) {
		Color oldc = g.getColor();
		g.setColor(circleColor);
		g.fillOval((int)(position.x - circleRadius), (int)(position.y - circleRadius), 2*circleRadius, 2*circleRadius);
		g.setColor(oldc);
	}
}
