package tests;

import java.util.concurrent.atomic.AtomicBoolean;

import dyn4j.dynamics.joint.FrictionJoint;
import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import framework.SimulationBody;

public class PlayerGameObject extends GameObject {
	
	SimulationBody player;
	SimulationBody anchor;
	FrictionJoint<SimulationBody> playerAnchorFriction;
	

	private final AtomicBoolean movePlayerForward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerBackward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerLeft = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerRight = new AtomicBoolean(false);

	private float playerMoveForce = 50;
	
	public PlayerGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);

		BAOKeyListener playerListener = new BAOKeyListener(movePlayerForward, movePlayerLeft, movePlayerBackward, movePlayerRight);
		frame.addKeyListener(playerListener);
		frame.canvas.addKeyListener(playerListener);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		anchor = new SimulationBody();
		anchor.addFixture(Geometry.createCircle(0.5));
		anchor.translate(new Vector2(1.5, -2.0));
		anchor.setMass(MassType.INFINITE);
		frame.world.addBody(anchor);
		
		player = new SimulationBody();
		player.addFixture(Geometry.createSquare(1.0));
		player.setMass(MassType.NORMAL);
		frame.world.addBody(player);
		
		playerAnchorFriction = new FrictionJoint<SimulationBody>(player, anchor, player.getWorldCenter());
		playerAnchorFriction.setMaximumForce(100);
		playerAnchorFriction.setMaximumTorque(1000);
		playerAnchorFriction.setCollisionAllowed(true);
		frame.world.addJoint(playerAnchorFriction);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvents() {
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
		player.applyForce(moveDir);
	}
	
}
