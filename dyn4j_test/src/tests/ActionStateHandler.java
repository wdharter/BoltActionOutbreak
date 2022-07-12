package tests;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

public class ActionStateHandler extends KeyAdapter implements MouseListener, MouseWheelListener{
	static int fullScrollAmount;
	private int currentScrollAmount;
	private boolean locked = true;
	private boolean boltUp = false;
	private boolean chambered = true;
	private boolean almostChambered = false;
	AtomicBoolean waction;
	AtomicBoolean aaction;
	AtomicBoolean saction;
	AtomicBoolean daction;
	AtomicBoolean releaseaction;
	AtomicBoolean pressaction;
	AtomicBoolean rightpressaction;
	AtomicBoolean mwdownaction;
	AtomicBoolean mwupaction;
	public ActionStateHandler(
			AtomicBoolean w, 
			AtomicBoolean a, 
			AtomicBoolean s, 
			AtomicBoolean d, 
			AtomicBoolean release, 
			AtomicBoolean press,
			AtomicBoolean rightpress,
			AtomicBoolean mwdown,
			AtomicBoolean mwup) {
		waction = w;
		aaction = a;
		saction = s;
		daction = d;
		releaseaction = release;
		pressaction = press;
		rightpressaction = rightpress;
		mwdownaction = mwdown;
		mwupaction = mwup;
	}
	public void keyPressed(KeyEvent e) {
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
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
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
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e) && chambered) {
			pressaction.set(true);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e) && chambered) {
			try {
				SoundManager Fire = new SoundManager(Sound.FIRE);
				Fire.play();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			releaseaction.set(true);	
			pressaction.set(false);
			chambered = false;
		}
		else if(SwingUtilities.isRightMouseButton(e)) {
			if(locked) {
				if(boltUp) {
					boltUp = false;
					System.out.println("Locked");
					if(almostChambered) {
						almostChambered = false;
						chambered = true;
					}
				}else {
					boltUp = true;
					System.out.println("Unlocked");
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(boltUp && e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			currentScrollAmount += e.getUnitsToScroll();
			if(Math.abs(currentScrollAmount) >= fullScrollAmount) {
				if(currentScrollAmount >= 0 && locked) {
					System.out.println("Opened");
					locked = false;
					mwdownaction.set(true);
				}
				else if(currentScrollAmount < 0 && !locked) {
					System.out.println("Closed");
					almostChambered = true;
					locked = true;
					mwdownaction.set(false);
				}
				currentScrollAmount = 0;
			}
		}
	}
}
