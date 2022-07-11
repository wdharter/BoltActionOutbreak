package tests;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingUtilities;

public class InputHandler extends KeyAdapter implements MouseListener, MouseWheelListener{
	static int fullScrollAmount;
	private int currentScrollAmount;
	private boolean locked = true;
	AtomicBoolean waction;
	AtomicBoolean aaction;
	AtomicBoolean saction;
	AtomicBoolean daction;
	AtomicBoolean releaseaction;
	AtomicBoolean pressaction;
	AtomicBoolean rightpressaction;
	AtomicBoolean mwdownaction;
	AtomicBoolean mwupaction;
	public InputHandler(
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
		if(SwingUtilities.isLeftMouseButton(e)) {
			pressaction.set(true);
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) {
			releaseaction.set(true);	
			pressaction.set(false);
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
		// Ensure that the scroll-wheel is being used (or arrow keys)
		if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			//in here modify some tracking variable and then actually change booleans after some check
			//that we overcame the fullScrollAmount, then reset it
			currentScrollAmount += e.getUnitsToScroll();
			if(Math.abs(currentScrollAmount) >= fullScrollAmount) {
				if(currentScrollAmount >= 0 && locked) {
					System.out.println("Unlocked");
					locked = false;
				}
				else if(currentScrollAmount < 0 && !locked) {
					System.out.println("Locked");
					locked = true;
				}
				currentScrollAmount = 0;
			}
		}
	}
}