package networking.Messages;

import java.io.Serializable;

import game.Player;

public class MessageConnect extends Message implements Serializable {
	private static final long serialVersionUID = -5397371650997054960L;
	Player _player;
	/*
	 * creates a message to be sent for connection
	 */
	public MessageConnect(MESSAGETYPE mt, Player player) {
		super(mt);
		_player = player;

	}
	/*
	 * returns the player
	 */
	public Player getPlayer() {
		return _player;
	}
}
