package simulator.animals;

import simulator.Vec2d;
import simulator.EcosystemSimulator;

import java.awt.Color;

public class Rabbit extends Animal {
	@Override
	public Color getDrawColor() { return Color.GREEN; }
	@Override
	public int getCircleRadius() { return 15; }

	public Rabbit() {
		super();
	}

	public Rabbit(Vec2d position, double visionRadius) {
		super(position, visionRadius);
		this.movementSpeed *= .9; // TODO do it properly
	}

	public Animal findGoal() {
		return panel.findClosestWolf(getPosition());
	}

	public void handleGoal(Animal animal) {
		moveAwayFrom(animal, 1.0 / EcosystemSimulator.framerate);	
	}
}
