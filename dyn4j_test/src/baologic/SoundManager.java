package baologic;
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
	String filePath = ".\\src\\baologic\\";
	String fileName;
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
				fileName = "Unlock";
				break;
			case OPEN:
				fileName = "Open";
				break;
			case LOAD:
				fileName = "Load";
				break;
			case CLOSE:
				fileName = "Close";
				break;
			case LOCK:
				fileName = "Lock";
				break;
			case FIRE:
				fileName = "Fire";
				break;
			case DRYFIRE:
				fileName = "Dryfire";
				break;
			case AIM:
				fileName = "Aim";
				break;
			case ZOMBIE:
				fileName = "Zombie";
				break;
			case ZOMBIE_WALK:
				fileName = "Zombie_Walk";
				break;
			case PLAYER_WALK:
				fileName = "Player_Walk";
				break;
			case DAMAGE_2_ZOMBIE:
				fileName = "Damage_2_Zombie";
				break;
			case DAMAGE_2_PLAYER:
				fileName = "Damage_2_Player";
				break;
			case IT_HAS_TO_BE:
				fileName = "It_Has_To_Be";
				break;
			case AMBIANCE:
				fileName = "Ambiance";
				break;
		}
		
		String absolutePath = filePath + fileName.concat(".wav");
		audioInputStream = AudioSystem.getAudioInputStream(new File(absolutePath).getAbsoluteFile());
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
		resetAudioStream();
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
	
	    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
	    LineUnavailableException 
		{
			String absolutePath = filePath + fileName.concat(".wav");
			audioInputStream = AudioSystem.getAudioInputStream(new File(absolutePath).getAbsoluteFile());
	        clip.open(audioInputStream);
		}
}
enum Sound{
	UNLOCK,
	OPEN,
	LOAD,
	CLOSE,
	LOCK,
	FIRE,
	DRYFIRE,
	AIM,
	ZOMBIE,
	ZOMBIE_WALK,
	PLAYER_WALK,
	DAMAGE_2_ZOMBIE,
	DAMAGE_2_PLAYER,
	IT_HAS_TO_BE,
	AMBIANCE
}
