package networking;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.imageio.ImageIO;

import game.Game;
import networking.Messages.MESSAGETYPE;
import networking.Messages.Message;
import networking.Messages.MessageConnect;
import networking.Messages.MessagePlayerID;
import networking.Messages.MessagePlayerInfo;
import networking.Messages.MessageReady;
import networking.Messages.MessageSendPlayers;
import networking.Messages.MessageStart;
import networking.Messages.MessageUpdateGame;

public class Client extends Thread{
	static Client _instance;
	ObjectOutputStream m_oos;
	ObjectInputStream m_ois;
	InetAddress m_serverIP;
	Socket m_socket;
	int _playerID;
	boolean m_connected;

	/*
	 * creates the client used by the player
	 */
	private Client() {}

	/*
	 * creates the instance of the client
	 */
	public static Client getInstance() {
		if(_instance == null)
			_instance = new Client();

		return _instance;
	}
	/*
	 * connects the client to the server
	 */
	public boolean connectToSever(InetAddress serverIP) {
		m_serverIP = serverIP;

		try {
			m_socket = new Socket(m_serverIP, 6789);
			m_socket.setSoTimeout(5000);

			//sets the output steam and th einput stream
			if(m_oos == null) 
				m_oos = new ObjectOutputStream(m_socket.getOutputStream());
			if(m_ois ==null) 
				m_ois = new ObjectInputStream(m_socket.getInputStream());

			this.start();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/*
	 * sends the messages over to the server session
	 */
	public void send(Message m) {
		try {
			m_oos.writeObject(m);
			m_oos.flush();
		}
		catch(Exception e){

		}
	}
	/*
	 * runs the client (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {

		//Listening to stuff
		boolean runWhile= true;
		Message m = null;
		System.out.println("client Run");

		//Change this to while not shutdown
		while(runWhile) {
			m = null;
			try {
				m = (Message) m_ois.readObject();
				MESSAGETYPE message = m.getMessageType();

				// checks what message has been received and performs the action in accordance 
				switch(message) {
				case  CONNECT: 
					MessageConnect mc = (MessageConnect) m;
					Game.gameInstance().addPlayer(mc.getPlayer());
					break;				
				case PLAYERINFO:
					MessagePlayerInfo mpi = (MessagePlayerInfo) m;
					Game.gameInstance().setPlayer(mpi.getPlayerID(), mpi.getSpriteFolder());
					break;
				case READY:
					MessageReady mr = (MessageReady) m;
					Game.gameInstance().setPlayerReady(mr.playerID());
					break;
				case START:
					MessageStart ms = (MessageStart) m;
					String location = ms.getbackgroundFileLocation();
					BufferedImage background = ImageIO.read(this.getClass().getResourceAsStream(location));
					Game.gameInstance().startGame(background);
					break;
				case  UPDATEGAME:
					MessageUpdateGame mug = (MessageUpdateGame) m;
					Game.gameInstance().updatePlayer(mug);
					break;
				case PLAYERID:
					MessagePlayerID  mpid = (MessagePlayerID) m;
					_playerID = mpid.getplayerID();
					break;
				case SENDPLAYERS:
					MessageSendPlayers msp = (MessageSendPlayers) m;
					Game.gameInstance().addPlayers(msp.getPlayers());
					break;
				default:
					//add error message
					break;
				}
			} catch (SocketTimeoutException e) {

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	/*
	 * returns the client id
	 */
	public int getplayerID() {

		return _playerID;
	}
	/*
	 * checks if the client can connect
	 *
	public void close()
	{
		try
		{
			if ( m_socket != null )
			{
				m_socket.close();
				m_connected = false;				
			}
		}
		catch (IOException e)
		{
			System.out.println("Unable to close connection : " + e);
		}
	}*/


}
