package simulator.animals;

import simulator.util.Vec2d;

public class Rabbit extends Animal {
	private int timeSinceJump = 0;
	private static int JUMP_COOLDOWN = 5;

	public Rabbit() {
		super();
	}

	public Rabbit(Vec2d position, double movementSpeed) {
		super(position, movementSpeed);
	}

	@Override
	public void move(Vec2d dx) {
		if(this.timeSinceJump > 0) {
			this.timeSinceJump--;
		} else {
			this.timeSinceJump = JUMP_COOLDOWN;
			super.move(dx.capMagnitude(movementSpeed));
		}
	}

	@Override
	public String toString() {
		return "Rabbit at " + getPosition().toString();
	}
}
