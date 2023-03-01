package simulator;

import java.awt.Color;

class Rabbit extends Animal {
	@Override
	public Color getDrawColor() { return Color.GREEN; }
	@Override
	public int getCircleRadius() { return 15; }

	public Rabbit() {
		
	}

	public Rabbit(Vec2d position) {
		super(position);
	}
}
