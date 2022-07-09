package tests;

public class BAOLauncher {
	public static void main(String[] args) {
		BAOSimulationFrame game = new BAOSimulationFrame("Bolt Action Outbreak", 10);
		PlayerGameObject player = new PlayerGameObject(0, game, "player");
		new ZombieGameObject(1, game, "zombie1", -4.2, 4, player);

		new ZombieGameObject(1, game, "zombie1", -5.2, 4, player);

		new ZombieGameObject(1, game, "zombie1", -1.2, 4, player);

		new ZombieGameObject(1, game, "zombie1", 4.2, 4, player);
		game.run();
	}
}
