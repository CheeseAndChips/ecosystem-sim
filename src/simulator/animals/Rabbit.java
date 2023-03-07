package simulator.animals;

import simulator.Vec2d;

import java.awt.Color;

public class Rabbit extends Animal {
	@Override
	public Color getDrawColor() { return Color.GREEN; }
	@Override
	public int getCircleRadius() { return 15; }

	public Rabbit() {
		super();
	}

	public Rabbit(Vec2d position, double visionRadius, double movementSpeed) {
		super(position, visionRadius, movementSpeed);
	}

	public Animal findGoal() {
		return container.findClosestWolf(getPosition());
	}

	public void handleGoal(Animal animal, double dt) {
		moveAwayFrom(animal, dt);	
	}
}
