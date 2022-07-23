package gamesrc;

import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;

// Parent class for all objects added to BAOSimulationFrame
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

	// Called on every object during the render loop, meant to draw anything necessary
	public abstract void render(Graphics2D g, double elapsedTime);

	// Same as with render, but during handle events
	public abstract void handleEvents();

	public abstract void destroy();
}
