package simulator.animals;

import java.util.List;
import java.util.function.Function;

import simulator.exceptions.WrongGoalException;
import simulator.util.Vec2d;

public class Wolf extends Animal {
	private static final double KILL_RADIUS = 10.0f;
	private int kills = 0;
	private Vec2d lastMovementDirection = null;

	public Wolf() {
		super();
	}

	public Wolf(Vec2d position, double movementSpeed) {
		super(position, movementSpeed);
	}

	public Function<List<Animal>, Animal> findGoal() {
		return lst -> {
			Animal bestAnimal = null;
			double bestDistance = Double.POSITIVE_INFINITY;
			for(Animal a : lst) {
				if(a instanceof Rabbit) {
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
		super.handleTick(goal);

		if(goal == null) {
			this.wander();
		} else {
			if(!(goal instanceof Rabbit))
				throw new WrongGoalException(this, goal);

			this.move(goal.getPosition().subtract(this.getPosition()));		
			this.tryAttacking(goal);
		}
	}

	public void wander() {
		Vec2d dx = new Vec2d(lastMovementDirection);
		dx.x *= movementSpeed;
		dx.y *= movementSpeed;
		this.move(dx);
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
		this.lastMovementDirection = dx.toUnitVector();
	}

	@Override
	public String toString() {
		return "Wolf at " + getPosition().toString() + " with " + String.valueOf(kills) + " kills";
	}
}
