package simulator;

import java.awt.Graphics2D;

public class Entity {
	private Vec2d position;
	private double health = 100.0;

	public Entity() {
		this(new Vec2d(0, 0));	
	}

	public Entity(Vec2d position) {
		this.position = position;
	}

	public Vec2d getPosition() {
		return new Vec2d(position);
	}

	public void applyDamage(double damage) {
		health -= damage;
		if(health < 0) {
			health = 0;
		}
	}

	public String toString() {
		return String.format("Entity at %s", this.position);
	}

	public void draw(Graphics2D g) {
		g.fillOval((int)position.x, (int)position.y, 25, 25);
	}
}
