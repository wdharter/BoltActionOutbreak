package gamesrc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

// Represents the player in the scene, as a physics-controlled circle
public class PlayerGameObject extends GameObject {

	// Physics part of the player
	SimulationBody body;
	
	// Player states, most can be simultaneously true
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
	
	// Updated while mouse is held down, used for aiming and firing
	private Point mousePos = new Point(0, 0);
	
	// Whenever zombies are killed, sends info to scoreboard
	private ScoreBoardGameObject scoreboard;
	private Camera camera;
	private float playerMoveForce = 7;

	private long last;

	public static final double NANO_TO_BASE = 1.0e9;
	boolean test;

	// Creates an ActionStateListener and binds all relevant objects
	public PlayerGameObject(int id, BAOSimulationFrame frame, String name, Camera mainCam,
			ScoreBoardGameObject scoreboard, AnimationManagerGameObject anims, int fullScroll) {
		super(id, frame, name);
		this.scoreboard = scoreboard;
		ActionStateHandler playerListener = new ActionStateHandler(anims, movePlayerForward, movePlayerLeft,
				movePlayerBackward, movePlayerRight, fire, aim, toggleBolt, boltBack, boltForward, load, fullScroll);
		frame.addKeyListener(playerListener);
		frame.addMouseListener(playerListener);
		frame.addMouseWheelListener(playerListener);
		frame.canvas.addKeyListener(playerListener);
		frame.canvas.addMouseListener(playerListener);
		frame.canvas.addMouseWheelListener(playerListener);
		MouseTracker m = new MouseTracker(mousePos);
		frame.canvas.addMouseListener(m);
		frame.canvas.addMouseMotionListener(m);
		camera = mainCam;
		test = true;
		this.frame.AddGameObject(this);
	}

	// Creates player body and hooks up to anchor frictionjoint
	@Override
	public void initialize() {
		body = new SimulationBody(id);
		body.addFixture(Geometry.createCircle(0.5));
		body.setColor(new Color(161, 136, 92));
		body.setMass(MassType.NORMAL);
		frame.world.addBody(body);

		FrictionJoint<SimulationBody> playerAnchorFriction = new FrictionJoint<SimulationBody>(body, frame.getAnchor(),
				body.getWorldCenter());
		playerAnchorFriction.setMaximumForce(100);
		playerAnchorFriction.setMaximumTorque(1000);
		playerAnchorFriction.setCollisionAllowed(false);
		frame.world.addJoint(playerAnchorFriction);
		initialized = true;
	}

	// Shows the aiming line whenever mouse button 1 is held down
	@Override
	public void render(Graphics2D g, double elapsedTime) {
		aim(g);
	}

	@Override
	public void handleEvents() {
		move();
		fire();
	}

	// Raycasts out from player towards mouse position, kills all zombies the raycast detects
	// also adds to the score based on the amount of zombies killed
	private void fire() {
		if (fire.get()) {
			fire.set(false);
			Vector2 start = body.getTransform().getTranslation();

			Vector2 mouseWorldPosition = camera.toWorldCoordinates(frame.getWidth(), frame.getHeight(), mousePos);

			if (BAOLauncher.Debug) {
				System.out.println("DEBUG: " + mouseWorldPosition);
			}
			Vector2 direction = mouseWorldPosition.subtract(start);
			Vector2 recoil = direction.getNormalized();
			recoil.multiply(-1200);
			body.applyForce(recoil);

			Ray ray = new Ray(start, direction);
			double length = 100000;
			List<RaycastResult<SimulationBody, BodyFixture>> results = frame.world.raycast(ray, length,
					new DetectFilter<SimulationBody, BodyFixture>(true, true, null));
			int zombieCount = 0;
			
			// Counts zombies hit for score multiplier
			for (RaycastResult<SimulationBody, BodyFixture> result : results) {
				if (result.getBody().zombieRef != null)
					zombieCount++;
			}
			
			// Removes all zombies hit and adds a score for each
			for (RaycastResult<SimulationBody, BodyFixture> result : results) {
				int enemyID = result.getBody().id;
				if (enemyID != 0 && result.getBody().zombieRef != null) {
					frame.QueueObjectToDelete(enemyID);
					scoreboard.score.addAndGet(1 * zombieCount);
					WaveHandler.enemyAmount.addAndGet(-1);
				}
			}

			// Creates a vapor trail going in the shot direction
			Vector2 end = start.copy().add(direction.multiply(100000));
			new VaporTrailGameObject(frame.GetID(), this.frame, "vapor", new Point((int) start.x, (int) start.y),
					new Point((int) end.x, (int) end.y), 3);
		}
	}

	// Similar logic to shooting, just draws a line that stops at the first body it meets
	private void aim(Graphics2D g) {
		// Aiming line
		if (aim.get()) {
			Vector2 start = body.getTransform().getTranslation();
			;
			Vector2 mouseWorldPosition = camera.toWorldCoordinates(frame.getWidth(), frame.getHeight(), mousePos);
			Vector2 direction = mouseWorldPosition.subtract(start);
			final double scale = frame.getScale();
			Ray ray = new Ray(start, direction);
			double length = 100000;
			boolean hitObject = false;
			
			List<RaycastResult<SimulationBody, BodyFixture>> results = frame.world.raycast(ray, length,
					new DetectFilter<SimulationBody, BodyFixture>(true, true, null));
			
			for (RaycastResult<SimulationBody, BodyFixture> result : results) {
				Vector2 point = result.getRaycast().getPoint();
				hitObject = true;
				g.setColor(new Color(20, 20, 20, 20));
				g.draw(new Line2D.Double(ray.getStart().x * scale, ray.getStart().y * scale, point.x * scale,
						point.y * scale));
				break;

			}
			if (!hitObject) {
				g.setColor(new Color(25, 25, 25, 25));
				g.draw(new Line2D.Double(ray.getStart().x * scale, ray.getStart().y * scale,
						ray.getStart().x * scale + ray.getDirectionVector().x * length * scale,
						ray.getStart().y * scale + ray.getDirectionVector().y * length * scale));
			}
		}
	}

	// Adds up each direction player wants to move in, 
	// then normalizes to prevent faster diagonal movement
	private void move() {
		Vector2 moveDir = new Vector2(0, 0);

		if (movePlayerForward.get()) {
			moveDir = moveDir.add(new Vector2(0.0, 1.0));
		}

		if (movePlayerBackward.get()) {
			moveDir = moveDir.add(new Vector2(0.0, -1.0));
		}

		if (movePlayerLeft.get()) {
			moveDir = moveDir.add(new Vector2(-1.0, 0.0));
		}

		if (movePlayerRight.get()) {
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
		double elapsedTime = (double) diff / NANO_TO_BASE;

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

// Updates mouse position whenever it is moved for use by PlayerGameObject
class MouseTracker extends MouseAdapter {
	Point track;

	MouseTracker(Point track) {
		this.track = track;
	}

	public void mouseDragged(MouseEvent e) {
		track.x = e.getX();
		track.y = e.getY();
	}

	public void mousePressed(MouseEvent e) {
		track.x = e.getX();
		track.y = e.getY();
	}

}
