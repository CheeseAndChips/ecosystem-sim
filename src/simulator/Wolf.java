package simulator;

import java.awt.Color;

class Wolf extends Animal {
	private double visionRadius;

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

	public void findClosestAttackable() {
		
	}
}
