package simulator;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel implements ActionListener {
	private List<Wolf> wolves;
	private List<Rabbit> rabbits;

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

		wolves = new ArrayList<>();
		rabbits = new ArrayList<>();
		pressedKeyCodes = new HashSet<Character>();
		sceneCamera = new Camera();
		timer = new Timer(1000 / framerate, this);
		timer.start();
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

	public void addWolf(Wolf animal) {
		animal.registerDrawingPanel(this);
		wolves.add(animal);
	}

	public void addRabbit(Rabbit animal) {
		animal.registerDrawingPanel(this);
		rabbits.add(animal);
	}

	public void handleAnimalAI() {
		for(Rabbit r : rabbits) {
			r.doTick();
		}
		for(Wolf w : wolves) {
			w.doTick();
		}
	}

	public void registerDead(Animal animal) {
		System.out.println("Registered as dead");
		if(animal instanceof Wolf) wolves.remove(animal);
		else if(animal instanceof Rabbit) rabbits.remove(animal);
	}

	public Wolf findClosestWolf(Vec2d point) {
		Wolf result = null;
		double dist = Double.POSITIVE_INFINITY;
		for(Wolf w : wolves) {
			double currDist = w.getPosition().distanceTo(point);
			if(dist > currDist) {
				dist = currDist;
				result = w;
			}
		}
		return result;
	}

	public Rabbit findClosestRabbit(Vec2d point) {
		Rabbit result = null;
		double dist = Double.POSITIVE_INFINITY;
		for(Rabbit r : rabbits) {
			double currDist = r.getPosition().distanceTo(point);
			if(dist > currDist) {
				dist = currDist;
				result = r;
			}
		}
		return result;
	}

	public void actionPerformed(ActionEvent e) {
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
		for(Animal ent : rabbits) {
			ent.draw(g2);
		}
		for(Animal ent : wolves) {
			ent.draw(g2);
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
