package tests;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class VaporTrailGameObject extends GameObject {
	private Point start;
	private Point end;
	private float duration;
	private float totalTime;
	private float startingGray;
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
	public void initialize() {}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		if(!disabled) {
			final double scale = frame.getScale();
			float currentGray = startingGray - (startingGray * (totalTime/duration));
			System.out.println((int)currentGray);
			currentGray = currentGray < 0? 0 : currentGray;
			g.setColor(new Color((int)currentGray, (int)currentGray, (int)currentGray, (int)currentGray));
			g.draw(new Line2D.Double(
					start.x * scale, 
					start.y * scale, 
					end.x *scale, 
					end.y *scale));
			totalTime += elapsedTime;
			if(totalTime > duration) {
				frame.QueueObjectToDelete(id);
				disabled = true;
			}
		}
	}

	@Override
	public void handleEvents() {}

}
