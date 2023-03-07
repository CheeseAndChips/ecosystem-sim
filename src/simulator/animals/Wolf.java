package simulator.animals;

import simulator.Vec2d;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wolf extends Animal {
	private static final double killRadius = 10.0f;
	private int kills = 0;

	@Override
	public Color getDrawColor() { return Color.RED; }
	@Override
	public int getCircleRadius() { return 25; }
	
	public Wolf() {
		super();
	}

	public Wolf(Vec2d position, double visionRadius, double movementSpeed) {
		super(position, visionRadius, movementSpeed);
	}

	public Animal findGoal() {
		return container.findClosestRabbit(this.getPosition());
	}
	
	public void handleGoal(Animal animal, double dt) {
		moveTowards(animal, dt);	

		if(Animal.calculateDistance(this, animal) <= killRadius) {
			animal.kill();
			kills++;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		Vec2d pos = getPosition();
		g.drawString("Kills: " + String.valueOf(kills), (int)pos.x, (int)(pos.y - getCircleRadius() * .7));
	}
}
