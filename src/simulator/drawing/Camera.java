package simulator.drawing;

public class Camera {
	public double x, y;
	public double zoom;

	public Camera() {
		this(0, 0, 1);	
	}

	public Camera(double x, double y, double zoom) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
	}
}
