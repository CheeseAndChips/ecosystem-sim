package simulator.animals;

import simulator.EcosystemSimulator;
import simulator.exceptions.BadBreedingException;
import simulator.util.Vec2d;
import simulator.util.RandomUtil;

public class AnimalFactory {
	public static Wolf spawnRandomWolf(double range, double movementSpeed, double visionDistance) {
		double x = RandomUtil.generateDoubleInRange(-range, range, EcosystemSimulator.rng);
		double y = RandomUtil.generateDoubleInRange(-range, range, EcosystemSimulator.rng);
		return new Wolf(new Vec2d(x, y), movementSpeed, visionDistance);
	}

	public static Rabbit spawnRandomRabbit(double range, double movementSpeed, double visionDistance) {
		double x = RandomUtil.generateDoubleInRange(-range, range, EcosystemSimulator.rng);
		double y = RandomUtil.generateDoubleInRange(-range, range, EcosystemSimulator.rng);
		return new Rabbit(new Vec2d(x, y), movementSpeed, visionDistance);
	}

	public static Animal breedAnimals(Animal a, Animal b) throws BadBreedingException {
		if(a.getClass() != b.getClass()) {
			throw new BadBreedingException(a, b);
		}

		Animal animalToClone = (EcosystemSimulator.rng.nextBoolean()) ? a : b;
		return (Animal)animalToClone.clone();
	}
}
