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

		panel.addWolf(new Wolf(300.0f, new Vec2d(0, 0)));
		panel.addRabbit(new Rabbit(new Vec2d(300, 200)));
	}
}
