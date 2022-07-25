package gamesrc;

import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// SOURCE: https://www.geeksforgeeks.org/play-audio-file-using-java/ (Heavily modified and adapted code)
public class SoundManager {
	Long currentFrame;
	Clip clip;
	String status;
	String filePath = "sound/";
	String fileName;
	AudioInputStream audioInputStream;
	boolean Loop = false;
	Sound s;

	public SoundManager(Sound s, boolean Loop)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.Loop = Loop;
		this.s = s;
		switch (s) {
		case UNLOCK:
			fileName = "Unlock";
			break;
		case OPEN:
			fileName = "Open";
			break;
		case EJECT:
			fileName = "Eject";
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
		String path = filePath + fileName.concat(".wav");
		audioInputStream = AudioSystem
				.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream((path))));
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		if (Loop)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void play() {
		clip.start();
		status = "play";
	}

	public void pause() {
		if (status.equals("paused")) {
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}

	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}

	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		String path = filePath + fileName.concat(".wav");
		audioInputStream = AudioSystem
				.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream((path))));
		clip.open(audioInputStream);
		if (Loop)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}

enum Sound {
	UNLOCK, OPEN, EJECT, LOAD, CLOSE, LOCK, FIRE, DRYFIRE, AIM, ZOMBIE, ZOMBIE_WALK, PLAYER_WALK, DAMAGE_2_ZOMBIE,
	DAMAGE_2_PLAYER, IT_HAS_TO_BE, AMBIANCE
}
