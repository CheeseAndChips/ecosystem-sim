package simulator.animals;

import simulator.exceptions.WrongGoalException;
import simulator.util.Vec2d;

public abstract class Animal implements AnimalAI, Cloneable {
	private Vec2d position;
	protected double health = 100.0;
	protected double movementSpeed;

	protected double visionDistance = 500.0f;

	public double getMovementSpeed() {
		return movementSpeed;
	}

	public Animal(Vec2d position, double movementSpeed, double visionDistance) {
		this.position = position;
		this.movementSpeed = movementSpeed;
		this.visionDistance = visionDistance;
	}

	public final boolean isAlive() {
		return health > 0;
	}

	public void kill() {
		health = 0;
	}

	public void handleTick(Animal goal) throws WrongGoalException {
		if(this == goal)
			throw new WrongGoalException(this, goal);
	}

	public Vec2d getPosition() {
		return (Vec2d)position.clone();
	}

	public void setPosition(Vec2d newPosition) {
		position = (Vec2d)newPosition.clone();
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

	@Override
	public Object clone() {
		Animal result = null;
		try {
			result = (Animal)super.clone();
			result.position = (Vec2d)position.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
