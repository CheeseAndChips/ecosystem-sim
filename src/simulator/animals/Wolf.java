package simulator.animals;

import simulator.util.Vec2d;

public class Wolf extends Animal {
	private static final double KILL_RADIUS = 10.0f;
	private int kills = 0;

	public Wolf() {
		super();
	}

	public Wolf(Vec2d position, double movementSpeed) {
		super(position, movementSpeed);
	}

	public boolean tryAttacking(Animal animal) {
		if(animal.isAlive() && Animal.calculateDistance(this, animal) <= KILL_RADIUS) {
			animal.kill();
			kills++;
			return true;
		}
		return false;
	}

	@Override
	public void move(Vec2d dx) {
		super.move(dx.capMagnitude(movementSpeed));
	}

	@Override
	public String toString() {
		return "Wolf at " + getPosition().toString() + " with " + String.valueOf(kills) + " kills";
	}
}
