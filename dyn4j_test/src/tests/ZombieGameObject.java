package tests;

import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import framework.SimulationBody;

public class ZombieGameObject extends GameObject {

	SimulationBody zombie;
	double initialX;
	double initialY;
	PlayerGameObject player;
	
	public ZombieGameObject(int id, BAOSimulationFrame frame, String name, double x, double y, PlayerGameObject player) {
		super(id, frame, name);
		this.player = player;
		initialX = x;
		initialY = y;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		zombie = new SimulationBody();
		zombie.addFixture(Geometry.createCircle(1.0));
		zombie.translate(new Vector2(initialX, initialY));
		zombie.setMass(MassType.NORMAL);
		frame.world.addBody(zombie);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvents() {
		// TODO Auto-generated method stub

	}

}
