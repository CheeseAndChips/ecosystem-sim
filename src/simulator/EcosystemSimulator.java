package simulator;

import simulator.animals.*;
import simulator.exceptions.BadBreedingException;
import simulator.exceptions.WrongGoalException;
import simulator.util.Vec2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EcosystemSimulator {
	public static final Random rng = new Random();

	public static void main(String args[]) throws BadBreedingException { 
		runTest();
	}

	public static void runTest() throws BadBreedingException {
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(AnimalFactory.spawnRandomWolf(300.0, 20.0, 1e6));
		for(int i = 0; i < 10; i++) {
			animals.add(AnimalFactory.spawnRandomRabbit(300.0, 11.0, 500));
		}

		Animal closeRabbit1 = AnimalFactory.spawnRandomRabbit(1.0, 10.0, 500);
		Animal closeRabbit2 = AnimalFactory.spawnRandomRabbit(1.0, 15.0, 500);
		Animal child = AnimalFactory.breedAnimals(closeRabbit1, closeRabbit2);

		animals.add(closeRabbit1);
		animals.add(closeRabbit2);
		animals.add(child);

		System.out.println("Speed: " + child.getMovementSpeed());

		int tick = 0;
		while(animals.size() > 1) {
			for(Animal a : animals) {
				Animal goal;
				goal = a.findGoal().apply(animals);

				try {
					a.handleTick(goal);
				} catch (WrongGoalException e) {
					System.out.printf("%s: %s attacking %s\n", e.getMessage(), e.getAttacker(), e.getGoal());
				}
			}

			animals.removeIf(a -> !a.isAlive());
			tick++;
		}
		System.out.println("Ticks taken: " + tick);
	}
}
