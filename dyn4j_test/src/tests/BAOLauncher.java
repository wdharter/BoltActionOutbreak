package tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;
public class BAOLauncher {
	public static void main(String[] args) {
		BAOSimulationFrame game = new BAOSimulationFrame("Bolt Action Outbreak", 10);
		PlayerGameObject player = new PlayerGameObject(0, game, "player", game.camera);
		EnemySpawner spawner = new EnemySpawner(game, player);
		game.run();
	}
}

class EnemySpawner implements ActionListener{
	BAOSimulationFrame game;
	PlayerGameObject player;
	private int delay = 1000;
	protected Timer timer;
	
	public EnemySpawner(BAOSimulationFrame game, PlayerGameObject player) {
		this.game = game;
		this.player = player;
		timer = new Timer(delay, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e)
	{
		Random r = new Random();
	    int x = 40;
	    int y = 30;
		for(int i = 0; i < 1; i++) {
			int side = r.nextInt(3);
			switch (side){
				case 0:
					//top
					x = r.nextInt(80) - 40;
					y = 30;
					break;
				case 1:
					//bottom
					x = r.nextInt(80) - 40;
					y = -30;
					break;
				case 2:
					//left
					y = r.nextInt(60) - 30;
					x = -40;
					break;
				case 3:
					//right
					y = r.nextInt(60) - 30;
					x = 40;
					break;
			}
			new ZombieGameObject(1, game, "zombie1", x, y, player);
		}
	}
}
