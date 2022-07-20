package gamesrc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreenGameObject extends GameObject{

	boolean won;
	
	public EndScreenGameObject(int id, BAOSimulationFrame frame, String name, boolean won) {
		super(id, frame, name);
		this.won = won;
		this.frame.AddGameObject(this);
	}

	@Override
	public void initialize() {
		initialized = true;
	}

	@Override
	public void render(Graphics2D g, double elapsedTime) {
		String s1 = won? "You won!" : "You lost!";
		String s2 = won? 
				"Record your score, close the game, and relaunch to try for a higher one." 
				: "Close the game and relaunch to try again.";
		float s2x = won? 0.225f : 0.145f;

		Font font = new Font("Serif", Font.BOLD, -50);
		g.scale(-1.0, 1);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(s1, (int)(frame.getWidth() * 0.08), 0);
		font = new Font("Serif", Font.BOLD, -20);
		g.setFont(font);
		g.drawString(s2, (int)(frame.getWidth() * s2x), -40);
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
