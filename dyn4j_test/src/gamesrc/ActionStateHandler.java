package gamesrc;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

import gamesrc.AnimationManagerGameObject.Anim;

// Handles mouse/keyboard inputs and logic tied to those inputs, primarily reloading.
public class ActionStateHandler extends KeyAdapter implements MouseListener, MouseWheelListener {

	static int fullScrollAmount;
	private int currentScrollAmount;
	private AtomicInteger magRoundCount = new AtomicInteger(); // Made atomic just in case inputs come in separate
																// threads
	private boolean closed = true;
	private boolean unlocked = false;
	private boolean chambered = true;
	private boolean cocked = true;
	private boolean almostChambered = false;
	private boolean justFired = false; // Used to stop responding to inputs
	public static boolean gameEnded = false;
	AnimationManagerGameObject anims;
	AtomicBoolean waction;
	AtomicBoolean aaction;
	AtomicBoolean saction;
	AtomicBoolean daction;
	AtomicBoolean releaseaction;
	AtomicBoolean pressaction;
	AtomicBoolean rightpressaction;
	AtomicBoolean mwdownaction;
	AtomicBoolean mwupaction;
	AtomicBoolean spaceaction;

	// Created by player game object,
	// these are checked by the player every frame
	// to respond to movement inputs
	// or to fire/aim
	public ActionStateHandler(AnimationManagerGameObject anims, AtomicBoolean w, AtomicBoolean a, AtomicBoolean s,
			AtomicBoolean d, AtomicBoolean release, AtomicBoolean press, AtomicBoolean rightpress, AtomicBoolean mwdown,
			AtomicBoolean mwup, AtomicBoolean space) {
		this.anims = anims;
		waction = w;
		aaction = a;
		saction = s;
		daction = d;
		releaseaction = release;
		pressaction = press;
		rightpressaction = rightpress;
		mwdownaction = mwdown;
		mwupaction = mwup;
		spaceaction = space;
		magRoundCount.set(5);
	}

	// Handles WASD movement and reloading inputs
	public void keyPressed(KeyEvent e) {
		if (gameEnded) {
			return;
		}
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			waction.set(true);
			break;
		case KeyEvent.VK_A:
			aaction.set(true);
			break;
		case KeyEvent.VK_S:
			saction.set(true);
			break;
		case KeyEvent.VK_D:
			daction.set(true);
			break;
		case KeyEvent.VK_SPACE:
			spaceaction.set(true);
			loadBullets();
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (gameEnded) {
			return;
		}
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			waction.set(false);
			break;
		case KeyEvent.VK_A:
			aaction.set(false);
			break;
		case KeyEvent.VK_S:
			saction.set(false);
			break;
		case KeyEvent.VK_D:
			daction.set(false);
			break;
		case KeyEvent.VK_SPACE:
			spaceaction.set(false);
			break;
		}
	}

	// Checks that player cannot reload more than 5 bullets, reloads
	// plays corresponding FX
	public void loadBullets() {
		if (!closed && magRoundCount.get() < 5) {
			int rounds = magRoundCount.get();
			magRoundCount.set(++rounds);
			if (magRoundCount.get() == 5) {
				anims.PlayAnimation(Anim.OPENEDFULL, false);
			}
			SoundManager load;
			try {
				load = new SoundManager(Sound.LOAD, false);
				load.play();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	// Aiming
	@Override
	public void mousePressed(MouseEvent e) {
		if (gameEnded) {
			return;
		}
		// We only show aim line if releasing the mouse would fire a shot
		if (SwingUtilities.isLeftMouseButton(e) && cocked && closed && !unlocked) {
			pressaction.set(true);
		}
	}

	// Firing & bolt up/down toggling
	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameEnded) {
			return;
		}
		// Play sound cue if magazine is empty
		if (SwingUtilities.isLeftMouseButton(e) && !unlocked && magRoundCount.get() == 0 && cocked) {
			try {
				SoundManager Dryfire = new SoundManager(Sound.DRYFIRE, false);
				Dryfire.play();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
			pressaction.set(false);
			cocked = false;
		}

		// Only fire if player fully reloaded and has bullets left
		if (SwingUtilities.isLeftMouseButton(e) && chambered && !unlocked && magRoundCount.get() > 0 && cocked) {
			try {
				SoundManager Fire = new SoundManager(Sound.FIRE, false);
				Fire.play();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
			releaseaction.set(true);
			pressaction.set(false);
			chambered = false;
			cocked = false;
			int roundCount = magRoundCount.get();
			magRoundCount.set(roundCount - 1);
			anims.PlayAnimation(Anim.FIRE, true);
			justFired = true;
			if (BAOLauncher.Debug) {
				chambered = true;
				magRoundCount.set(roundCount + 1);
			}

			// Locks/unlocks the bolt depending on its current state
			// Locks if bolt is closed and unlocked
			// Unlocks if bolt is locked
		} else if (SwingUtilities.isRightMouseButton(e)) {
			if (closed) {
				if (unlocked) {
					unlocked = false;
					anims.PlayAnimation(Anim.LOCK, true);
					SoundManager Lock;
					try {
						Lock = new SoundManager(Sound.LOCK, false);
						Lock.play();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if (almostChambered) {
						almostChambered = false;
						chambered = true;
					}
				} else {
					unlocked = true;
					SoundManager Unlock;
					anims.PlayAnimation(Anim.UNLOCK, false);
					try {
						Unlock = new SoundManager(Sound.UNLOCK, false);
						Unlock.play();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	// Opens/closes chamber depending on its state
	// Opens if bolt is unlocked and closed
	// Closes if bolt is open
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (gameEnded) {
			return;
		}
		// Ignores PGUP and PGDN key scrolling
		if (unlocked && e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			currentScrollAmount += e.getUnitsToScroll();
			// fullScrollAmount is the average of the two tuned values at launch
			if (Math.abs(currentScrollAmount) >= fullScrollAmount) {
				if (currentScrollAmount >= 0 && closed) {
					closed = false;
					cocked = true;
					mwdownaction.set(true);
					SoundManager Open;
					if (justFired) {
						SoundManager Eject;
						try {
							Eject = new SoundManager(Sound.EJECT, false);
							Eject.play();
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							e1.printStackTrace();
						}
						anims.PlayAnimation(Anim.OPEN, false);
						justFired = false;
					} else if (magRoundCount.get() > 0) {
						anims.PlayAnimation(Anim.OPENFULLNOSHOT, false);
					} else {
						anims.PlayAnimation(Anim.OPENEMPTY, false);
					}
					try {
						Open = new SoundManager(Sound.OPEN, false);
						Open.play();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
				} else if (currentScrollAmount < 0 && !closed) {
					almostChambered = true;
					closed = true;
					mwdownaction.set(false);
					SoundManager Close;
					anims.PlayAnimation(Anim.CLOSE, false);
					try {
						Close = new SoundManager(Sound.CLOSE, false);
						Close.play();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
				currentScrollAmount = 0;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}