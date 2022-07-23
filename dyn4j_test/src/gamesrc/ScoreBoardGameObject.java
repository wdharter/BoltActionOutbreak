package gamesrc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreBoardGameObject extends GameObject {

	public AtomicInteger score = new AtomicInteger();

	public ScoreBoardGameObject(int id, BAOSimulationFrame frame, String name) {
		super(id, frame, name);
		score.set(0);
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		initialized = true;
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		Font font = new Font("Serif", Font.BOLD, -50);
		g.scale(-1.0, 1);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(score.get()), 0, (int) (frame.getHeight() / 2.65f));
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
