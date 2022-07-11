package tests;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
public class BAOLauncher {
	public static void main(String[] args) {
		//LauncherFrame launcher = new LauncherFrame();
		//launcher.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//launcher.setVisible(true);
		
		//launcher will be for calibrating mouse wheel input
		
		BAOSimulationFrame game = new BAOSimulationFrame("Bolt Action Outbreak", 10);
		PlayerGameObject player = new PlayerGameObject(0, game, "player", game.camera);
		EnemySpawner spawner = new EnemySpawner(game, player);
		game.run();
	}
}


class LauncherFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private int currentPhase;
	private JLabel help;
	private JLabel number;
	private JButton rightButton;
	
	public LauncherFrame() {
		super("Calibration");
	    setSize( 260, 158 ); 
	    
		Box box = Box.createVerticalBox();
		help = new JLabel("<html>Place your finger at "
				+ "the top of the<br>scroll wheel and scroll towards<br> "
				+ "you until it is at the back.</html>");
		help.setAlignmentX(LEFT_ALIGNMENT);
		box.add(help);
		
		number = new JLabel("<html> <font size=\"+5\">0</font></html>");
		box.add(number);
		
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.setAlignmentX(LEFT_ALIGNMENT);
		box.add(buttonBox);
		
		JButton restart = new JButton();
		restart.setText("Restart");
		restart.setAlignmentX(LEFT_ALIGNMENT);
		restart.setFocusPainted(false);
		restart.setContentAreaFilled(false);
		restart.addActionListener(new ResetButton());
		buttonBox.add(restart);
		
		JButton next = new JButton();
		next.setText("Next");
		next.setAlignmentX(LEFT_ALIGNMENT);
		next.setFocusPainted(false);
		next.setContentAreaFilled(false);
		next.addActionListener(new NextButton());
		rightButton = next;
		buttonBox.add(next);
		
	    add(box, BorderLayout.CENTER);
	}
	
	private void phase1() {
		help.setText("<html>Place your finger at "
				+ "the top of the<br>scroll wheel and scroll towards<br> "
				+ "you until it is at the back.</html>");
		number = new JLabel("<html> <font size=\"+5\">0</font></html>");
		rightButton.setText("Next");
	    setSize( 260, 158 ); 
	}
	
	private void phase2() {
		help.setText("<html>Now repeat that "
				+ "but the other way</html>");
		number = new JLabel("<html> <font size=\"+5\">0</font></html>");
		rightButton.setText("Launch");
	    setSize( 260, 126 ); 
	}
	
	private class NextButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			phase2();
		}
	}
	
	private class ResetButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			phase1();
			
		}
	}
	
	private class ScrollWheelRecorder implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}

class EnemySpawner implements ActionListener{
	BAOSimulationFrame game;
	PlayerGameObject player;
	private int delay = 1000;
	protected Timer timer;
	int enemyAmount = 0;
	
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
			new ZombieGameObject((enemyAmount * 2)+1, game, "zombie1", x, y, player);
			enemyAmount++;
		}
	}
}
