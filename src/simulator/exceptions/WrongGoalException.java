package simulator.exceptions;

import simulator.animals.Animal;

public class WrongGoalException extends EcosystemSimulationException {
	private Animal attacker, goal;

	public Animal getAttacker() {
		return attacker;
	}

	public Animal getGoal() {
		return goal;
	}

	public WrongGoalException(Animal attacker, Animal goal) {
		super("Wrong goal");
		this.attacker = attacker;
		this.goal = goal;
	}
}
