package tests;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import dyn4j.dynamics.joint.FrictionJoint;
import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import dyn4j.world.World;
import framework.SimulationBody;
import framework.SimulationFrame;

public class BAOSimulationFrame extends SimulationFrame {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<GameObject> objects;
	
	public BAOSimulationFrame(String name, double scale) {
		super(name, scale);
		this.setMousePanningEnabled(false);
		this.setMousePickingEnabled(false);
		objects = new ArrayList<GameObject>();
	}
	
	public void AddGameObject(GameObject g) {
		objects.add(g);
	}
	
	protected void initializeWorld() {
		this.world.setGravity(World.ZERO_GRAVITY);
		
		for(GameObject g : objects) {
			g.initialize();
		}
	}
	
	protected void render(Graphics2D g, double deltaTime) {
		super.render(g, deltaTime);
		for(GameObject gObject : objects) {
			gObject.render();
		}
	}
	
	protected void handleEvents() {
		super.handleEvents();
		for(GameObject g : objects) {
			g.handleEvents();
		}
	}
}
