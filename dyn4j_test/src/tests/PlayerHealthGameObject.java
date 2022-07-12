package tests;

import java.awt.Graphics2D;

import dyn4j.dynamics.contact.Contact;
import dyn4j.dynamics.contact.SolvedContact;
import dyn4j.world.ContactCollisionData;
import dyn4j.world.listener.ContactListener;
import framework.SimulationBody;

public class PlayerHealthGameObject extends GameObject implements ContactListener<SimulationBody> {

	public PlayerHealthGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);
		frame.world.addContactListener(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void begin(ContactCollisionData<SimulationBody> collision, Contact contact) {
		System.out.println("SOMETHING TOUCHED SOMETHING");
		
	}

	@Override
	public void persist(ContactCollisionData<SimulationBody> collision, Contact oldContact, Contact newContact) {
		System.out.println("SOMETHING TOUCHING SOMETHING");
		
	}

	@Override
	public void end(ContactCollisionData<SimulationBody> collision, Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyed(ContactCollisionData<SimulationBody> collision, Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collision(ContactCollisionData<SimulationBody> collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(ContactCollisionData<SimulationBody> collision, Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(ContactCollisionData<SimulationBody> collision, SolvedContact contact) {
		// TODO Auto-generated method stub
		
	}

}
