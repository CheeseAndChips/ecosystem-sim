package simulator;

import java.awt.Dimension;

import javax.swing.JFrame;

public class EcosystemSimulator{
	public static final Dimension windowSize = new Dimension(1280, 720);
	public static final int framerate = 60;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DrawingPanel panel = new DrawingPanel(framerate);

		frame.setSize(windowSize);
		frame.setTitle("Ecosystem simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);

		panel.addEntity(new Vec2d(150, 150));
	}
}
