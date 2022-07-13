package tests;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Timer;

import dyn4j.dynamics.contact.Contact;
import dyn4j.dynamics.contact.SolvedContact;
import dyn4j.world.ContactCollisionData;
import dyn4j.world.listener.ContactListener;
import framework.SimulationBody;

public class PlayerHealthGameObject extends GameObject implements ContactListener<SimulationBody> {

	public AtomicInteger health = new AtomicInteger();
	
	public PlayerHealthGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);
		frame.world.addContactListener(this);
		health.set(100);
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		
		initialized = true;
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		Font font = new Font("Serif", Font.BOLD, -50);
		g.scale(-1.0, 1);
		g.setFont(font);
		System.out.println(frame.getHeight());
		g.drawString(Integer.toString(health.get()), (int) (frame.getWidth()/2.1f), (int) (frame.getHeight()/2.55f));
		g.scale(-1.0, 1);
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
	}
	
	@Override
	public void persist(ContactCollisionData<SimulationBody> collision, Contact oldContact, Contact newContact) {
		if(collision.getBody1().id == 1 || collision.getBody2().id == 1) {
			SimulationBody enemy = collision.getBody1().id == 1? collision.getBody2() : collision.getBody1();
			if(enemy.zombieRef != null && !enemy.zombieRef.dealtDamageInMove.get() && enemy.zombieRef.moving.get()) {
				System.out.println(health.addAndGet(-10));
				enemy.zombieRef.dealtDamageInMove.set(true);
				if(health.get() <= 0) {
					frame.QueueObjectToDelete(1);
				}
			}
		}
	}


	@Override
	public void destroyed(ContactCollisionData<SimulationBody> collision, Contact contact) {}

	@Override
	public void collision(ContactCollisionData<SimulationBody> collision) {}

	@Override
	public void preSolve(ContactCollisionData<SimulationBody> collision, Contact contact) {}

	@Override
	public void postSolve(ContactCollisionData<SimulationBody> collision, SolvedContact contact) {}

	@Override
	public void end(ContactCollisionData<SimulationBody> collision, Contact contact) {
		// TODO Auto-generated method stub
		
	}
}