package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import networking.Messages.MESSAGETYPE;
import networking.Messages.Message;
import networking.Messages.MessagePlayerID;

public class ServerSession extends Thread{

	InetAddress _clientIP ;
	ObjectInputStream _ois;
	ObjectOutputStream _oos;
	Socket _welcomeSocket;
	int _player;

	/*
	 * creates a new server session
	 */
	public ServerSession(Socket s){
		_welcomeSocket = s;
		_clientIP = s.getInetAddress();

		int i = Server.getInstance().getClientSessions().size()+1;
		_player = i;

		openStream();
		MessagePlayerID mpid = new MessagePlayerID(MESSAGETYPE.PLAYERID, i);
		sendMessage(mpid);

	}
	/*
	 * opens the input and output streams to send and receive messages
	 */
	public void openStream() {
		try {
			_ois = new ObjectInputStream(_welcomeSocket.getInputStream());
			_oos = new ObjectOutputStream(_welcomeSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	/*
	 * gets the player ID
	 */
	public int getPlayerID() {
		return _player;
	}
	/*
	 * Broadcasts all messages to be sent (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		int i=0;
		boolean runWhile= true;
		Message m = null;

		while(runWhile) {
			m = null;
			try {
				m = (Message) _ois.readObject();

				Server.getInstance().broadcast(m, this);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				i++;
				e.printStackTrace();
				if(i > 2)
					runWhile=false;
			}

		}
	}
	/*
	 * sends messages over the network 
	 */
	public synchronized boolean sendMessage(Message m) {
		try {
			_oos.writeObject(m);
			_oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
