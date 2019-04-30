package networking.Messages;

import java.io.Serializable;

public class MessagePlayerID extends Message implements Serializable{
	private static final long serialVersionUID = -5060079465157142816L;
	private int _playerID;
	/*
	 * creates a message with the player id
	 */
	public MessagePlayerID(MESSAGETYPE mt, int playerID) {
		super(mt);
		_playerID = playerID;
	}
	/*
	 * gets the player id
	 */
	public int getplayerID() {
		return _playerID;
	}
}
