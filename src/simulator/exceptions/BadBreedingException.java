package simulator.exceptions;

import simulator.animals.Animal;

public class BadBreedingException extends EcosystemSimulationException {
	private Animal parentA, parentB;

	public Animal getParentA() {
		return parentA;
	}

	public Animal getParentB() {
		return parentB;
	}

	public BadBreedingException(Animal a, Animal b) {
		super("Breeding two different kinds of animals");
		this.parentA = a;
		this.parentB = b;
	}
}
