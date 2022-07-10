package tests;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicBoolean;

import dyn4j.dynamics.joint.FrictionJoint;
import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import framework.Camera;
import framework.SimulationBody;

public class PlayerGameObject extends GameObject {
	
	SimulationBody body;
	private final AtomicBoolean movePlayerForward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerBackward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerLeft = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerRight = new AtomicBoolean(false);
	private final AtomicBoolean fire = new AtomicBoolean(false);
	private Camera camera;
	private float playerMoveForce = 50;
	
	public PlayerGameObject(int id, BAOSimulationFrame frame, String name, Camera mainCam) {
		super(id, frame, name);
		
		InputHandler playerListener = new InputHandler(movePlayerForward, movePlayerLeft, movePlayerBackward, movePlayerRight, fire);
		frame.addKeyListener(playerListener);
		frame.addMouseListener(playerListener);
		frame.canvas.addKeyListener(playerListener);
		frame.canvas.addMouseListener(playerListener);
		camera = mainCam;
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		body = new SimulationBody();
		body.addFixture(Geometry.createSquare(1.0));
		body.setMass(MassType.NORMAL);
		frame.world.addBody(body);
		

		FrictionJoint<SimulationBody> playerAnchorFriction = new FrictionJoint<SimulationBody>(
				body, 
				frame.getAnchor(), 
				body.getWorldCenter());
		playerAnchorFriction.setMaximumForce(100);
		playerAnchorFriction.setMaximumTorque(1000);
		playerAnchorFriction.setCollisionAllowed(false);
		frame.world.addJoint(playerAnchorFriction);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvents() {
		move();
		fire();
	}
	
	private void fire() {
		if(fire.get()) {
			fire.set(false);

			Vector2 start = body.getTransform().getTranslation();
			Point mousePosition = MouseInfo.getPointerInfo().getLocation();
			
			Vector2 mouseWorldPosition = camera.toWorldCoordinates(frame.getWidth(), frame.getHeight(), mousePosition);
			System.out.println(mouseWorldPosition);
			Vector2 direction = mouseWorldPosition.subtract(start);
			direction.normalize();
			direction.multiply(-1200);
			body.applyForce(direction);
			//raycast to mouse position
			//delete everything it hits
		}
	}
	
	private void move() {
		Vector2 moveDir = new Vector2(0, 0);
		
		if(movePlayerForward.get()) {
			moveDir = moveDir.add(new Vector2(0.0, 1.0));
		}

		if(movePlayerBackward.get()) {
			moveDir = moveDir.add(new Vector2(0.0, -1.0));
		}
		
		if(movePlayerLeft.get()) {
			moveDir = moveDir.add(new Vector2(-1.0, 0.0));
		}

		if(movePlayerRight.get()) {
			moveDir = moveDir.add(new Vector2(1.0, 0.0));
		}
		
		moveDir.normalize();
		moveDir.multiply(playerMoveForce);
		body.applyForce(moveDir);
	}
	
	public SimulationBody getBody() {
		return body;
	}
	
}
