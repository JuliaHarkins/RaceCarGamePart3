package networking.Messages;

import java.io.Serializable;

public class MessageStart extends Message implements Serializable{

	private static final long serialVersionUID = -2484878105805992295L;
	String _bgFileLocation;
	/*
	 * sends out a message to start the game
	 */
	public MessageStart(MESSAGETYPE mt, String backgroundFileLocation) {
		super(mt);
		_bgFileLocation = backgroundFileLocation;
	}
	/*
	 * sends out the background image location
	 */
	public String getbackgroundFileLocation() {
		return _bgFileLocation;
	}


}
