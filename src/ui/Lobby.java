package ui;

import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import game.Game;
import game.ImageFiles;
import networking.Messages.*;
import game.Player;
import networking.Client;
import networking.Messages.MESSAGETYPE;
import networking.Messages.MessagePlayerInfo;
import networking.Messages.MessageReady;

import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Lobby extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btn_play;
	private JLabel lb_selectPlayer;
	private JLabel lb_selectMap;
	private JButton btn_player1Left;
	private JButton btn_playerRight;
	private JButton btn_mapLeft;
	private JButton btn_mapRight;
	private JLabel lb_player1Image;
	private JLabel lb_mapImage;
	private JLabel lb_lobbyIP;


	ArrayList<ImageFiles> _maps;
	ArrayList<ImageFiles> _cars;
	boolean _host;
	boolean _ready;
	Player _player;
	int _car;
	int _map;
	InetAddress _hostIP;
	JLabel lb_readyCount;


	/**
	 * Create the frame.
	 */
	public Lobby(ArrayList<ImageFiles> maps, ArrayList<ImageFiles> cars, InetAddress hostIP, Player player, boolean host) {

		_maps = maps;
		_cars = cars;
		_player = player;
		_map =0;
		_car = 0;
		_host = host;
		_ready = false;
		_hostIP = hostIP;
		initGUI();
	}
	private void initGUI() {
		//creating the 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 495);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 29, 0, 0, 32, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.contentPane.setLayout(gbl_contentPane);


		//getting the images
		BufferedImage car = null;
		try {
			String location = _cars.get(0).getFileLocation()+ "/0.png";
			car = ImageIO.read(this.getClass().getResourceAsStream(location));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ImageIcon playerCar = new ImageIcon(car);

		BufferedImage map = null;
		try {
			map = ImageIO.read(this.getClass().getResourceAsStream(_maps.get(_map).getFileLocation()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedImage resizedMap = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);

		//updating the size of the map
		Graphics2D g2d = (Graphics2D) resizedMap.getGraphics();
		g2d.drawImage(map, 0, 0, 100,100, null);
		ImageIcon selectedMap = new ImageIcon(resizedMap);

		this.lb_lobbyIP = new JLabel("Host IP:  " +_hostIP.getHostAddress());
		GridBagConstraints gbc_lb_lobbyIP = new GridBagConstraints();
		gbc_lb_lobbyIP.insets = new Insets(0, 0, 5, 5);
		gbc_lb_lobbyIP.gridx = 4;
		gbc_lb_lobbyIP.gridy = 1;
		this.contentPane.add(this.lb_lobbyIP, gbc_lb_lobbyIP);

		//player1 buttons
		this.lb_selectPlayer = new JLabel("Select Player "+_player.getID()+" Car");
		this.lb_selectPlayer.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lb_selectPlayer1 = new GridBagConstraints();
		gbc_lb_selectPlayer1.insets = new Insets(0, 0, 5, 5);
		gbc_lb_selectPlayer1.gridx = 13;
		gbc_lb_selectPlayer1.gridy = 1;
		this.contentPane.add(this.lb_selectPlayer, gbc_lb_selectPlayer1);

		this.btn_player1Left = new JButton("<--");
		this.btn_player1Left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_car > 0)
					_car--;
				else
					_car=_cars.size()-1;

				UpdatePlayer();
			}
		});

		this.lb_readyCount = new JLabel(Game.gameInstance().getReadyCount());
		GridBagConstraints gbc_lb_readyCount = new GridBagConstraints();
		gbc_lb_readyCount.insets = new Insets(0, 0, 5, 5);
		gbc_lb_readyCount.gridx = 4;
		gbc_lb_readyCount.gridy = 2;
		this.contentPane.add(this.lb_readyCount, gbc_lb_readyCount);

		GridBagConstraints gbc_btn_playerLeft = new GridBagConstraints();
		gbc_btn_playerLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btn_playerLeft.gridx = 12;
		gbc_btn_playerLeft.gridy = 2;
		this.contentPane.add(this.btn_player1Left, gbc_btn_playerLeft);
		this.lb_player1Image = new JLabel();
		lb_player1Image.setIcon(playerCar);

		GridBagConstraints gbc_lb_playerImage = new GridBagConstraints();
		gbc_lb_playerImage.insets = new Insets(0, 0, 5, 5);
		gbc_lb_playerImage.gridx = 13;
		gbc_lb_playerImage.gridy = 2;
		this.contentPane.add(this.lb_player1Image, gbc_lb_playerImage);


		this.btn_playerRight = new JButton("-->");
		this.btn_playerRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(_car < _cars.size()-1)
					_car++;
				else
					_car=0;

				UpdatePlayer();
			}
		});

		GridBagConstraints gbc_btn_playerRight = new GridBagConstraints();
		gbc_btn_playerRight.insets = new Insets(0, 0, 5, 5);
		gbc_btn_playerRight.gridx = 14;
		gbc_btn_playerRight.gridy = 2;
		this.contentPane.add(this.btn_playerRight, gbc_btn_playerRight);

		if(_host) {
			//map selection

			this.lb_selectMap = new JLabel("Select Map");
			GridBagConstraints gbc_lb_selectMap = new GridBagConstraints();
			gbc_lb_selectMap.insets = new Insets(0, 0, 5, 5);
			gbc_lb_selectMap.gridx = 13;
			gbc_lb_selectMap.gridy = 4;
			this.contentPane.add(this.lb_selectMap, gbc_lb_selectMap);

			this.btn_mapLeft = new JButton("<--");
			this.btn_mapLeft.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if(_map >0)
						_map--;
					else
						_map= _maps.size()-1;

					UpdateMap();
				}
			});

			GridBagConstraints gbc_btn_mapLeft = new GridBagConstraints();
			gbc_btn_mapLeft.insets = new Insets(0, 0, 5, 5);
			gbc_btn_mapLeft.gridx = 12;
			gbc_btn_mapLeft.gridy = 5;
			this.contentPane.add(this.btn_mapLeft, gbc_btn_mapLeft);

			this.lb_mapImage = new JLabel();
			lb_mapImage.setIcon(selectedMap);

			GridBagConstraints gbc_lb_mapImage = new GridBagConstraints();
			gbc_lb_mapImage.fill = GridBagConstraints.HORIZONTAL;
			gbc_lb_mapImage.insets = new Insets(0, 0, 5, 5);
			gbc_lb_mapImage.gridx = 13;
			gbc_lb_mapImage.gridy = 5;
			this.contentPane.add(this.lb_mapImage, gbc_lb_mapImage);

			this.btn_mapRight = new JButton("-->");
			this.btn_mapRight.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(_map < _maps.size() -1)
						_map++;
					else
						_map=0;
					UpdateMap();
				}
			});

			GridBagConstraints gbc_btn_mapRight = new GridBagConstraints();
			gbc_btn_mapRight.insets = new Insets(0, 0, 5, 5);
			gbc_btn_mapRight.gridx = 14;
			gbc_btn_mapRight.gridy = 5;
			this.contentPane.add(this.btn_mapRight, gbc_btn_mapRight);
		}

		String btntxt;
		if(_host)
			btntxt = "Start!";
		else
			btntxt = "Ready!";
		//play
		this.btn_play = new JButton(btntxt);
		this.btn_play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setUpGame();
			}
		});

		GridBagConstraints gbc_btn_play = new GridBagConstraints();
		gbc_btn_play.insets = new Insets(0, 0, 5, 5);
		gbc_btn_play.gridx = 9;
		gbc_btn_play.gridy = 7;
		this.contentPane.add(this.btn_play, gbc_btn_play);
	}
	private void setUpGame() {

		String spriteFolder=_cars.get(_car).getFileLocation();
		Game.gameInstance().setPlayer(_player.getID(), spriteFolder);
		BufferedImage background=null;
		_player.setReadyState(true);
		Client.getInstance().send(new MessagePlayerInfo(MESSAGETYPE.PLAYERINFO, _player.getSprite().getFolderPath(), _player.getID() ));

		if(_host) {
			boolean startGame = true;
			try {
				background = ImageIO.read(this.getClass().getResourceAsStream(_maps.get(_map).getFileLocation()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			for(Player p :Game.gameInstance().getPlayers())
				if(!p.getReadyState())
					startGame =false;
			if(startGame) {
				Game.gameInstance().startGame(background);
				Client.getInstance().send(new MessageStart(MESSAGETYPE.START, _maps.get(_map).getFileLocation()));
			}
			else
				JOptionPane.showMessageDialog(null, "waiting for players to ready up." + Game.gameInstance().getReadyCount());
		}
		else
		{
			Client.getInstance().send(new MessageReady(MESSAGETYPE.READY, _player.getID()));
			Game.gameInstance().updateReadyLable();
			this.btn_play.setVisible(false);
		}

	}

	private void UpdatePlayer() {
		BufferedImage car = null;
		try {
			car = ImageIO.read(this.getClass().getResourceAsStream(_cars.get(_car).getFileLocation()+ "/0.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ImageIcon playerCar = new ImageIcon(car);
		lb_player1Image.setIcon(playerCar);

	}
	private void UpdateMap() {
		BufferedImage map = null;
		try {
			map = ImageIO.read(this.getClass().getResourceAsStream(_maps.get(_map).getFileLocation()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedImage resizedMap = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) resizedMap.getGraphics();
		g2d.drawImage(map, 0, 0, 100,100, null);
		ImageIcon selectedMap = new ImageIcon(resizedMap);
		lb_mapImage.setIcon(selectedMap);

	}
	public void updateReadyLB(String txt) {
		this.lb_readyCount.setText(txt);
	}
}

