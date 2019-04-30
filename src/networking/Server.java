package networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import networking.Messages.Message;

public class Server extends Thread{
	static Server Instance;
	int _serverPort;
	InetAddress _serverAdd;
	ArrayList<ServerSession> _ss;
	ServerSocket _socket;	

	private Server () {}
	/*
	 * creates the server if it doesn't already exist
	 */
	public static Server getInstance() {
		if(Instance == null)
			Instance = new Server();
		return Instance;
	}
	/*
	 * broadcasts messages
	 */
	public void broadcast(Message m, ServerSession ss) {
		for(ServerSession s : _ss){
			if(s!= ss) {
				s.sendMessage(m);
			}
		}
	}

	/*
	 * starts the server
	 */
	public void startSever( int portID) {

		_serverPort = portID;
		_ss = new ArrayList<ServerSession>();
		try {
			_socket = new ServerSocket(_serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}
	/*
	 * returns the list of server sessions
	 */
	public ArrayList<ServerSession> getClientSessions()
	{
		return _ss;
	}

	/*
	 * listens for new server sessions trying to connect(non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		boolean addClient = true;
		//Change this to while not shutdown
		while(addClient) {
			try {
				_ss.add( new ServerSession(_socket.accept()));
				_ss.get(_ss.size()-1).start();				
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(_ss.size() >2)
				addClient= false;
		}
	}

}
