package gamesrc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.Timer;

import dyn4j.dynamics.joint.FrictionJoint;
import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import framework.SimulationBody;

public class ZombieGameObject extends GameObject {

	SimulationBody zombie;
	double initialX;
	double initialY;
	PlayerGameObject player;
	private float zombieMoveForce;
	private float maxSpeed = 5;
	public AtomicBoolean moving = new AtomicBoolean();
	public AtomicBoolean dealtDamageInMove = new AtomicBoolean();
	private float lungeLength = 15;
	private long last;

	public static final double NANO_TO_BASE = 1.0e9;

	public ZombieGameObject(int id, BAOSimulationFrame frame, String name, double x, double y, PlayerGameObject player,
			float zombieMoveForce) {
		super(id, frame, name);

		this.player = player;
		initialX = x;
		initialY = y;
		moving.set(true);
		new StepTimer(moving, dealtDamageInMove);
		this.frame.AddGameObject(this);
		this.zombieMoveForce = zombieMoveForce;
		dealtDamageInMove.set(false);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		zombie = new SimulationBody(id);
		zombie.zombieRef = this;
		zombie.addFixture(Geometry.createCircle(0.5));
		zombie.translate(new Vector2(initialX, initialY));
		zombie.setColor(new Color(32, 79, 54));
		zombie.setMass(MassType.NORMAL);
		frame.world.addBody(zombie);

		FrictionJoint<SimulationBody> zombieAnchorFriction = new FrictionJoint<SimulationBody>(zombie,
				frame.getAnchor(), zombie.getWorldCenter());
		zombieAnchorFriction.setMaximumForce(100);
		zombieAnchorFriction.setMaximumTorque(1000);
		zombieAnchorFriction.setCollisionAllowed(false);
		frame.world.addJoint(zombieAnchorFriction);
		initialized = true;
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvents() {
		if (initialized) {
			Vector2 playerPosition = player.getBody().getTransform().getTranslation();
			Vector2 myPosition = zombie.getTransform().getTranslation();
			Vector2 moveDir = playerPosition.subtract(myPosition).getNormalized();
			if (ActionStateHandler.gameEnded) {
				moveDir.multiply(-1);
			}
			moveDir.multiply(zombieMoveForce);
			Vector2 distance = (new Vector2((int) myPosition.x - (int) playerPosition.x,
					(int) myPosition.y - (int) playerPosition.y)).subtract(myPosition);
			if (distance.getMagnitude() <= lungeLength) {
				moveDir.multiply(2.3);
			}
			// get the current time
			long time = System.nanoTime();
			// get the elapsed time from the last iteration
			long diff = time - this.last;
			// set the last time
			this.last = time;
			// convert from nanoseconds to seconds
			double elapsedTime = (double) diff / NANO_TO_BASE;

			moveDir.multiply(elapsedTime * 1000);

			if (moving.get())
				zombie.applyForce(moveDir);

			Vector2 currentVelocity = zombie.getLinearVelocity();
			double currentSpeed = zombie.getLinearVelocity().getMagnitude();
			if (currentSpeed > maxSpeed)
				currentSpeed = maxSpeed;
			currentVelocity.normalize();
			currentVelocity.multiply(currentSpeed);
			zombie.setLinearVelocity(currentVelocity);
		}
	}

	@Override
	public void destroy() {
		frame.world.removeBody(zombie);
	}

}

class StepTimer implements ActionListener {
	private int delay;
	protected Timer timer;
	AtomicBoolean moving;
	AtomicBoolean dealtDamage;

	public StepTimer(AtomicBoolean moving, AtomicBoolean dealtDamage) {
		Random r = new Random();
		delay = r.nextInt(200) + 300;
		timer = new Timer(delay, this);
		timer.start();
		this.moving = moving;
		this.dealtDamage = dealtDamage;
	}

	public void actionPerformed(ActionEvent e) {
		// moving.set(!moving.get());
		if (moving.get()) {
			moving.set(false);
			Random r = new Random();
			delay = r.nextInt(200);
		} else {
			moving.set(true);
			dealtDamage.set(false);
			Random r = new Random();
			delay = r.nextInt(200) + 300;
		}
	}
}