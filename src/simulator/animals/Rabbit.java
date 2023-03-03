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

	public void doTick() {
		Animal toAvoid = verifyMaxDistance(panel.findClosestWolf(getPosition()), this.visionRadius);
		if(toAvoid == null) {
			moveWithAngle(lastMovementAngle, 1.0 / EcosystemSimulator.framerate);
		} else {
			moveAwayFrom(toAvoid, 1.0 / EcosystemSimulator.framerate);
		}
	}
}
