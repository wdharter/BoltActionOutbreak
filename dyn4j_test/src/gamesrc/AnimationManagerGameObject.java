package gamesrc;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class AnimationManagerGameObject extends GameObject {
	private final BufferedImage[] idle = {
			Sprite.getSprite(GetStream("BoltActionStages"), 0, 0)
			};
	private final BufferedImage[] shoot = {
			Sprite.getSprite(GetStream("BoltActionStages"), 0, 1),
			Sprite.getSprite(GetStream("BoltActionStages"), 1, 1),
			Sprite.getSprite(GetStream("BoltActionStages"), 2, 1),
			Sprite.getSprite(GetStream("BoltActionStages"), 3, 1),
			Sprite.getSprite(GetStream("BoltActionStages"), 4, 1),
			Sprite.getSprite(GetStream("BoltActionStages"), 5, 1),
			Sprite.getSprite(GetStream("BoltActionStages"), 6, 1)
			};
	private final BufferedImage[] unlock = {
			Sprite.getSprite(GetStream("BoltActionStages"), 0, 2),
			Sprite.getSprite(GetStream("BoltActionStages"), 1, 2),
			Sprite.getSprite(GetStream("BoltActionStages"), 2, 2),
			Sprite.getSprite(GetStream("BoltActionStages"), 3, 2),
			Sprite.getSprite(GetStream("BoltActionStages"), 4, 2),
			Sprite.getSprite(GetStream("BoltActionStages"), 5, 2),
			Sprite.getSprite(GetStream("BoltActionStages"), 6, 2)
			};
	private final BufferedImage[] openFull = {
			Sprite.getSprite(GetStream("BoltActionStages"), 0, 3),
			Sprite.getSprite(GetStream("BoltActionStages"), 1, 3),
			Sprite.getSprite(GetStream("BoltActionStages"), 2, 3),
			Sprite.getSprite(GetStream("BoltActionStages"), 3, 3)
			};
	private final BufferedImage[] openFullNoShot = {
			Sprite.getSprite(GetStream("BoltActionFullNoShotOpen"), 0, 0),
			Sprite.getSprite(GetStream("BoltActionFullNoShotOpen"), 1, 0),
			Sprite.getSprite(GetStream("BoltActionFullNoShotOpen"), 2, 0)
			};
	private final BufferedImage[] openEmpty = {
			Sprite.getSprite(GetStream("BoltActionDryOpen"), 0, 0),
			Sprite.getSprite(GetStream("BoltActionDryOpen"), 1, 0),
			Sprite.getSprite(GetStream("BoltActionDryOpen"), 2, 0)
			};
	private final BufferedImage[] openedFull = {
			Sprite.getSprite(GetStream("BoltActionOpenedFull"), 0, 0)
			};
	private final BufferedImage[] close = {
			Sprite.getSprite(GetStream("BoltActionStages"), 0, 4),
			Sprite.getSprite(GetStream("BoltActionStages"), 1, 4),
			Sprite.getSprite(GetStream("BoltActionStages"), 2, 4)
			};
	private BufferedImage[] lock = {
			Sprite.getSprite(GetStream("BoltActionStages"), 0, 5),
			Sprite.getSprite(GetStream("BoltActionStages"), 1, 5),
			Sprite.getSprite(GetStream("BoltActionStages"), 2, 5),
			Sprite.getSprite(GetStream("BoltActionStages"), 3, 5),
			Sprite.getSprite(GetStream("BoltActionStages"), 4, 5),
			Sprite.getSprite(GetStream("BoltActionStages"), 5, 5)
			};
	private BufferedImage[] bullet = {
			Sprite.getSprite(GetStream("Bullet_For_BoltAction_19_25"), 0, 0),
			Sprite.getSprite(GetStream("Bullet_For_BoltAction_19_25"), 1, 0),
			Sprite.getSprite(GetStream("Bullet_For_BoltAction_19_25"), 2, 0),
			Sprite.getSprite(GetStream("Bullet_For_BoltAction_19_25"), 3, 0),
			Sprite.getSprite(GetStream("Bullet_For_BoltAction_19_25"), 4, 0),
			Sprite.getSprite(GetStream("Bullet_For_BoltAction_19_25"), 5, 0),
			Sprite.getSprite(GetStream("Bullet_For_BoltAction_19_25"), 6, 0)
			};
	private Animation idleAnim = new Animation(idle, 8, false);
	private Animation shootAnim = new Animation(shoot, 8, false);
	private Animation unlockAnim = new Animation(unlock, 4, false);
	private Animation openFullAnim = new Animation(openFull, 4, false);
	private Animation openFullNoShotAnim = new Animation(openFullNoShot, 4, false);
	private Animation openEmptyAnim = new Animation(openEmpty, 4, false);
	private Animation openedFullAnim = new Animation(openedFull, 4, false);
	private Animation closeAnim = new Animation(close, 4, false);
	private Animation lockAnim = new Animation(lock, 4, false);
	private Animation bulletAnim = new Animation(bullet, 8, false);
	
	private Animation currAnim = idleAnim;
	private Animation currBulletAnim = bulletAnim;
	private Anim curr;
	private boolean idleAtEnd;
	private boolean playingBullet;
	
	public AnimationManagerGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);
		this.frame.AddGameObject(this);
		playingBullet = false;
	}

	@Override
	public void initialize() {
		initialized = true;
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		if(currAnim.isStopped() && idleAtEnd)
			PlayAnimation(Anim.IDLE, true);
		if(curr == Anim.OPEN && !playingBullet) {
			PlayBullet();
		}
		currAnim.update();
		g.scale(1.0, -1.0);
		g.drawImage(currAnim.getSprite(), -520, -350, null);
		if(!currBulletAnim.isStopped()) {
			currBulletAnim.update();
			g.drawImage(currBulletAnim.getSprite(), -520, -350, null);
		}
		g.scale(1.0, -1.0);
		
	}
	
	public void PlayAnimation(Anim a, boolean idleAtEnd) {
		this.idleAtEnd = idleAtEnd;
		curr = a;
		currAnim.stop();
		currAnim.reset();
		currAnim = GetAnim(a);
		currAnim.start();	
		if(playingBullet && currBulletAnim.isStopped()) {
			playingBullet = false;
		}
	}
	
	public void PlayBullet() {
		currBulletAnim.stop();
		currBulletAnim.reset();
		currBulletAnim = bulletAnim;
		playingBullet = true;
		currBulletAnim.start();
	}

	@Override
	public void handleEvents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public InputStream GetStream(String file) {
		return AnimationManagerGameObject.class.getResourceAsStream("sprites/" + file + ".png");
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
		case OPENFULLNOSHOT:
			return openFullNoShotAnim;
		case OPENEDFULL:
			return openedFullAnim;
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
		OPENEDFULL,
		BULLET,
		OPENFULLNOSHOT
	}

}
