package tests;

import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;
import dyn4j.dynamics.joint.FrictionJoint;
import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Vector2;
import dyn4j.world.World;
import framework.SimulationBody;
import framework.SimulationFrame;

public class BAOSimulationFrame extends SimulationFrame {
	private static final long serialVersionUID = 1L;
	private final AtomicBoolean movePlayerForward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerBackward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerLeft = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerRight = new AtomicBoolean(false);
	
	private SimulationBody player;
	private float playerMoveForce = 50;
	
	public BAOSimulationFrame(String name, double scale) {
		super(name, scale);
		BAOKeyListener playerListener = new BAOKeyListener(movePlayerForward, movePlayerLeft, movePlayerBackward, movePlayerRight);
		this.addKeyListener(playerListener);
		this.canvas.addKeyListener(playerListener);
		this.setMousePanningEnabled(false);
		this.setMousePickingEnabled(false);
	}
	
	protected void initializeWorld() {
		this.world.setGravity(World.ZERO_GRAVITY);
		
		SimulationBody anchor = new SimulationBody();
		anchor.addFixture(Geometry.createCircle(0.5));
		anchor.translate(new Vector2(1.5, -2.0));
		anchor.setMass(MassType.INFINITE);
		this.world.addBody(anchor);
		
		player = new SimulationBody();
		player.addFixture(Geometry.createSquare(1.0));
		player.setMass(MassType.NORMAL);
		this.world.addBody(player);
		
		FrictionJoint<SimulationBody> playerAnchorFriction = new FrictionJoint<SimulationBody>(player, anchor, player.getWorldCenter());
		playerAnchorFriction.setMaximumForce(100);
		playerAnchorFriction.setMaximumTorque(1000);
		playerAnchorFriction.setCollisionAllowed(true);
		this.world.addJoint(playerAnchorFriction);
	}
	
	protected void render(Graphics2D g, double deltaTime) {
		super.render(g, deltaTime);
	}
	
	protected void handleEvents() {
		super.handleEvents();
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
