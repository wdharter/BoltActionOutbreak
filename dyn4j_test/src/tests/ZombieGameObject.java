package tests;

import java.awt.Graphics2D;
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
	private float zombieMoveForce = 50;
	private float maxSpeed = 5;
	private AtomicBoolean moving = new AtomicBoolean();
	
	public ZombieGameObject(int id, BAOSimulationFrame frame, String name, double x, double y, PlayerGameObject player) {
		super(id, frame, name);
		
		this.player = player;
		initialX = x;
		initialY = y;
		moving.set(true);
		new StepTimer(moving);
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		zombie = new SimulationBody(id);
		zombie.addFixture(Geometry.createCircle(0.5));
		zombie.translate(new Vector2(initialX, initialY));
		zombie.setMass(MassType.NORMAL);
		frame.world.addBody(zombie);
		
		FrictionJoint<SimulationBody> zombieAnchorFriction = new FrictionJoint<SimulationBody>(
				zombie, 
				frame.getAnchor(), 
				zombie.getWorldCenter());
		zombieAnchorFriction.setMaximumForce(100);
		zombieAnchorFriction.setMaximumTorque(1000);
		zombieAnchorFriction.setCollisionAllowed(false);
		frame.world.addJoint(zombieAnchorFriction);
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvents() {
		Vector2 playerPosition = player.getBody().getTransform().getTranslation();
		Vector2 myPosition = zombie.getTransform().getTranslation();
		Vector2 moveDir = playerPosition.subtract(myPosition).getNormalized();

		moveDir.multiply(zombieMoveForce);
		if(moving.get())
			zombie.applyForce(moveDir);
		
		Vector2 currentVelocity = zombie.getLinearVelocity();
		double currentSpeed = zombie.getLinearVelocity().getMagnitude();
		if(currentSpeed > maxSpeed)
			currentSpeed = maxSpeed;
		currentVelocity.normalize();
		currentVelocity.multiply(currentSpeed);
		zombie.setLinearVelocity(currentVelocity);
		
	}

}


class StepTimer implements ActionListener{
	private int delay;
	protected Timer timer;
	AtomicBoolean moving;
	
	public StepTimer(AtomicBoolean moving) {
		Random r = new Random();
		delay = r.nextInt(200) + 300;
		timer = new Timer(delay, this);
		timer.start();
		this.moving = moving;
	}

	public void actionPerformed(ActionEvent e)
	{
		//moving.set(!moving.get());
		if(moving.get())
			moving.set(false);
		else
			moving.set(true);
	}
}