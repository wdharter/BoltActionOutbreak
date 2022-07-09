package tests;

public class BAOLauncher {
	public static void main(String[] args) {
		BAOSimulationFrame game = new BAOSimulationFrame("Bolt Action Outbreak", 10);
		new PlayerGameObject(0, game, "player");
		game.run();
	}
}
