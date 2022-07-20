package gamesrc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

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
	private final AtomicBoolean toggleBolt = new AtomicBoolean(false);
	private final AtomicBoolean boltBack = new AtomicBoolean(false);
	private final AtomicBoolean boltForward = new AtomicBoolean(false);
	private final AtomicBoolean load = new AtomicBoolean(false);
	private ScoreBoardGameObject scoreboard;
	private Camera camera;
	private float playerMoveForce = 7;

	private long last;

	public static final double NANO_TO_BASE = 1.0e9;
	boolean test;
	public PlayerGameObject(
			int id, 
			BAOSimulationFrame frame, 
			String name, 
			Camera mainCam, 
			ScoreBoardGameObject 
			scoreboard, 
			AnimationManagerGameObject anims) 
	{	
		super(id, frame, name);
		this.scoreboard = scoreboard;
		ActionStateHandler playerListener = new ActionStateHandler(
				anims,
				movePlayerForward, 
				movePlayerLeft, 
				movePlayerBackward, 
				movePlayerRight, 
				fire, 
				aim,
				toggleBolt,
				boltBack,
				boltForward,
				load);
		frame.addKeyListener(playerListener);
		frame.addMouseListener(playerListener);
		frame.addMouseWheelListener(playerListener);
		frame.canvas.addKeyListener(playerListener);
		frame.canvas.addMouseListener(playerListener);
		frame.canvas.addMouseWheelListener(playerListener);
		camera = mainCam;
		test = true;
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		body = new SimulationBody(id);
		body.addFixture(Geometry.createCircle(0.5));
		body.setColor(new Color(161, 136, 92));
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
		initialized = true;
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
			if(BAOLauncher.Debug) {
				System.out.println("DEBUG: " + mouseWorldPosition);
			}
			Vector2 direction = mouseWorldPosition.subtract(start);
			Vector2 recoil = direction.getNormalized();
			recoil.multiply(-1200);
			body.applyForce(recoil);
			
			Ray ray = new Ray(start, direction);
			double length = 100000;
			List<RaycastResult<SimulationBody, BodyFixture>> results = frame.world.raycast(ray, length, new DetectFilter<SimulationBody, BodyFixture>(true, true, null));
			int zombieCount = 0;
			for(RaycastResult<SimulationBody, BodyFixture> result : results) {
				if(result.getBody().zombieRef != null)
					zombieCount++;
			}
			for(RaycastResult<SimulationBody, BodyFixture> result : results) {
				int enemyID = result.getBody().id;
				if(enemyID != 0 && result.getBody().zombieRef != null) {
					frame.QueueObjectToDelete(enemyID);
					scoreboard.score.addAndGet(1 * zombieCount);
					WaveHandler.enemyAmount.addAndGet(-1);
				}
			}
			
			Vector2 end = start.copy().add(direction.multiply(400));
			new VaporTrailGameObject(frame.GetID(), this.frame, "vapor", new Point((int)start.x, (int)start.y), new Point((int)end.x, (int)end.y), 3);
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
				g.setColor(new Color(20, 20, 20, 20));
				g.draw(new Line2D.Double(
						ray.getStart().x * scale, 
						ray.getStart().y * scale, 
						point.x *scale, 
						point.y *scale));
				break;
						
			}
			if(!hitObject) {
				g.setColor(new Color(25, 25, 25, 25));
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
		

		// get the current time
        long time = System.nanoTime();
        // get the elapsed time from the last iteration
        long diff = time - this.last;
        // set the last time
        this.last = time;
    	// convert from nanoseconds to seconds
    	double elapsedTime = (double)diff / NANO_TO_BASE;
    	
    	moveDir.multiply(elapsedTime * 1000);
    	
		
		body.applyForce(moveDir);
	}
	
	public SimulationBody getBody() {
		return body;
	}

	@Override
	public void destroy() {
		frame.world.removeBody(body);
	}
	
}
