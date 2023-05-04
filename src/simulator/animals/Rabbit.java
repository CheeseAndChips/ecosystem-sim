package simulator.animals;

import java.util.List;
import java.util.function.Function;

import simulator.EcosystemSimulator;
import simulator.exceptions.WrongGoalException;
import simulator.util.Vec2d;

public class Rabbit extends Animal {
	private int timeSinceJump = 0;
	private static int JUMP_COOLDOWN = 5;

	public Rabbit(Vec2d position, double movementSpeed, double visionDistance) {
		super(position, movementSpeed, visionDistance);
	}

	public Function<List<Animal>, Animal> findGoal() {
		return lst -> {
			Animal bestAnimal = null;
			double bestDistance = Double.POSITIVE_INFINITY;
			for(Animal a : lst) {
				if(a instanceof Wolf) {
					double dist = Animal.calculateDistance(a, this);
					if(dist < this.visionDistance && dist < bestDistance) {
						bestAnimal = a;
						bestDistance = dist;
					}
				}
			}
			return bestAnimal;
		};
	}

	public void handleTick(Animal goal) throws WrongGoalException {
		if(goal == null) {
			this.wander();
		} else {
			if(!(goal instanceof Wolf))
				throw new WrongGoalException(this, goal);
			this.move(this.getPosition().subtract(goal.getPosition()));		
		}
	}

	public void wander() {
		Vec2d dx = Vec2d.generateRandomUnitVector(EcosystemSimulator.rng);
		dx.x *= movementSpeed;
		dx.y *= movementSpeed;
		this.move(dx);
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
