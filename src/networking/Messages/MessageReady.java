package networking.Messages;

import java.io.Serializable;


public class MessageReady extends Message implements Serializable {
	private static final long serialVersionUID = -5060079465157142816L;
	private int _id;
	public MessageReady(MESSAGETYPE mt, int id) {
		super(mt);
		_id = id;
		_messageType = mt;

	}
	/*
	 * returns the player ID
	 */
	public int playerID() {
		return _id;
	}

}
