package networking.Messages;

import java.io.Serializable;

public class MessagePlayerInfo extends Message implements Serializable{
	String _spriteFolder;
	int _id;
	private static final long serialVersionUID = 5614411587405909733L;
	/*
	 * sends a message with all the required player info
	 */
	public MessagePlayerInfo(MESSAGETYPE mt, String folderPath, int id) {
		super(mt);
		_messageType = mt;
		_spriteFolder = folderPath;
		_id = id;
	}
	/*
	 * gets the player ID
	 */
	public int getPlayerID() {
		return _id;	
	}
	/*
	 * gets the folder for the sprite
	 */
	public String getSpriteFolder() {
		return _spriteFolder;	
	}
}
