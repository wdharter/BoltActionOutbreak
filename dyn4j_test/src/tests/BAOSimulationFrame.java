package tests;

import java.awt.Graphics2D;
import java.util.ArrayList;
import dyn4j.world.World;
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
	
	public void DeleteGameObject(int id) {
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).getID() == id) {
				objects.remove(i);
				i++;
			}
		}
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
