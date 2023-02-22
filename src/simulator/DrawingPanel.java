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
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.Timer;

public class DrawingPanel extends JPanel implements ActionListener {
	private List<Entity> entities;
	private Timer timer;
	private long oldTime;
	public Camera sceneCamera;
	private double cameraMovementSpeed = 50.0f;
	private double zoomSpeed = 1.0f;
	private int framerate;
	private HashSet<Character> pressedKeyCodes;

	private final Font fpsFont = new Font("", Font.PLAIN, 24);
	private final Point fpsPosition = new Point(50, 50);

	public DrawingPanel(int framerate) {
		this.framerate = framerate;

		setFocusable(true);
		requestFocusInWindow();

		entities = new ArrayList<>();
		pressedKeyCodes = new HashSet<Character>();
		sceneCamera = new Camera();
		timer = new Timer(1000 / framerate, this);
		timer.start();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				/*
				switch(e.getKeyCode()){
					case KeyEvent.VK_W:
						sceneCamera.x += cameraMovementSpeed / framerate;
						break;
					case KeyEvent.VK_S:
						sceneCamera.x -= cameraMovementSpeed / framerate;
						break;
					case KeyEvent.VK_A:
						sceneCamera.y += cameraMovementSpeed / framerate;
						break;
					case KeyEvent.VK_D:
						sceneCamera.y -= cameraMovementSpeed / framerate;
						break;
				}
				*/
				pressedKeyCodes.add(Character.toLowerCase(e.getKeyChar()));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				pressedKeyCodes.remove(Character.toLowerCase(e.getKeyChar()));
			}
		});
	}

	public void handleMovement() {
		if(pressedKeyCodes.contains('w')) sceneCamera.y -= cameraMovementSpeed / framerate;
		if(pressedKeyCodes.contains('s')) sceneCamera.y += cameraMovementSpeed / framerate;
		if(pressedKeyCodes.contains('a')) sceneCamera.x -= cameraMovementSpeed / framerate;
		if(pressedKeyCodes.contains('d')) sceneCamera.x += cameraMovementSpeed / framerate;		

		if(pressedKeyCodes.contains('+')) sceneCamera.zoom += zoomSpeed / framerate;		
		if(pressedKeyCodes.contains('-')) sceneCamera.zoom -= zoomSpeed / framerate;		
	}

	public void addEntity(Vec2d pos) {	
		entities.add(new Entity(pos));
	}

	public Entity findClosestEntity(Entity search) {
		double minDist = Double.POSITIVE_INFINITY;
		Entity closest = null;
		for(Entity e : entities){
			if(e != search) {
				double dist = e.getPosition().getDistSqr(search.getPosition());
				if(minDist > dist) {
					minDist = dist;
					closest = e;
				}
			}
		}
		return closest;
	}

	public void actionPerformed(ActionEvent e) {
		handleMovement();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		Dimension dim = getSize();
		System.out.println(Integer.toString(dim.width) + " " + Integer.toString(dim.height));
		AffineTransform oldTransform = g2.getTransform();
		g2.translate(sceneCamera.x + dim.width / 2, sceneCamera.y + dim.height / 2);
		g2.scale(sceneCamera.zoom, sceneCamera.zoom);
		for(Entity ent : entities) {
			ent.draw(g2);
		}
		g2.setTransform(oldTransform);

		drawFps(g2);
		Toolkit.getDefaultToolkit().sync();
	}

	void drawFps(Graphics2D g) {
		long newTime = System.nanoTime();
		long nsSpent = newTime - oldTime;
		oldTime = newTime;

		float fps = (1e9f / nsSpent);
		g.setFont(fpsFont);
		g.drawString(String.format("fps: %.1f", fps), fpsPosition.x, fpsPosition.y);
	}
}
