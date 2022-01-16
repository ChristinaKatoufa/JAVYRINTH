package quiz;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Quiz audio
 * Correct/Wrong answers
 * Clock ticking
 */
class Sound {
	public static final int CORRECT = 0;
	public static final int WRONG = 1;
	public static final int TICKTOCK = 2;
	Clip clip;
	URL soundURL[] = new URL[3];

	public Sound() {
		soundURL[CORRECT] = getClass().getResource("/quiz/sounds/correct.wav");
		soundURL[WRONG] = getClass().getResource("/quiz/sounds/wrong.wav");
		soundURL[TICKTOCK] = getClass().getResource("/quiz/sounds/ticktock.wav");

	}

	private void setFile(int soundID) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[soundID]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
		}
	}

	private void playOnce(int soundID) {
		setFile(soundID);
		clip.start();
	}
	
	private void playLoop(int soundID) {
		playOnce(soundID);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		if (clip!=null) clip.stop();
	}

	public void play(int soundID) {
		// Plays a new sound. If it is sound FX playOnce, if it is music playLoop.
		// If already playing a clip the new clip will play over it.
		switch (soundID) {
		case WRONG:
			playOnce(soundID);
			break;
		case CORRECT:
			playOnce(soundID);
			break;
		case TICKTOCK:
			playLoop(soundID);
			break;

		}
	}
	/**
	 * Waits for previous clip to finish and then starts a clip
	 * If the previous clip loops you should stop and then call play
	 * @param soundID
	 */
	public void playNext(int soundID) {
		// https://stackoverflow.com/questions/557903/how-can-i-wait-for-a-java-sound-clip-to-finish-playing-back
		while (this.clip.getMicrosecondLength() != this.clip.getMicrosecondPosition()) {
			// Do Nothing
		}
		play(soundID);
	}
}

