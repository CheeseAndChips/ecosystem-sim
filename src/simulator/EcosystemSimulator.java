package simulator;

import simulator.animals.*;
import simulator.exceptions.WrongGoalException;
import simulator.util.Vec2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EcosystemSimulator {
	public static final Random rng = new Random();

	public static void main(String args[]) {
		runTest();
	}

	public static void runTest() {
		List<Animal> animals = new ArrayList<Animal>() {{
			add(new Rabbit(new Vec2d(200, 0), 20));
			add(new Wolf(new Vec2d(0, 0), 10));
			add(new Rabbit(new Vec2d(300, 10), 20));
		}};
		while(animals.size() > 1) {
			for(Animal a : animals) {
				Animal goal;
				if(rng.nextFloat() < .2f)
					goal = animals.get(0);
				else
					goal = a.findGoal().apply(animals);

				try {
					a.handleTick(goal);
				} catch (WrongGoalException e) {
					System.out.printf("%s: %s attacking %s\n", e.getMessage(), e.getAttacker(), e.getGoal());
				}
			}

			animals.removeIf(a -> !a.isAlive());

			System.out.println("Alive animals:");
			for(Animal a : animals) {
				System.out.println(a);
			}
		}
	}
}
