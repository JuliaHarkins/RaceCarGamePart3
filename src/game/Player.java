package game;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = -51111456458995069L;
	private String _name;
	private Sprite _sprite;
	private boolean _ready;
	private int _id;

	/*
	 * creates a player
	 */
	public Player(int i, String name) {
		_id = i;
		_name = name;
		_ready = false;
	}
	/*
	 * sets the players name
	 */
	public void setName(String name) {
		_name = name;
	}
	/*
	 * returns the players name
	 */
	public String getName() {
		return _name;
	}
	/*
	 * sets the players sprite
	 */
	public void setSprite(Sprite s) {
		_sprite = s;
	}
	/*
	 * gets the players sprite
	 */
	public Sprite getSprite() {
		return _sprite;
	}
	/*
	 * sets the ready state of the player
	 */
	public void setReadyState(boolean ready) {
		_ready = ready;
	}
	/*
	 * gets the ready state of the player
	 */
	public boolean getReadyState() {
		return _ready;
	}
	/*
	 * gets the players ID
	 */
	public int getID() {
		return _id;
	}
}
