package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import networking.Client;
import networking.Server;
import networking.Messages.MESSAGETYPE;
import networking.Messages.MessageConnect;
import networking.Messages.MessageSendPlayers;
import networking.Messages.MessageUpdateGame;
import ui.*;

public class Game {

	static Game _instance;
	ArrayList<ImageFiles> _maps;
	ArrayList<ImageFiles> _cars;
	String _player1SpriteFolder;
	String _player2SpriteFolder;
	BufferedImage _background;
	ArrayList<Player> _players;
	int _id;
	Sound _crash;
	Lobby _l;
	GamePanel _gamePanel;
	Timer _t;
	boolean _playable;
	boolean _host;
	InetAddress _ip;

	/*
	 * manages the game and adds the resources
	 */
	private Game() {
		_maps = new ArrayList<ImageFiles>();
		_players = new ArrayList<Player>();
		ImageFiles map1 = new ImageFiles("Map 1", "/maps/0.png");
		ImageFiles map3 = new ImageFiles("Map 3", "/maps/2.png");

		_maps.add(map1);
		_maps.add(map3);

		_cars = new ArrayList<ImageFiles>();

		ImageFiles car1 = new ImageFiles("Red Car", "/carRed");
		ImageFiles car2 = new ImageFiles("Blue Car", "/carBlue");

		_cars.add(car1);
		_cars.add(car2);

		_crash = new Sound("/sounds/CrashWAVRenamed.wav");
	}
	/*
	 * Singleton that creates the game only once
	 */
	public static Game gameInstance() {
		if (_instance == null)
			_instance = new Game();
		return _instance;
	}
	/*
	 * gets the list of players
	 */
	public ArrayList<Player> getPlayers() {
		return _players;
	}
	/*
	 * gets details to load the lobby
	 */
	public void InitializeGame(boolean host, InetAddress localhost) {
		_host = host;
		_ip = localhost;
	}
	/*
	 * loads up the lobby 
	 */
	public void loadLobby(String userName) {
		if (_host)
			Server.getInstance().startSever(6789);

		if (Client.getInstance().connectToSever(_ip)) {
			try {

				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int id = Client.getInstance().getplayerID();
			_id = 0;
			System.out.println("player id = " + id);

			Player p = new Player(id, _player1SpriteFolder);
			p.setName(userName);
			Client.getInstance().send(new MessageConnect(MESSAGETYPE.CONNECT, p));
			_players.add(p);
			_l = new Lobby(_maps, _cars, _ip, p, _host);
			_l.pack();
			_l.setVisible(true);

			_t = new Timer(32, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CallUpdate();
				}
			});

			_t.setRepeats(true);
		} else
			JOptionPane.showMessageDialog(null, "Unable to connect");
	}

	/*
	 * adds the sprite for the player who's id has been given
	 */
	public void setPlayer(int id, String spriteFolder) {
		for (Player p : _players) {
			if (p.getID() == id) {
				p.setSprite(new Sprite(spriteFolder, (200 +(30*id)),100));
				p.getSprite().SetCrashSound(_crash);
				break;
			}
		}
	}
	/*
	 * started the game using the provided background
	 */
	public void startGame(BufferedImage background) {
		_background = background;
		if(!_host)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		for (Player p : _players)
			p.getSprite().setBackground(_background);

		JFrame frame = new JFrame();
		GamePanel panel = new GamePanel(_players, _id, _background, this);
		SetGamePanel(panel);
		StartTimer();

		panel.setBackground(Color.white);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	/*
	 * sets up the game pannel
	 */
	public void SetGamePanel(GamePanel gamePanel) {
		_gamePanel = gamePanel;
	}
	/*
	 * starts the timer
	 */
	public void StartTimer() {
		_t.start();
	}
	/*
	 * sets a player as ready
	 */
	public void setPlayerReady(int playerID) {
		_players.get(playerID-1).setReadyState(true);
		updateReadyLable();	
	}

	/*
	 * updates the players sprite and sends this information to the other players
	 */
	public void CallUpdate() {
		//updates the sprites speed and location
		_players.get(_id).getSprite().update(_players, _players.get(_id).getID());
		_gamePanel.update();

		int x = _players.get(_id).getSprite().getX();
		int y = _players.get(_id).getSprite().getY();
		int image = _players.get(_id).getSprite().getImage();
		int id = _players.get(_id).getID();
		//sends the updated location
		Client.getInstance().send((new MessageUpdateGame(MESSAGETYPE.UPDATEGAME, x, y, image, id)));
	}
	/*
	 *updates the other player 
	 */
	public void updatePlayer(MessageUpdateGame mug) {
		for(Player  p : _players)
			if(p.getID() == mug.getPlayerID())
				//updates the other players using the sent message
				p.getSprite().setXYAndImage(mug.getSpriteX(), mug.getSpriteY(), mug.getImage());;
	}
	/*
	 * gets the count of the players that are ready
	 */
	public String getReadyCount() {
		int i = 0;
		for (Player p : _players)
			if (p.getReadyState())
				i++;
		String count =i + "/" + _players.size() + " Players Ready";
		return count;
	}
	/*
	 * updates the ready label in the lobby
	 */
	public void updateReadyLable() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_l.updateReadyLB(getReadyCount());
	}
	/*
	 * adds a new player
	 */
	public void addPlayer(Player p) {
		boolean add= true;
		for(Player knownPlayer: _players)
			if(knownPlayer.getID()==p.getID())
				add = false;
		if(add)
			_players.add(p);
		if(_host) {
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Client.getInstance().send(new MessageSendPlayers(MESSAGETYPE.SENDPLAYERS,_players));
		}
		updateReadyLable();		
	}
	/*
	 * adds all known players
	 */
	public void addPlayers(ArrayList<Player> players) {
		for(Player sentPlayer : players) {
			boolean exists = false;
			for(Player knownPlayer: _players)
				if(knownPlayer.getID()==sentPlayer.getID())
					exists = true;
			if(!exists)
				_players.add(sentPlayer);
		}
		if(_host)
			Client.getInstance().send(new MessageSendPlayers(MESSAGETYPE.SENDPLAYERS, _players));
		updateReadyLable();

		for(Player p: _players) {
			System.out.println(p.getID());
		}
	}
}
