package tests;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
	SimulationBody anchor;
	
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
	}
	
	private void level1() {
		// Basic level walls
	    addWall(-41.7, 0, 2, 60);
	    addWall(41.7, 0, 2, 60);
	    addWall(0, 23, 81.28, 2);
	    addWall(0, -23, 81.28, 2);
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
}
