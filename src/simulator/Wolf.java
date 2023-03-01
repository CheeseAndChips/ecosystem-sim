package simulator;

import java.awt.Color;

class Wolf extends Animal {
	private double visionRadius;
	private static final double killRadius = 10.0f;

	@Override
	public Color getDrawColor() { return Color.RED; }
	@Override
	public int getCircleRadius() { return 25; }
	
	public Wolf(double visionRadius) {
		super();	
		this.visionRadius = visionRadius;
	}

	public Wolf(double visionRadius, Vec2d position) {
		super(position);
		this.visionRadius = visionRadius;
	}

	public Rabbit findClosestAttackable() {
		Rabbit found = this.panel.findClosestRabbit(getPosition());
		if(found == null)
			return null;
		double distance = Animal.calculateDistance(this, found);
		if(distance >= visionRadius)
			return null;
		return found;
	}

	public void doTick() {
		Rabbit toAttack = findClosestAttackable();
		if(toAttack == null) {
			moveWithAngle(lastMovementAngle, 1.0 / EcosystemSimulator.framerate);
		} else {
			moveTowards(toAttack, 1.0 / EcosystemSimulator.framerate);

			if(Animal.calculateDistance(this, toAttack) <= killRadius) {
				panel.registerDead(toAttack);	
			}
		}
	}
}
