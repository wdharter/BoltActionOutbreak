package tests;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {

	Long currentFrame;
	Clip clip;
	String status;
	AudioInputStream audioInputStream;
	Sound s;
	// .\src\tests\
	public SoundManager(Sound s)
			throws UnsupportedAudioFileException, 
			IOException, LineUnavailableException
	{
		this.s = s;
		switch(s)
		{
			case UNLOCK:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Unlock.wav").getAbsoluteFile());
				break;
			case OPEN:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Open.wav").getAbsoluteFile());
				break;
			case CLOSE:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Close.wav").getAbsoluteFile());
				break;
			case LOCK:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Lock.wav").getAbsoluteFile());
				break;
			case FIRE:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Unlock.wav").getAbsoluteFile());
				break;
			case AIM:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Aim.wav").getAbsoluteFile());
				break;
			case ZOMBIE:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Zombie.wav").getAbsoluteFile());
				break;
			case ZOMBIE_WALK:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Zombie_Walk.wav").getAbsoluteFile());
				break;
			case PLAYER_WALK:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Player_Walk.wav").getAbsoluteFile());
				break;
			case DAMAGE_2_ZOMBIE:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Damage_2_Zombie.wav").getAbsoluteFile());
				break;
			case DAMAGE_2_PLAYER:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Damage_2_Player.wav").getAbsoluteFile());
				break;
			case IT_HAS_TO_BE:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\It_Has_To_Be.wav").getAbsoluteFile());
				break;
			case AMBIANCE:
				audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Ambiance.wav").getAbsoluteFile());
				break;
		}
		
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		
		}
	    public void play() 
	    {
	        clip.start();     
	        status = "play";
	    }
	    public void pause() 
	    {
	        if (status.equals("paused")) 
	        {
	            System.out.println("audio is already paused");
	            return;
	        }
	        this.currentFrame = 
	        this.clip.getMicrosecondPosition();
	        clip.stop();
	        status = "paused";
	    }
		public void restart() throws IOException, LineUnavailableException,
		UnsupportedAudioFileException 
		{
		clip.stop();
		clip.close();
		resetAudioStream(this.s);
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
		}
	
	    public void stop() throws UnsupportedAudioFileException,
	    IOException, LineUnavailableException 
	    {
	        currentFrame = 0L;
	        clip.stop();
	        clip.close();
	    }
	
	    public void resetAudioStream(Sound s) throws UnsupportedAudioFileException, IOException,
	    LineUnavailableException 
		{
	    	switch(s)
			{
				case UNLOCK:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Unlock.wav").getAbsoluteFile());
					break;
				case OPEN:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Open.wav").getAbsoluteFile());
					break;
				case CLOSE:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Close.wav").getAbsoluteFile());
					break;
				case LOCK:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Lock.wav").getAbsoluteFile());
					break;
				case FIRE:
					audioInputStream = AudioSystem.getAudioInputStream(new File(".\\src\\tests\\Fire.wav").getAbsoluteFile());
					break;
				case AIM:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Aim.wav").getAbsoluteFile());
					break;
				case ZOMBIE:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Zombie.wav").getAbsoluteFile());
					break;
				case ZOMBIE_WALK:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Zombie_Walk.wav").getAbsoluteFile());
					break;
				case PLAYER_WALK:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Player_Walk.wav").getAbsoluteFile());
					break;
				case DAMAGE_2_ZOMBIE:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Damage_2_Zombie.wav").getAbsoluteFile());
					break;
				case DAMAGE_2_PLAYER:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Damage_2_Player.wav").getAbsoluteFile());
					break;
				case IT_HAS_TO_BE:
					audioInputStream = AudioSystem.getAudioInputStream(new File("It_Has_To_Be.wav").getAbsoluteFile());
					break;
				case AMBIANCE:
					audioInputStream = AudioSystem.getAudioInputStream(new File("Ambiance.wav").getAbsoluteFile());
					break;
			}
	        clip.open(audioInputStream);
		}
}
enum Sound{
	UNLOCK,
	OPEN,
	CLOSE,
	LOCK,
	FIRE,
	AIM,
	ZOMBIE,
	ZOMBIE_WALK,
	PLAYER_WALK,
	DAMAGE_2_ZOMBIE,
	DAMAGE_2_PLAYER,
	IT_HAS_TO_BE,
	AMBIANCE
}
