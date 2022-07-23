package gamesrc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicInteger;

public class WaveGameObject extends GameObject {

	public AtomicInteger wave = new AtomicInteger();

	public WaveGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);
		wave.set(1);
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		initialized = true;
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		Font font = new Font("Chiller", Font.BOLD, -50);
		g.scale(-1.0, 1);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("Wave " + Integer.toString(wave.get()), (int) (frame.getWidth() / -2.7),
				(int) (frame.getHeight() / -2.40f));
		g.scale(-1.0, 1);
	}

	@Override
	public void handleEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}