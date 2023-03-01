package simulator;

import java.awt.Dimension;

import javax.swing.JFrame;

public class EcosystemSimulator {
	public static final Dimension windowSize = new Dimension(1280, 720);
	public static final int framerate = 60;

	public static void main(String[] args) {
		Animal testEntity1 = new Animal();
		Animal testEntity2 = new Animal(new Vec2d(300, 200));
		double dist = Animal.calculateDistance(testEntity1, testEntity2);
		int iterCnt = (int)(dist / 10 * framerate + 1);
		int i = 0;
		for(i = 0; i < iterCnt; i++){		
			testEntity1.moveTowards(testEntity2, 1 / (double)framerate);
			testEntity1.println();
			testEntity1.applyDamage(0.01);
		}
		System.out.println("i = " + String.valueOf(i));
		testEntity1.applyDamage(1000);
		testEntity1.println();
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

		panel.addEntity(new Vec2d(0, 0));
		panel.addEntity(new Vec2d(200, 200));
	}
}
