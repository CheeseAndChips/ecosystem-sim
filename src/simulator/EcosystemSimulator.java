package simulator;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

public class EcosystemSimulator {
	public static final Dimension windowSize = new Dimension(1280, 720);
	public static final int framerate = 60;
	public static final Random rng = new Random();

	public static void main(String args[]) {
		runWindow();	
	}

	public static void runWindow() {
		JFrame frame = new JFrame();
		DrawingPanel panel = new DrawingPanel(framerate);

		frame.setSize(windowSize);
		frame.setTitle("Ecosystem simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);

		for(int i = 0; i < 5; i++) {
			panel.addAnimal(new Wolf(genRandomPoint(400), 300.0));
			panel.addAnimal(new Rabbit(genRandomPoint(400), 300.0));
		}
	}

	public static Vec2d genRandomPoint(double range) {
		double x = 2*EcosystemSimulator.rng.nextDouble() - 1;
		double y = 2*EcosystemSimulator.rng.nextDouble() - 1;
		return new Vec2d(x * range, y * range);
	}
}
