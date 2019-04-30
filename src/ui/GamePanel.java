package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;

import game.Game;
import game.Player;

public class GamePanel extends javax.swing.JPanel implements KeyListener{

	private static final long serialVersionUID = 6003545036230230394L;

	int _width ;
	int _height ;
	ArrayList<Player> _players;
	BufferedImage _background;
	Player _player; 
	Timer _t;
	Game _game;
	/*
	 * contains all the information to create the game
	 */
	public GamePanel(ArrayList<Player> players, int playerID, BufferedImage background, Game game) {
		_players = players;
		_background = background;
		_width = _background.getWidth();
		_height = _background.getHeight();
		_game = game;

		_player = players.get(playerID);
		addKeyListener(this);

		setPreferredSize(new Dimension(_width,_height));
		this.setFocusable(true);
		this.requestFocus();
		this.grabFocus();
	}
	/*
	 * Repaints the game
	 */
	public void update() {
		repaint();
	}
	/*
	 * create the image to be drawn(non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(_background, 0,0, null);
		for(Player p : _players)
			p.getSprite().drawImage(g);
	}

	/*
	 * listens for the keys to be changed (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			_player.getSprite().setDirectionToTrue('w');
			break;
		case KeyEvent.VK_DOWN:
			_player.getSprite().setDirectionToTrue('s');
			break;
		case KeyEvent.VK_RIGHT:
			_player.getSprite().setDirectionToTrue('d');
			break;
		case KeyEvent.VK_LEFT:
			_player.getSprite().setDirectionToTrue('a');
			break;
		case KeyEvent.VK_W:
			_player.getSprite().setDirectionToTrue('w');
			break;
		case KeyEvent.VK_S:
			_player.getSprite().setDirectionToTrue('s');
			break;
		case KeyEvent.VK_D:
			_player.getSprite().setDirectionToTrue('d');
			break;
		case KeyEvent.VK_A:
			_player.getSprite().setDirectionToTrue('a');
			break;
		default :
			break;
		}
	}

	/*
	 * listens for the keys being released (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			_player.getSprite().setDirectionToFalse('w');
			break;
		case KeyEvent.VK_DOWN:
			_player.getSprite().setDirectionToFalse('s');
			break;
		case KeyEvent.VK_RIGHT:
			_player.getSprite().setDirectionToFalse('d');
			break;
		case KeyEvent.VK_LEFT:
			_player.getSprite().setDirectionToFalse('a');
			break;
		case KeyEvent.VK_W:
			_player.getSprite().setDirectionToFalse('w');
			break;
		case KeyEvent.VK_S:
			_player.getSprite().setDirectionToFalse('s');
			break;
		case KeyEvent.VK_D:
			_player.getSprite().setDirectionToFalse('d');
			break;
		case KeyEvent.VK_A:
			_player.getSprite().setDirectionToFalse('a');
			break;
		default :
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}


