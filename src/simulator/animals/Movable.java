package simulator.animals;

import simulator.util.Vec2d;

public interface Movable {
	Vec2d position = null;	
	public void move(Vec2d dx);
}
