package baologic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AnimationManagerGameObject extends GameObject {
	private final BufferedImage[] idle = {
			Sprite.getSprite("BoltActionStages", 0, 0)
			};
	private final BufferedImage[] shoot = {
			Sprite.getSprite("BoltActionStages", 0, 1),
			Sprite.getSprite("BoltActionStages", 1, 1),
			Sprite.getSprite("BoltActionStages", 2, 1),
			Sprite.getSprite("BoltActionStages", 3, 1),
			Sprite.getSprite("BoltActionStages", 4, 1),
			Sprite.getSprite("BoltActionStages", 5, 1),
			Sprite.getSprite("BoltActionStages", 6, 1)
			};
	private final BufferedImage[] unlock = {
			Sprite.getSprite("BoltActionStages", 0, 2),
			Sprite.getSprite("BoltActionStages", 1, 2),
			Sprite.getSprite("BoltActionStages", 2, 2),
			Sprite.getSprite("BoltActionStages", 3, 2),
			Sprite.getSprite("BoltActionStages", 4, 2),
			Sprite.getSprite("BoltActionStages", 5, 2),
			Sprite.getSprite("BoltActionStages", 6, 2)
			};
	private final BufferedImage[] openFull = {
			Sprite.getSprite("BoltActionStages", 0, 3),
			Sprite.getSprite("BoltActionStages", 1, 3),
			Sprite.getSprite("BoltActionStages", 2, 3),
			Sprite.getSprite("BoltActionDryOpen", 2, 0) //included so bullet transitions to bullet anim
			};
	private final BufferedImage[] openEmpty = {
			Sprite.getSprite("BoltActionDryOpen", 0, 0),
			Sprite.getSprite("BoltActionDryOpen", 1, 0),
			Sprite.getSprite("BoltActionDryOpen", 2, 0)
			};
	private final BufferedImage[] close = {
			Sprite.getSprite("BoltActionStages", 0, 4),
			Sprite.getSprite("BoltActionStages", 1, 4),
			Sprite.getSprite("BoltActionStages", 2, 4)
			};
	private BufferedImage[] lock = {
			Sprite.getSprite("BoltActionStages", 0, 5),
			Sprite.getSprite("BoltActionStages", 1, 5),
			Sprite.getSprite("BoltActionStages", 2, 5),
			Sprite.getSprite("BoltActionStages", 3, 5),
			Sprite.getSprite("BoltActionStages", 4, 5),
			Sprite.getSprite("BoltActionStages", 5, 5)
			};
	private BufferedImage[] bullet = {
			Sprite.getSprite("Bullet_For_BoltAction_19_25", 0, 0),
			Sprite.getSprite("Bullet_For_BoltAction_19_25", 1, 0),
			Sprite.getSprite("Bullet_For_BoltAction_19_25", 2, 0),
			Sprite.getSprite("Bullet_For_BoltAction_19_25", 3, 0),
			Sprite.getSprite("Bullet_For_BoltAction_19_25", 4, 0),
			Sprite.getSprite("Bullet_For_BoltAction_19_25", 5, 0),
			Sprite.getSprite("Bullet_For_BoltAction_19_25", 6, 0)
			};
	private Animation idleAnim = new Animation(idle, 8, false);
	private Animation shootAnim = new Animation(shoot, 8, false);
	private Animation unlockAnim = new Animation(unlock, 8, false);
	private Animation openFullAnim = new Animation(openFull, 8, false);
	private Animation openEmptyAnim = new Animation(openEmpty, 8, false);
	private Animation closeAnim = new Animation(close, 8, false);
	private Animation lockAnim = new Animation(lock, 8, false);
	private Animation bulletAnim = new Animation(bullet, 8, false);
	
	private Animation currAnim = idleAnim;
	private Animation currBulletAnim = bulletAnim;
	private Anim curr;
	private boolean idleAtEnd;
	
	public AnimationManagerGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		if(currAnim.isStopped() && idleAtEnd)
			PlayAnimation(Anim.IDLE, true);
		if(currAnim.isStopped() && curr == Anim.OPEN) {
			PlayBullet();
		}
		currAnim.update();
		g.scale(1.0, -1.0);
		g.drawImage(currAnim.getSprite(), -520, -350, null);
		g.scale(1.0, -1.0);
	}
	
	public void PlayAnimation(Anim a, boolean idleAtEnd) {
		this.idleAtEnd = idleAtEnd;
		curr = a;
		currAnim.stop();
		currAnim.reset();
		currAnim = GetAnim(a);
	}
	
	public void PlayBullet() {
		
	}

	@Override
	public void handleEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	private Animation GetAnim(Anim a) {
		switch(a) {
		case IDLE:
			return idleAnim;
		case UNLOCK:
			return unlockAnim;
		case OPEN:
			return openFullAnim;
		case CLOSE:
			return closeAnim;
		case LOCK:
			return lockAnim;
		case FIRE:
			return shootAnim;
		case OPENEMPTY:
			return openEmptyAnim;
		case BULLET:
			return bulletAnim;
		}
		return null;
	}
	
	enum Anim{
		IDLE,
		UNLOCK,
		OPEN,
		CLOSE,
		LOCK,
		FIRE,
		OPENEMPTY,
		BULLET
	}

}
