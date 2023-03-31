package simulator;

import simulator.animals.*;
import simulator.util.Vec2d;

import java.util.Random;


public class EcosystemSimulator {
	public static final Random rng = new Random();

	public static void main(String args[]) {
		runTest();
	}

	public static void runTest() {
		Animal rabbit = new Rabbit(new Vec2d(200, 0), 20);
		Animal wolf = new Wolf(new Vec2d(0, 0), 10);
		while(rabbit.isAlive()) {
			rabbit.move(new Vec2d(0, 10000));
			wolf.move(rabbit.getPosition().subtract(wolf.getPosition()));
			if(((Wolf)wolf).tryAttacking(rabbit)) System.out.println("Attack successful");
			else System.out.println("Attack failed");

			System.out.println(rabbit);
			System.out.println(wolf);
		}
	}
}
