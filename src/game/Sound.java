package game;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Sound {

	Clip _sound;
	String _fileLocation;
	/*
	 * creates the sound form the file location
	 */
	public Sound(String fileLocation) {
		_fileLocation = fileLocation;
		//attempts to create the sound
		try 
		{
			System.out.println("you have started");
			_sound = AudioSystem.getClip();
			InputStream input = this.getClass().getResourceAsStream(fileLocation);
			InputStream buffer = new BufferedInputStream(input);
			AudioInputStream ais = AudioSystem.getAudioInputStream(buffer);
			_sound.open(ais);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		
	}
	/*
	 * plays the sound if it isn't already playing
	 */
	public void playSound() {
		if(!_sound.isRunning()) 
		{
			_sound.setFramePosition(0);
			_sound.start();
		}
	}
}
