package tests;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import dyn4j.dynamics.joint.FrictionJoint;
import dyn4j.geometry.Geometry;
import dyn4j.geometry.MassType;
import dyn4j.geometry.Ray;
import dyn4j.world.result.RaycastResult;
import dyn4j.geometry.Vector2;
import framework.Camera;
import framework.SimulationBody;
import dyn4j.world.DetectFilter;
import dyn4j.dynamics.BodyFixture;

public class PlayerGameObject extends GameObject {
	
	SimulationBody body;
	private final AtomicBoolean movePlayerForward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerBackward = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerLeft = new AtomicBoolean(false);
	private final AtomicBoolean movePlayerRight = new AtomicBoolean(false);
	private final AtomicBoolean aim = new AtomicBoolean(false);
	private final AtomicBoolean fire = new AtomicBoolean(false);
	private Camera camera;
	private float playerMoveForce = 50;
	
	public PlayerGameObject(int id, BAOSimulationFrame frame, String name, Camera mainCam) {
		super(id, frame, name);
		InputHandler playerListener = new InputHandler(movePlayerForward, movePlayerLeft, movePlayerBackward, movePlayerRight, fire, aim);
		frame.addKeyListener(playerListener);
		frame.addMouseListener(playerListener);
		frame.canvas.addKeyListener(playerListener);
		frame.canvas.addMouseListener(playerListener);
		camera = mainCam;
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		body = new SimulationBody(id);
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
	public void render(Graphics2D g, double elapsedTime){
		aim(g);
		
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
			Vector2 direction = mouseWorldPosition.subtract(start);
			Vector2 recoil = direction.getNormalized();
			recoil.multiply(-1200);
			body.applyForce(recoil);
			
			Ray ray = new Ray(start, direction);
			double length = 100000;
			List<RaycastResult<SimulationBody, BodyFixture>> results = frame.world.raycast(ray, length, new DetectFilter<SimulationBody, BodyFixture>(true, true, null));
			for(RaycastResult<SimulationBody, BodyFixture> result : results) {
				int enemyID = result.getBody().id;
				if(enemyID != 0) {
					frame.QueueObjectToDelete(enemyID);
					frame.world.removeBody(result.getBody());
				}
			}
		}
	}
	
	private void aim(Graphics2D g)
	{ 
		if(aim.get())
		{
			Vector2 start = body.getTransform().getTranslation();
			Point mousePosition = MouseInfo.getPointerInfo().getLocation();
			
			Vector2 mouseWorldPosition = camera.toWorldCoordinates(frame.getWidth(), frame.getHeight(), mousePosition);
			Vector2 direction = mouseWorldPosition.subtract(start);
			final double scale = frame.getScale();
			Ray ray = new Ray(start, direction);
			double length = 100000;
			boolean hitObject = false;
			List<RaycastResult<SimulationBody, BodyFixture>> results = frame.world.raycast(ray, length, new DetectFilter<SimulationBody, BodyFixture>(true, true, null));
			for(RaycastResult<SimulationBody, BodyFixture> result : results) {
				Vector2 point = result.getRaycast().getPoint();
				hitObject = true;
				g.setColor(Color.GRAY);
				g.draw(new Line2D.Double(
						ray.getStart().x * scale, 
						ray.getStart().y * scale, 
						point.x *scale, 
						point.y *scale));
				break;
						
			}
			if(!hitObject) {
				g.setColor(Color.BLACK);
				g.draw(new Line2D.Double(
						ray.getStart().x * scale, 
						ray.getStart().y * scale, 
						ray.getStart().x * scale + ray.getDirectionVector().x * length * scale, 
						ray.getStart().y * scale + ray.getDirectionVector().y * length * scale));
			}

			
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
