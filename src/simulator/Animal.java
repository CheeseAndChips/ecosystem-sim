package simulator;

import java.awt.Color;
import java.awt.Graphics2D;

public class Animal {
	private Vec2d position;
	private double health = 100.0;
	private double movementSpeed = 10.0;

	// for painting 
	public Color getDrawColor() { return Color.GRAY; }
	public int getCircleRadius() { return 20; }

	public Animal() {
		this(new Vec2d(0, 0));	
	}

	public Animal(Vec2d position) {
		this.position = position;
	}

	public Vec2d getPosition() {
		return new Vec2d(position);
	}

	public void setPosition(Vec2d newPosition) {
		position = new Vec2d(newPosition);
	}

	public void moveTowards(Animal other, double dt) {
		moveTowards(other.position, dt);
	}

	public void moveTowards(Vec2d destPosition, double dt) {
		Vec2d dx = destPosition.subtract(position);
		dx.capMagnitude(movementSpeed * dt);
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

	public void draw(Graphics2D g) {
		Color oldc = g.getColor();
		int circleRadius = getCircleRadius();
		g.setColor(getDrawColor());
		g.fillOval((int)(position.x - circleRadius), (int)(position.y - circleRadius), 2*circleRadius, 2*circleRadius);
		g.setColor(oldc);
	}

	public static double calculateDistance(Animal a, Animal b) {
		return a.getPosition().subtract(b.getPosition()).getMagnitude();
	}
}
