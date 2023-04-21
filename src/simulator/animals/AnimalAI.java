package simulator.animals;

import java.util.List;
import java.util.function.Function;

import simulator.exceptions.WrongGoalException;

public interface AnimalAI extends Movable {
	public void handleTick(Animal goal) throws WrongGoalException;
	public Function<List<Animal>, Animal> findGoal();
	public void wander();
}
