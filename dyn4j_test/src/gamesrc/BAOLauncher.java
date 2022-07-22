package gamesrc;

import javax.swing.Box;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
public class BAOLauncher {
	public static boolean Debug = false;
	public static void main(String[] args) {
		boolean skip = false;
		if(!skip) {
			LauncherFrame launcher = new LauncherFrame();
			launcher.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			launcher.setVisible(true);
		}
		else {
			new GameStarter(21, 21);
		}
	}
}

class GameStarter {
	GameStarter(int firstScrollCheck, int secondScrollCheck){
		ActionStateHandler.fullScrollAmount = (int) (Math.abs((Math.abs(firstScrollCheck) + Math.abs(secondScrollCheck))/2) * 0.85f);
		int level = 1;
		BAOSimulationFrame game = new BAOSimulationFrame("Bolt Action Outbreak", 15, level);
		ScoreBoardGameObject scoreboard = new ScoreBoardGameObject(-1, game, "scoreboard");
		AnimationManagerGameObject anims = new AnimationManagerGameObject(-2, game, "anims");
		WaveGameObject waves = new WaveGameObject(-3, game, "waves");
		PlayerGameObject player = new PlayerGameObject(game.GetID(), game, "player", game.camera, scoreboard, anims);
		new PlayerHealthGameObject(game.GetID(), game, "health");
		new WaveHandler(game, player, waves);
		game.run();
	}
}


class LauncherFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JLabel help;
	private JLabel number;
	private JButton rightButton;
	private int rotationTally;
	private int rot1;
	private JFrame frame;
	
	public LauncherFrame() {
		super("Calibration");
		frame = this;
	    setSize( 292, 158 ); 
	    this.setResizable(false);
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
		
		JButton defaults = new JButton();
		defaults.setText("Use defaults (21, 21)");
		defaults.setAlignmentX(LEFT_ALIGNMENT);
		defaults.setFocusPainted(false);
		defaults.setContentAreaFilled(false);
		defaults.addActionListener(new DefaultButton());
		buttonBox.add(defaults);
		
	    this.addMouseWheelListener(new ScrollWheelRecorder());
	    add(box, BorderLayout.CENTER);
	}
	
	private void phase1() {
		help.setText("<html>Place your finger at "
				+ "the top of the<br>scroll wheel and scroll towards<br> "
				+ "you until it is at the back.</html>");
		number.setText("<html> <font size=\"+5\">"+rotationTally+"</font></html>");
		rightButton.setText("Next");
	    setSize( 292, 158 ); 
	}
	
	private void phase2() {
		help.setText("<html>Now repeat that "
				+ "but the other way. If reloading is too hard in-game, <br>reload and try scrolling less at calibration.</html>");
		number.setText("<html> <font size=\"+5\">"+rotationTally+"</font></html>");
		rightButton.setText("Launch");
	    setSize( 300, 158 ); 
	}
	
	private class NextButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(rightButton.getText() == "Launch") {
				frame.dispose();
				new GameStarter(rot1, rotationTally);
			}
			rot1 = rotationTally;
			rotationTally = 0;
			phase2();
		}
	}
	
	private class ResetButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			rotationTally = 0;
			phase1();
		}
	}
	
	private class DefaultButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			new GameStarter(21, 21);
		}
	}
	
	private class ScrollWheelRecorder implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			rotationTally += e.getUnitsToScroll();
			number.setText("<html> <font size=\"+5\">"+rotationTally+"</font></html>");
			repaint();
		}
	}
}
