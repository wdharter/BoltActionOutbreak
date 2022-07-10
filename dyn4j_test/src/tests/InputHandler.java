package tests;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputHandler extends KeyAdapter implements MouseListener{
	AtomicBoolean waction;
	AtomicBoolean aaction;
	AtomicBoolean saction;
	AtomicBoolean daction;
	AtomicBoolean clickaction;
	public InputHandler(AtomicBoolean w, AtomicBoolean a, AtomicBoolean s, AtomicBoolean d, AtomicBoolean click) {
		waction = w;
		aaction = a;
		saction = s;
		daction = d;
		clickaction = click;
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		clickaction.set(true);	
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
