package simulator.drawing;

import simulator.animals.*;
import simulator.Vec2d;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel implements ActionListener {
	private AnimalContainer animals;
	private Queue<Animal> toAdd;
	private Lock queueLock;

	private int framerate;
	private HashSet<Character> pressedKeyCodes;
	private Timer timer;

	// camera and camera movement
	private double cameraMovementSpeed = 75.0f;
	private double zoomSpeed = 1.0f;
	public Camera sceneCamera;

	// fps drawing
	private boolean displayFps = true;
	private long lastFrameTime;
	private final Font fpsFont = new Font("", Font.PLAIN, 24);
	private final Point fpsPosition = new Point(50, 50);

	public DrawingPanel(int framerate) {
		this.framerate = framerate;

		setFocusable(true);
		requestFocusInWindow();

		animals = new AnimalContainer();
		toAdd = new ArrayDeque<>();
		queueLock = new ReentrantLock();
		pressedKeyCodes = new HashSet<Character>();
		sceneCamera = new Camera();
		timer = new Timer(1000 / framerate, this);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				pressedKeyCodes.add(Character.toLowerCase(e.getKeyChar()));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				pressedKeyCodes.remove(Character.toLowerCase(e.getKeyChar()));
			}
		});
	}

	public void startSimulation() {
		timer.start();
	}

	public void stopSimulation() {
		timer.stop();
	}

	public void handleCameraMovement() {
		double dx = cameraMovementSpeed / framerate / sceneCamera.zoom;
		double dz = 1 + zoomSpeed / framerate;
		if(pressedKeyCodes.contains('w')) sceneCamera.y += dx;
		if(pressedKeyCodes.contains('s')) sceneCamera.y -= dx;
		if(pressedKeyCodes.contains('a')) sceneCamera.x += dx;
		if(pressedKeyCodes.contains('d')) sceneCamera.x -= dx;	

		if(pressedKeyCodes.contains('+')) sceneCamera.zoom *= dz;
		if(pressedKeyCodes.contains('-')) sceneCamera.zoom /= dz; 
	}

	public void handleAnimalAI() {
		for(Animal a : animals) {
			a.handleAI();
		}
		animals.cleanupDeadAnimals();
	}

	public void addAnimal(Animal animal) {
		queueLock.lock();
		try {
			toAdd.add(animal);
			System.out.println("Adding animal " + animal.toString());
		} finally {
			queueLock.unlock();
		}
	}

	private void moveFromQueueToContainer() {
		queueLock.lock();
		try {
			while(!toAdd.isEmpty()) {
				animals.addAnimal(toAdd.remove());
			}
		} finally {
			queueLock.unlock();
		}
	}

	public void actionPerformed(ActionEvent e) {
		moveFromQueueToContainer();
		handleCameraMovement();
		handleAnimalAI();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		Dimension dim = getSize();
		AffineTransform oldTransform = g2.getTransform();
		AffineTransform newTransform = new AffineTransform();

		newTransform.translate(dim.width / 2, dim.height / 2);
		newTransform.scale(sceneCamera.zoom, sceneCamera.zoom);
		newTransform.translate(sceneCamera.x, sceneCamera.y);
		g2.setTransform(newTransform);
		for(Animal animal : animals) {
			animal.draw(g2);
		}
		g2.setTransform(oldTransform);

		drawFps(g2);
		Toolkit.getDefaultToolkit().sync();
	}

	void drawFps(Graphics2D g) {
		if(!displayFps)
			return;

		long newTime = System.nanoTime();
		long nsSpent = newTime - lastFrameTime;
		lastFrameTime = newTime;

		float fps = (1e9f / nsSpent);
		g.setFont(fpsFont);
		g.drawString(String.format("fps: %.1f", fps), fpsPosition.x, fpsPosition.y);
	}
}
