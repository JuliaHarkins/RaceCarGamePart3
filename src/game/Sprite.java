package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Sprite implements Serializable{

	private static final long serialVersionUID = 8709635085798658972L;
	//coordinates
	private int _x;
	private int _y;

	private double _speed;
	private double _rotation;

	private boolean _right;
	private boolean _left;
	private boolean _decelerate;
	private boolean _accelerate;

	private Sound _crashSound;


	static final Color _grass = new Color(30,186,30);

	//the current image in the array
	int _currentImage;



	//Array of all images within the sprite
	BufferedImage[] _images;
	BufferedImage _background;
	//location of the sprites images
	String _folderPath;

	/**
	 * The creation of the sprite from the path
	 * in which the sprites images are kept
	 * @param folderPath
	 */
	public Sprite(String folderPath, int x, int y) {
		_x = x;
		_y = y;
		_folderPath = folderPath;
		_currentImage = 0;
		setImages();
		_crashSound = null;
	}
	public void setBackground(BufferedImage background) {
		_background = background;
	}
	/*
	 * sets the direction to true based on the input used 
	 */
	public void setDirectionToTrue(char key) {
		switch(key) {
		case 'w':
			_accelerate=true;
			break;
		case 'a':

			_left=true;
			break;
		case 's':
			_decelerate=true;
			break;
		case 'd':
			_right=true;
			break;

		}
	}
	/*
	 * sets the direction to false
	 */
	public void setDirectionToFalse(char key) {
		switch(key) {
		case 'w':
			_accelerate=false;
			break;
		case 'a':
			_left=false;
			break;
		case 's':
			_decelerate=false;
			break;
		case 'd':
			_right=false;
			break;

		}
	}
	/***
	 * checks if the sprite needs updated
	 */
	public void update(ArrayList<Player> players, int id) {
		double newX, newY = 0;

		//updates the speed
		if(_accelerate && !_decelerate) {
			if(_speed <6)
				_speed += 1;
		}
		else if(_decelerate && !_accelerate) {
			if(_speed > -3)
				_speed -=1 ;
		}
		else {
			_speed*=0.9;

			if(_speed < 0 &&  _speed>-.5) {
				_speed =0;
			}
			if(_speed > 0 && _speed < .5) {
				_speed =0;
			}
		}

		if(_right && !_left) {
			_rotation += 8;
			_rotation %= 360;

		}
		else if(_left && !_right) {
			_rotation -= 8;
			if(_rotation <0)
				_rotation = 360-_rotation;
		}

		//sets the new x and y assuming there are no crashes
		newY = _speed  * Math.sin(Math.toRadians(_rotation+90));
		newX = _speed  * Math.cos(Math.toRadians(_rotation+90));

		//checks if the car has been hit and plays the crash sound if it has.
		if(players.size()>1)
			for(Player p: players) {
				if(p.getID() != id) {
					if(!OffTrack((int)(_x +newX), (int)(_y +newY) )&& !CrashCheck((int)(_x +newX), (int)(_y +newY),p.getSprite().getX() , p.getSprite().getY())) {
						_y += newY;
						_x += newX;
					}
					else {
						_speed = 0;
						if(_crashSound !=null) {
							_crashSound.playSound();
						}
						break;
					}
				}
			}
		else {
			if(!OffTrack((int)(_x +newX), (int)(_y +newY) )) {
				_y += newY;
				_x += newX;
			}
			else {
				_speed = 0;
				if(_crashSound !=null) {
					_crashSound.playSound();
				}				
			}
		}

		//checks if the image needs updated
		double segment = 360/16;
		_currentImage = (int)(_rotation/segment);
		if(_currentImage>15)
			_currentImage =15;



	}
	/*
	 * checks if the car has gone off track
	 */
	private boolean OffTrack(int updatedX, int updatedY){
		int midX = (_images[_currentImage].getWidth()/2) +updatedX;
		int midY = (_images[_currentImage].getHeight()/2) +updatedY;

		boolean result = false;
		int colourAsInt = _background.getRGB(midX, midY);
		Color colour = new Color(colourAsInt);

		if(colour.equals(_grass)) {
			result = true;
		}
		return result;
	}
	public void setXYAndImage(int x, int y, int image) {
		_x = x; 
		_y = y;
		_currentImage= image;
	}

	/*
	 * checks if the car has crashed using the new x & y with the other cars x  y
	 */
	public boolean CrashCheck(int updatedX, int updatedY, int otherCarX, int otherCarY){
		boolean crashed =false;
		int midX = (_images[_currentImage].getWidth()/2) +updatedX;
		int midY = (_images[_currentImage].getHeight()/2) +updatedY;

		int otherCarMidX = (_images[_currentImage].getWidth()/2) +otherCarX;
		int otherCarMidY = (_images[_currentImage].getHeight()/2) +otherCarY;

		int distanceX = midX - otherCarMidX;
		int distanceY = midY - otherCarMidY;
		if((distanceX >-20 && distanceX <20) && ( distanceY >-20 && distanceY <20)) {
			crashed =true;
		}
		return crashed;
	}

	/*
	 * returns the x value
	 */
	public int getX() {
		return _x;
	}
	/* 
	 *returns the y value
	 */
	public int getY() {
		return _y;
	}
	/*
	 * gets the current image
	 */
	public int getImage() {
		return _currentImage;
	}

	/**
	 * draws the image into the main panel
	 * the current image @param g
	 */
	public void drawImage(Graphics g) {
		try {
			g.drawImage(_images[_currentImage], _x, _y, null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * sets the array full of images of the sprite
	 */
	private void setImages() {
		_images = new BufferedImage[16];
		for(int i = 0; i <16; i++) {
			_images[i] = getImage(i+".png");
		}
	}

	/**
	 * retrieves individual images and returns them
	 * the file name of the image @param fileName
	 * returns the images @return
	 */
	private BufferedImage getImage(String fileName) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(this.getClass().getResourceAsStream(_folderPath + "/"+ fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}
	public void SetCrashSound(Sound crash) {
		_crashSound = crash;
	}
	public String getFolderPath() {
		return _folderPath;
	}
}
