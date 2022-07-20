package gamesrc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Timer;

public class WaveHandler implements ActionListener {

	BAOSimulationFrame game;
	PlayerGameObject player;
	private int delay = 1000;
	protected Timer timer;
	public static AtomicInteger enemyAmount = new AtomicInteger(0);
	private int enemiesSpawned = 0;
	int maxEnemies = 40;
	public WaveHandler(BAOSimulationFrame game, PlayerGameObject player) {
		this.game = game;
		this.player = player;
		game.SetWaves(this);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e)
	{
		if(enemiesSpawned <= maxEnemies) {
			Random r = new Random();
		    int x = 40;
		    int y = 30;
		    int spawnWidth = 60;
		    int spawnHeight = 30;
			for(int i = 0; i < 1; i++) {
				int side = r.nextInt(4);
				switch (side){
					case 0:
						//top
						x = r.nextInt(spawnWidth) - spawnWidth/2;
						y = spawnHeight/2;
						break;
					case 1:
						//bottom
						x = r.nextInt(spawnWidth) - spawnWidth/2;
						y = -spawnHeight/2;
						break;
					case 2:
						//left
						y = r.nextInt(spawnHeight) - spawnHeight/4;
						x = -spawnWidth/2;
						break;
					case 3:
						//right
						y = r.nextInt(spawnHeight) - spawnHeight/2;
						x = spawnWidth/2;
						break;
				}
				new ZombieGameObject(game.GetID(), game, "zombie1", x, y, player);
				enemyAmount.addAndGet(1);
				enemiesSpawned++;
			}
		}
		else if(enemyAmount.get() == 0) {
			// If we have spawned all enemies, and they have all been killed
			game.ResetGame();
		}
	}
	
	public void ResetWaves(PlayerGameObject player) {
		enemiesSpawned = 0;
		enemyAmount.set(0);
		this.player = player;
		timer.stop();
		timer = new Timer(delay, this);
		timer.start();
	}

}
