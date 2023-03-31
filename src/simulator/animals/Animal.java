package simulator.animals;

import simulator.util.Vec2d;

public class Animal {
	private Vec2d position;
	protected double health = 100.0;
	protected double movementSpeed;

	public Animal() {
		this(new Vec2d(0, 0), 50.0f);	
	}

	public Animal(Vec2d position, double movementSpeed) {
		this.position = position;
		this.movementSpeed = movementSpeed;
	}

	public final boolean isAlive() {
		return health > 0;
	}

	public void kill() {
		health = 0;
	}

	public Vec2d getPosition() {
		return new Vec2d(position);
	}

	public void setPosition(Vec2d newPosition) {
		position = new Vec2d(newPosition);
	}

	public void move(Vec2d dx) {
		position.x += dx.x;
		position.y += dx.y;
	}

	public final double getHealth() {
		return health;
	}

	public void applyDamage(double damage) {
		health -= damage;
		if(health < 0) {
			health = 0;
		}
	}

	public static double calculateDistance(Animal a, Animal b) {
		return a.getPosition().distanceTo(b.getPosition());
	}
}
