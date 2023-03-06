package simulator.drawing;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import simulator.Vec2d;
import simulator.animals.Animal;
import simulator.animals.Rabbit;
import simulator.animals.Wolf;

public class AnimalContainer implements Iterable<Animal> {
	private List<Wolf> wolves;
	private List<Rabbit> rabbits;

	public AnimalContainer() {
		this.wolves = new ArrayList<>();
		this.rabbits = new ArrayList<>();
	}

	public AnimalIterator iterator() {
		return new AnimalIterator(rabbits.iterator(), wolves.iterator());
	}

	public void addAnimal(Animal animal) {
		animal.registerContainer(this);
		if(animal instanceof Wolf) wolves.add((Wolf)animal);
		else if(animal instanceof Rabbit) rabbits.add((Rabbit)animal);
		// TODO add exception
	}

	public void cleanupDeadAnimals() {
		rabbits.removeIf(a -> !a.isAlive());
		wolves.removeIf(a -> !a.isAlive());
	}

	public Wolf findClosestWolf(Vec2d point) {
		return findClosestAnimal(wolves, point);
	}

	public Rabbit findClosestRabbit(Vec2d point) {
		return findClosestAnimal(rabbits, point);
	}

	private static <T extends Animal> T findClosestAnimal(List<T> choices, Vec2d point) {
		T result = null;
		double dist = Double.POSITIVE_INFINITY;
		for(T t : choices) {
			double currDist = t.getPosition().distanceTo(point);
			if(dist > currDist) {
				dist = currDist;
				result = t;
			}
		}
		return result;
	}
}

class AnimalIterator implements Iterator<Animal> {
	private Iterator<Rabbit> rabbitIterator;
	private Iterator<Wolf> wolfIterator;

	public AnimalIterator(Iterator<Rabbit> rabbits, Iterator<Wolf> wolves) {
		this.rabbitIterator = rabbits;
		this.wolfIterator = wolves;
	}

	public boolean hasNext() {
		return rabbitIterator.hasNext() || wolfIterator.hasNext();
	}

	public Animal next() {
		if(rabbitIterator.hasNext()) return rabbitIterator.next();
		else return wolfIterator.next();
	}
}
