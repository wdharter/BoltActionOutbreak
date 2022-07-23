package gamesrc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

// Draws a line that fades out over a fixed period of time, then self-destroys
public class VaporTrailGameObject extends GameObject {
	private Point start;
	private Point end;
	private float duration; // Actual time the effect should last for
	private float totalTime; // Time that has passed since line creation
	private float startingGray; // Shade of gray to start the line at
	private boolean disabled;

	public VaporTrailGameObject(int id, BAOSimulationFrame frame, String name, Point start, Point end, float duration) {
		super(id, frame, name);
		this.start = start;
		this.end = end;
		this.duration = duration;
		totalTime = 0;
		startingGray = 40;
		disabled = false;
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		initialized = true;
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		if (!disabled) {
			final double scale = frame.getScale();
			// Current gray shade is scaled to be a percentage of startingGray based on
			// a percentage of totalTime / duration, results in a fade out effect
			float currentGray = startingGray - (startingGray * (totalTime / duration));
			currentGray = currentGray < 0 ? 0 : currentGray;
			g.setColor(new Color((int) currentGray, (int) currentGray, (int) currentGray, (int) currentGray));
			g.draw(new Line2D.Double(start.x * scale, start.y * scale, end.x * scale, end.y * scale));
			totalTime += elapsedTime;
			if (totalTime > duration) {
				frame.QueueObjectToDelete(id);
				disabled = true;
			}
		}
	}

	@Override
	public void handleEvents() {
	}

	@Override
	public void destroy() {
	}

}
