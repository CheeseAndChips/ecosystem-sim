package simulator;

import java.awt.Color;

class Wolf extends Animal {
	private static final double killRadius = 10.0f;
	private int kills = 0;

	@Override
	public Color getDrawColor() { return Color.RED; }
	@Override
	public int getCircleRadius() { return 25; }
	
	public Wolf() {
		super();	
	}

	public Wolf(Vec2d position, double visionRadius) {
		super(position, visionRadius);
		this.visionRadius = visionRadius;
	}

	public void doTick() {
		Animal toAttack = verifyMaxDistance(panel.findClosestRabbit(getPosition()), this.visionRadius);
		if(toAttack == null) {
			moveWithAngle(lastMovementAngle, 1.0 / EcosystemSimulator.framerate);
		} else {
			moveTowards(toAttack, 1.0 / EcosystemSimulator.framerate);

			if(Animal.calculateDistance(this, toAttack) <= killRadius) {
				panel.registerDead(toAttack);	
				kills++;
			}
		}
	}
}
