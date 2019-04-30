package networking.Messages;

import java.io.Serializable;

public class Message implements Serializable {

	protected MESSAGETYPE _messageType;
	private static final long serialVersionUID = -5202215874844527734L;

	public Message(MESSAGETYPE mt) {
		_messageType = mt;
	}

	public MESSAGETYPE getMessageType() {
		return _messageType;
	}
	public void setMessageType(MESSAGETYPE messageType) {
		_messageType = messageType;
	}

}
