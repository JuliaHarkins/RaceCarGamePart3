package networking.Messages;

import java.io.Serializable;
import java.util.ArrayList;

import game.Player;

public class MessageSendPlayers extends Message implements Serializable{

	ArrayList<Player> _players;
	private static final long serialVersionUID = -1894304333249646390L;

	/*
	 * sends the list of players
	 */
	public MessageSendPlayers(MESSAGETYPE mt, ArrayList<Player> players) {
		super(mt);
		_players = players;
	}
	/*
	 * gets the lsit of players
	 */
	public ArrayList<Player> getPlayers(){
		return _players;
	}

}
