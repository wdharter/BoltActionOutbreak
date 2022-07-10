package tests;

import java.awt.Graphics2D;
import java.util.ArrayList;
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

	SimulationBody anchor;
	
	private AtomicBoolean initialized = new AtomicBoolean();
	
	public BAOSimulationFrame(String name, double scale) {
		super(name, scale);
		this.setMousePanningEnabled(false);
		this.setMousePickingEnabled(false);
		objects = new Vector<GameObject>();
		initialized.set(false);
	}
	
	public void AddGameObject(GameObject g) {
		// If we have already initialized then initialize manually
		if(initialized.get()) {
			g.initialize();
		}
		objects.add(g);
	}
	
	public void DeleteGameObject(int id) {
		//for(int i = 0; i < objects.size(); i++) {
			//if(objects.get(i).getID() == id) {
				//objects.remove(i);
				//i++;
			//}
		//}
		for(GameObject g : objects) {
			System.out.println(g.id);
			if(g.id == id) {
				g.active.set(false);
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
		for(GameObject gObject : objects) {
			if(gObject.active.get())
				gObject.render(g, deltaTime);
		}
	}
	
	protected void handleEvents() {
		super.handleEvents();
		for(GameObject g : objects) {
			if(g.active.get())
				g.handleEvents();
		}
		
	}
	
	public SimulationBody getAnchor() {
		return anchor;
	}
}
