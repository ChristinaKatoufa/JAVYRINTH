package game.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
	
	public static final int MUSIC = 0;
	public static final int START = 1;
	public static final int SWOOSH = 2;
	public static final int DYING = 3;
	public static final int WIN = 4;

	Clip clip;
	URL soundURL[] = new URL[10];
	
	public Audio() {
		soundURL[MUSIC] = getClass().getResource("/sounds/music.wav");
		soundURL[START] = getClass().getResource("/sounds/start.wav");
		soundURL[SWOOSH] = getClass().getResource("/sounds/swoosh.wav");
		soundURL[DYING] = getClass().getResource("/sounds/dying.wav");
		soundURL[WIN] = getClass().getResource("/sounds/win.wav");
	}
	
	private void setFile(int soundID) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[soundID]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e) {
		}
	}
	private void playLoop(int soundID) {
		playOnce(soundID);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	private void playOnce(int soundID) {
		setFile(soundID);
		clip.start();
	}
	
	public void stop() {
		// Use stop with playLoop or during clip is played. If you call stop
		// after clip finished causes Exception: Cannot invoke "javax.sound.sampled.Clip.stop()" because "this.clip" is null 
		clip.stop();
	}
	
	public void play(int soundID) {
		// Plays a new sound. If it is FX playOnce, if it is music playLoop.
		// If already playing a clip the new clip will play over it.
		switch (soundID) {
			case MUSIC: playLoop(soundID); break;
			case START: playOnce(soundID); break;
			case SWOOSH: playOnce(soundID); break;
			case DYING: playOnce(soundID); break;
			case WIN: playOnce(soundID); break;
		}
	}
	
	public void playNext(int soundID) {
		// Waits for previous clip to finish and then starts a clip
		// If the previous clip loops you should stop and then call play
		//* https://stackoverflow.com/questions/557903/how-can-i-wait-for-a-java-sound-clip-to-finish-playing-back
		while (this.clip.getMicrosecondLength() != this.clip.getMicrosecondPosition()) {
			// Do Nothing
		}
		play(soundID);
	}
}