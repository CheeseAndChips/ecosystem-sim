package simulator.animals;

import simulator.Vec2d;
import simulator.EcosystemSimulator;
import simulator.drawing.AnimalContainer;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Animal {
	private Vec2d position;
	protected double visionRadius;
	protected double health = 100.0;
	protected double movementSpeed;
	protected AnimalContainer container;

	protected Vec2d lastMovementDirection = Vec2d.generateRandomUnitVector(EcosystemSimulator.rng);

	// for painting 
	public Color getDrawColor() { return Color.GRAY; }
	public int getCircleRadius() { return 20; }

	public Animal() {
		this(new Vec2d(0, 0), 300.0f, 50.0f);	
	}

	public Animal(Vec2d position, double visionRadius, double movementSpeed) {
		this.position = position;
		this.visionRadius = visionRadius;
		this.movementSpeed = movementSpeed;
	}

	public abstract Animal findGoal();
	public abstract void handleGoal(Animal animal, double dt);

	public void registerContainer(AnimalContainer container) {
		this.container = container;
	}

	public boolean isAlive() {
		return health > 0;
	}

	public void kill() {
		health = 0;
	}

	public void wander(double dt) {
		relativeMove(
			new Vec2d(
				lastMovementDirection.x * movementSpeed * dt,
				lastMovementDirection.y * movementSpeed * dt
			)
		);
	}
	
	public void handleAI(double dt) {
		Animal goal = findGoal();
		if(goal != null && goal.getPosition().distanceTo(this.getPosition()) <= visionRadius) {
			handleGoal(goal, dt);
		} else {
			wander(dt);
		}
	}

	public final Vec2d getPosition() {
		return new Vec2d(position);
	}

	public final void setPosition(Vec2d newPosition) {
		position = new Vec2d(newPosition);
	}

	public void moveTowards(Animal other, double dt) {
		moveTowards(other.position, dt);
	}

	public void moveTowards(Vec2d point, double dt) {
		Vec2d dx = point.subtract(position);
		dx.capMagnitude(movementSpeed * dt);
		relativeMove(dx);
	}

	public void moveAwayFrom(Animal other, double dt) {
		moveAwayFrom(other.position, dt);
	}

	public void moveAwayFrom(Vec2d point, double dt) {
		Vec2d dx = position.subtract(point);
		dx.capMagnitude(movementSpeed * dt);
		relativeMove(dx);
	}

	public final void relativeMove(Vec2d dx) {
		position.x += dx.x;
		position.y += dx.y;
		lastMovementDirection = dx.toUnitVector();
	}

	public double getHealth() {
		return health;
	}

	public void applyDamage(double damage) {
		health -= damage;
		if(health < 0) {
			health = 0;
		}
	}

	public void draw(Graphics2D g) {
		Color oldc = g.getColor();
		int circleRadius = getCircleRadius();
		g.setColor(getDrawColor());
		g.fillOval((int)(position.x - circleRadius), (int)(position.y - circleRadius), 2*circleRadius, 2*circleRadius);
		g.setColor(oldc);
	}

	public static double calculateDistance(Animal a, Animal b) {
		return a.getPosition().distanceTo(b.getPosition());
	}

	@Override
	public String toString() {
		return String.format("{%s at position %s}", this.getClass().getSimpleName(), this.position.toString());
	}
}
