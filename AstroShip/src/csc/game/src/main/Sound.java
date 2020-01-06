package csc.game.src.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.*;

/**
 * Class that reads sound files and plays them.
 */
public class Sound {

	static int f, d;
	private static Clip musicClip, fxClip;
	private static int frame;
	public static int fxD;

	/**
	 * Loops the clip forever or until stopped
	 */
	public static void loop(String soundFileName) {
		try {
			// Allows the file to be read in JAR
			URL url = Main.class.getClassLoader().getResource(soundFileName);
			// Set up an ais from the file
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(url);
			// Obtain a clip
			musicClip = AudioSystem.getClip();
			// Open the clip and load the ais
			musicClip.open(audioInputStream);
			// Set the volume for the clip
			setVolume(musicClip, d);
			// Clip starts from a certain frame (either from the start or where
			// it stopped last time)
			musicClip.setFramePosition(getFrame());
			// Loop
			musicClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays the clip once or until stopped.
	 */
	public static void play(String soundFileName) {
		try {
			// Allows the file to be read in JAR
			URL url = Main.class.getClassLoader().getResource(soundFileName);
			// Set up an ais from the file
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(url);
			// Obtain a clip
			fxClip = AudioSystem.getClip();
			// Open the clip and load the ais
			fxClip.open(audioInputStream);
			// Set the volume for the clip
			setVolume(fxClip, fxD);
			// Start playing
			fxClip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the clip.
	 */
	public static void stopLoop() {
		// Save the frame position
		setFrame(musicClip.getFramePosition());
		musicClip.stop();
	}

	public static void stop() {
		fxClip.stop();
	}

	/**
	 * Increase volume for sound effects.
	 */
	public static void increaseFxVolume() {
		// Won't go higher than 4
		if (fxD < 4)
			fxD += 4;
		// Checks if the sound is "muted" (just very low)
		if (fxD < -32)
			fxD = -28;
	}

	/**
	 * Decrease volume for sound effects.
	 */
	public static void decreaseFxVolume() {
		if (fxD > -28)
			fxD -= 4;
		// Jumps to -50 to "mute"
		else
			fxD = -50;
	}

	/**
	 * Increase volume for the music
	 */
	public static void increaseMusicVolume() {
		// Won't go higher than 4
		if (d < 4)
			d += 4;
		// Checks if the sound is "muted" (just very low)
		if (d < -32)
			d = -28;
	}

	/**
	 * Decrease volume for the music.
	 */
	public static void decreaseMusicVolume() {
		if (d > -28)
			d -= 4;
		// Jumps to -50 to "mute"
		else
			d = -50;
	}

	/**
	 * Sets the volume for the clip
	 */
	private static void setVolume(Clip clip, float vol) {
		if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl volume = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(vol);
		}
	}

	/**
	 * Getter for frame.
	 */
	public static int getFrame() {
		return frame;
	}

	/**
	 * Setter for frame.
	 */
	public static void setFrame(int frame) {
		Sound.frame = frame;
	}

}
