// SOURCE: https://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
package gamesrc;

import java.awt.image.BufferedImage;

public class Frame {

	private BufferedImage frame;
	private int duration;

	public Frame(BufferedImage frame, int duration) {
		this.frame = frame;
		this.duration = duration;
	}

	public BufferedImage getFrame() {
		return frame;
	}

	public void setFrame(BufferedImage frame) {
		this.frame = frame;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
