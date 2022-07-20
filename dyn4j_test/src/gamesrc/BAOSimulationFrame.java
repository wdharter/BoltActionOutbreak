package gamesrc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import dyn4j.world.World;
import framework.SimulationBody;
import framework.SimulationFrame;

public class BAOSimulationFrame extends SimulationFrame {
	private static final long serialVersionUID = 1L;
	private int level = 0;
	private Vector<GameObject> objects;
	private Vector<Integer> objectIDsToDelete;
	private Vector<GameObject> objectsToInitialize;
	private static AtomicInteger lastID = new AtomicInteger(0);
	private boolean reset = false;
	private boolean ended = false;
	SimulationBody anchor;
	private boolean won = false;
	
	private AtomicBoolean initialized = new AtomicBoolean();
	
	public BAOSimulationFrame(String name, double scale, int level) {
		super(name, scale);
		this.level = level;
		this.setMousePanningEnabled(false);
		this.setMousePickingEnabled(false);
		objects = new Vector<GameObject>();
		objectsToInitialize = new Vector<GameObject>();
		objectIDsToDelete = new Vector<Integer>();
		initialized.set(false);
	}
	
	public int GetID() {
		lastID.set(lastID.get()+1);
		return lastID.get();
	}
	
	public void AddGameObject(GameObject g) {
		// If we have already initialized then initialize manually
		if(initialized.get()) {
			objectsToInitialize.add(g);
		}else {
			objects.add(g);
		}
	}
	
	public void InitializeQueuedGameObjects() {
		if(initialized.get()) {
			for(GameObject g : objectsToInitialize) {
				g.initialize();
				objects.add(g);
			}
			objectsToInitialize.clear();
		}
	}
	
	public void QueueObjectToDelete(int id) {
		objectIDsToDelete.add(id);
	}
	
	private void DeleteQueuedGameObjects() {
		for(Integer i : objectIDsToDelete) {
			for(int j = 0; j < objects.size(); j++) {
				if(objects.elementAt(j).id == i) {
					objects.elementAt(j).destroy();
					objects.remove(j);
					break;
				}
			}
		}
		objectIDsToDelete.clear();
	}
	
	protected void initializeWorld() {
		this.world.setGravity(World.ZERO_GRAVITY);
		anchor = new SimulationBody();
		anchor.translate(new Vector2(1.5, -2.0));
		anchor.setMass(MassType.INFINITE);
		this.world.addBody(anchor);
		
		switch(level) {
			case 1:
				level1();
			case 2:
				level2();
		}
		
		for(GameObject g : objects) {
			g.initialize();
		}
		initialized.set(true);
		SoundManager Ambiance;
		try {
			Ambiance = new SoundManager(Sound.AMBIANCE, true);
			Ambiance.play();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	
	private void level1() {
		// Basic level walls
	    addWall(-41.7, 0, 2, 60);
	    addWall(41.7, 0, 2, 60);
	    addWall(0, 23, 81.28, 2);
	    addWall(0, -23, 81.28, 2);
	    
	    addWall(-35.9, 9.7, 5, 17.5);
	    addWall(-35.9, -9.7, 5, 14);
	    addWall(-26.4, -3.7, 14, 2);
	    addWall(0, -15.7, 40, 2);
	    addWall(30, -15.7, 10, 2);
	    addWall(30, -10, 10, 2);
	    addWall(30, -5, 10, 2);
	    addWall(10, -10, 20, 4);
	    addWall(12.3, -4, 15, 4);
	    addWall(-4, -8.5, 4, 7);

	    addWall(-13.5, -3, 6, 5);
	    addWall(-3, -2, 10, 2);
	    addWall(-20, -10, 24, 4);
	    
	    
	    addCircle(-10, 10, 8);
	    addCircle(-25, 10, 4);
	    addCircle(-31, 10, 1);
	    addCircle(30, 0, 1);
	    addCircle(30, 5, 1);
	    addCircle(30, 10, 1);
	    addCircle(30, 15, 1);
	    addCircle(30, 20, 1);
	    addCircle(35, 0, 1);
	    addCircle(35, 5, 1);
	    addCircle(35, 10, 1);
	    addCircle(35, 15, 1);
	    addCircle(35, 20, 1);
	    addCircle(32.5, 2.5, 0.5);
	    addCircle(32.5, 7.5, 0.5);
	    addCircle(32.5, 12.5, 0.5);
	    addCircle(32.5, 17.5, 0.5);

	    addTriangle(5, 2, new Vector2(0, 0), new Vector2(15, 0), new Vector2(0, 15));
	    addTriangle(24, 20, new Vector2(0, 0), new Vector2(-15, 0), new Vector2(0, -15));
	}
	
	private void level2() {
		// Basic level walls
	    addWall(-41.7, 0, 2, 60);
	    addWall(41.7, 0, 2, 60);
	    addWall(0, 23, 81.28, 2);
	    addWall(0, -23, 81.28, 2);
	}
	
	private void addWall(double x, double y, double w, double h) {
		SimulationBody wall = new SimulationBody();
		wall.addFixture(Geometry.createRectangle(w, h), 0.2);
		wall.setMass(MassType.INFINITE);
		wall.translate(x, y);
		wall.setColor(Color.gray);
	    this.world.addBody(wall);
	}
	
	private void addCircle(double x, double y, double r) {
		SimulationBody circle = new SimulationBody();
		circle.addFixture(Geometry.createCircle(r), 0.2);
		circle.setMass(MassType.INFINITE);
		circle.translate(x, y);
		circle.setColor(Color.gray);
	    this.world.addBody(circle);
	}
	
	private void addTriangle(double x, double y, Vector2 p1, Vector2 p2, Vector2 p3) {
		SimulationBody triangle = new SimulationBody();
		triangle.addFixture(Geometry.createTriangle(p1, p2, p3));
		triangle.setMass(MassType.INFINITE);
		triangle.translate(x, y);
		triangle.setColor(Color.gray);
	    this.world.addBody(triangle);
	}
	
	protected void render(Graphics2D g, double deltaTime) {
		super.render(g, deltaTime);
		@SuppressWarnings("unchecked")
		Vector<GameObject> objectsCopy = (Vector<GameObject>) objects.clone();
		for(GameObject gObject : objectsCopy) {
			gObject.render(g, deltaTime);
		}
		DeleteQueuedGameObjects();
		InitializeQueuedGameObjects();
		if(reset) {
			End();
		}
	}
	
	protected void handleEvents() {
		super.handleEvents();
		@SuppressWarnings("unchecked")
		Vector<GameObject> objectsCopy = (Vector<GameObject>) objects.clone();
		for(GameObject g : objectsCopy) {
			g.handleEvents();
		}
	}
	
	public SimulationBody getAnchor() {
		return anchor;
	}
	
	// Toggles a boolean so that at the end of the next render loop, we call Reset()
	public void Endgame(boolean won) {
		reset = true;
		this.won = won;
	}
	private void End() {
		if(!ended) {
			ActionStateHandler.gameEnded = true;
			new EndScreenGameObject(GetID(), this, "endscreen", won);
			ended = true;
		}
	}
}
