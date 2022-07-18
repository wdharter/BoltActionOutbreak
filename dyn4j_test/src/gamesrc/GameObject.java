package gamesrc;

import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class GameObject {
	protected BAOSimulationFrame frame;
	protected int id;
	private String name;
	public AtomicBoolean active = new AtomicBoolean();
	public boolean initialized;
	
	public GameObject(int id, BAOSimulationFrame frame, String name) {
		this.frame = frame;
		this.id = id;
		this.name = name;
		initialized = false;
		active.set(true);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void initialize();
	
	public abstract void render(Graphics2D g, double elapsedTime);
	
	public abstract void handleEvents();
	
	public abstract void destroy();
}
