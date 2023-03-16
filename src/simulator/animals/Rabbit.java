package simulator.animals;

import simulator.Vec2d;

import java.awt.Color;
import java.awt.Graphics2D;

public class Rabbit extends Animal {
	public double timeSurvived = 0.0f;

	@Override
	public Color getDrawColor() { return Color.GREEN; }
	@Override
	public int getCircleRadius() { return 15; }

	public Rabbit() {
		super();
	}

	public Rabbit(Vec2d position, double visionRadius, double movementSpeed) {
		super(position, visionRadius, movementSpeed);
	}

	public Animal findGoal() {
		return container.findClosestWolf(getPosition());
	}

	@Override
	public void handleTick(double dt) {
		timeSurvived += dt;
		super.handleTick(dt);
	}

	public void handleGoal(Animal animal, double dt) {
		moveAwayFrom(animal, dt);
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		Vec2d pos = getPosition();
		String formattedTime = String.format("%.1f s", timeSurvived);
		g.drawString("Alive: " + formattedTime, (int)pos.x, (int)(pos.y - getCircleRadius() * .7));
	}
}
