package tests;

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

	Vector<DamageTimer> timers;
	public AtomicInteger health = new AtomicInteger();
	
	public PlayerHealthGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);
		frame.world.addContactListener(this);
		timers = new Vector<DamageTimer>();
		health.set(0);
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
		if(collision.getBody1().id == 1 || collision.getBody2().id == 1) {
			int enemyID = collision.getBody1().id == 1? collision.getBody2().id : collision.getBody1().id;
			for(int i = 0; i < timers.size(); i++) {
				if(enemyID == timers.elementAt(i).enemyID) {
					timers.elementAt(i).StopTimer();
					timers.remove(i);
					break;
				}
			}
			timers.add(new DamageTimer(enemyID, this));
		}
	}

	@Override
	public void end(ContactCollisionData<SimulationBody> collision, Contact contact) {
		if(collision.getBody1().id == 1 || collision.getBody2().id == 1) {
			int enemyID = collision.getBody1().id == 1? collision.getBody2().id : collision.getBody1().id;
			for(int i = 0; i < timers.size(); i++) {
				if(enemyID == timers.elementAt(i).enemyID) {
					timers.elementAt(i).StopTimer();
					timers.remove(i);
					break;
				}
			}
		}
	}
	
	
	@Override
	public void persist(ContactCollisionData<SimulationBody> collision, Contact oldContact, Contact newContact) {}


	@Override
	public void destroyed(ContactCollisionData<SimulationBody> collision, Contact contact) {}

	@Override
	public void collision(ContactCollisionData<SimulationBody> collision) {}

	@Override
	public void preSolve(ContactCollisionData<SimulationBody> collision, Contact contact) {}

	@Override
	public void postSolve(ContactCollisionData<SimulationBody> collision, SolvedContact contact) {}
}

class DamageTimer implements ActionListener{
	public int enemyID;
	private int delay = 10;
	protected Timer timer;
	private PlayerHealthGameObject h;
	
	DamageTimer(int id, PlayerHealthGameObject h){
		enemyID = id;
		this.h = h;
		timer = new Timer(delay, this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(h.health.addAndGet(-1));
	}
	
	public void StopTimer() {
		timer.stop();
	}
	
}
