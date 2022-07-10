package tests;


public abstract class GameObject {
	protected BAOSimulationFrame frame;
	private int id;
	private String name;
	
	public GameObject(int id, BAOSimulationFrame frame, String name) {
		this.frame = frame;
		this.id = id;
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void initialize();
	
	public abstract void render();
	
	public abstract void handleEvents();
}
