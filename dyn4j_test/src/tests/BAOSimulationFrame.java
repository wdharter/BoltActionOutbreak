package tests;

import java.awt.Graphics2D;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import dyn4j.world.World;
import framework.SimulationBody;
import framework.SimulationFrame;

public class BAOSimulationFrame extends SimulationFrame {
	private static final long serialVersionUID = 1L;
	
	private Vector<GameObject> objects;
	private Vector<Integer> objectIDsToDelete;

	SimulationBody anchor;
	
	private AtomicBoolean initialized = new AtomicBoolean();
	
	public BAOSimulationFrame(String name, double scale) {
		super(name, scale);
		this.setMousePanningEnabled(false);
		this.setMousePickingEnabled(false);
		objects = new Vector<GameObject>();
		objectIDsToDelete = new Vector<Integer>();
		initialized.set(false);
	}
	
	public void AddGameObject(GameObject g) {
		// If we have already initialized then initialize manually
		if(initialized.get()) {
			g.initialize();
		}
		objects.add(g);
	}
	
	public void QueueObjectToDelete(int id) {
		objectIDsToDelete.add(id);
	}
	
	private void DeleteQueuedGameObjects() {
		for(Integer i : objectIDsToDelete) {
			for(int j = 0; j < objects.size(); j++) {
				if(objects.elementAt(j).id == i) {
					objects.remove(j);
					break;
				}
			}
		}
	}
	
	protected void initializeWorld() {
		this.world.setGravity(World.ZERO_GRAVITY);
		anchor = new SimulationBody();
		anchor.translate(new Vector2(1.5, -2.0));
		anchor.setMass(MassType.INFINITE);
		this.world.addBody(anchor);
		
		for(GameObject g : objects) {
			g.initialize();
		}
		initialized.set(true);
	}
	
	protected void render(Graphics2D g, double deltaTime) {
		super.render(g, deltaTime);
		@SuppressWarnings("unchecked")
		Vector<GameObject> objectsCopy = (Vector<GameObject>) objects.clone();
		for(GameObject gObject : objectsCopy) {
			gObject.render(g, deltaTime);
		}
	}
	
	protected void handleEvents() {
		super.handleEvents();
		for(GameObject g : objects) {
			g.handleEvents();
		}
		DeleteQueuedGameObjects();
	}
	
	public SimulationBody getAnchor() {
		return anchor;
	}
}
