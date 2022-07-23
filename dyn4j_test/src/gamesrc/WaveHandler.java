package gamesrc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Timer;

// Spawns enemies in waves, changing their attributes in subsequent ones
public class WaveHandler implements ActionListener {

	BAOSimulationFrame game;
	PlayerGameObject player;
	WaveGameObject waves;
	private int delay = 1000;
	private int spawnRate = 1;
	protected Timer timer;
	public static AtomicInteger enemyAmount = new AtomicInteger(0);
	private int enemiesSpawned = 0;
	int maxEnemies = 15;
	float forceMultiplier = 1.0f;

	public WaveHandler(BAOSimulationFrame frame, PlayerGameObject player, WaveGameObject waves) {
		this.game = frame;
		this.player = player;
		this.waves = waves;
		timer = new Timer(delay, this);
		timer.start();
	}

	// Spawns a set of enemies based on current wave once timer is over
	public void actionPerformed(ActionEvent e) {
		if (enemiesSpawned <= maxEnemies) {
			Random r = new Random();
			int x = 40;
			int y = 30;
			int spawnWidth = 60;
			int spawnHeight = 30;
			for (int i = 0; i < spawnRate; i++) {
				int side = r.nextInt(4);
				switch (side) {
				case 0:
					// top
					x = r.nextInt(spawnWidth) - spawnWidth / 2;
					y = spawnHeight / 2;
					break;
				case 1:
					// bottom
					x = r.nextInt(spawnWidth) - spawnWidth / 2;
					y = -spawnHeight / 2;
					break;
				case 2:
					// left
					y = r.nextInt(spawnHeight) - spawnHeight / 4;
					x = -spawnWidth / 2;
					break;
				case 3:
					// right
					y = r.nextInt(spawnHeight) - spawnHeight / 2;
					x = spawnWidth / 2;
					break;
				}
				new ZombieGameObject(game.GetID(), game, "zombie1", x, y, player, (7.00f * forceMultiplier));
				enemyAmount.addAndGet(1);
				enemiesSpawned++;
			}
		} else if (enemyAmount.get() == 0 && waves.wave.get() == 3) {
			// If we have spawned all enemies, and are on the final wave
			game.Endgame(true);
		} else if (enemyAmount.get() == 0)
			NewWave();
	}

	// Sets wave attributes and new enemy attributes
	public void NewWave() {
		enemiesSpawned = 0;
		enemyAmount.set(0);
		waves.wave.getAndAdd(1);
		maxEnemies += 5 + (waves.wave.get() * 1);
		if (BAOLauncher.Debug)
			System.out.println("DEBUG: Max Enemies =  " + maxEnemies);
		spawnRate++;
		forceMultiplier += .5f;
	}
}
