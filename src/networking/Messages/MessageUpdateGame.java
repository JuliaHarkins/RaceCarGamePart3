package networking.Messages;

import java.io.Serializable;

public class MessageUpdateGame extends Message implements Serializable{
	int _spriteX;
	int _spriteY;
	int _playerID;
	int _image;
	private static final long serialVersionUID = -3361619968275068640L;
	/*
	 * sends all the information required to update the other player
	 */
	public MessageUpdateGame(MESSAGETYPE mt, int spriteX, int spriteY,int image, int playerID ) {
		super(mt);
		_spriteX = spriteX;
		_spriteY = spriteY;
		_playerID = playerID;
		_image = image;
	}
	/*
	 * gets the playerID
	 */
	public int getPlayerID() {
		return _playerID;
	}
	/*
	 * gets the x coordinate of the sprite
	 */
	public int getSpriteX() {
		return _spriteX;
	}
	/*
	 * gets the y coordinate for the sprite
	 */
	public int getSpriteY() {
		return _spriteY;
	}
	/*
	 * gets the image number
	 */
	public int getImage() {
		return _image;
	}

}
