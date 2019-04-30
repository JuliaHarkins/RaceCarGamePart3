package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;

import game.Game;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class UserDetails {

	private JFrame frame;
	private JTextField txt_username;
	private JTextField txt_ipAddress;
	private JButton btn_ConnectToLobby;
	private JLabel lb_UserName;
	private JLabel lb_hostIP;	
	private boolean _host;

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public UserDetails(boolean hosting) {
		_host = hosting;
		initialize();
	}


	public void openWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDetails window = new UserDetails(_host);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 643, 224);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.frame.getContentPane().setLayout(gridBagLayout);

		this.lb_UserName = new JLabel("User Name");
		GridBagConstraints gbc_lb_UserName = new GridBagConstraints();
		gbc_lb_UserName.insets = new Insets(0, 0, 5, 5);
		gbc_lb_UserName.anchor = GridBagConstraints.EAST;
		gbc_lb_UserName.gridx = 1;
		gbc_lb_UserName.gridy = 2;
		this.frame.getContentPane().add(this.lb_UserName, gbc_lb_UserName);

		this.txt_username = new JTextField();
		GridBagConstraints gbc_txt_username = new GridBagConstraints();
		gbc_txt_username.fill = GridBagConstraints.BOTH;
		gbc_txt_username.insets = new Insets(0, 0, 5, 0);
		gbc_txt_username.gridx = 2;
		gbc_txt_username.gridy = 2;
		this.frame.getContentPane().add(this.txt_username, gbc_txt_username);
		this.txt_username.setColumns(10);
		if(!_host) {

			this.lb_hostIP = new JLabel("Host IP");
			GridBagConstraints gbc_lb_hostIP = new GridBagConstraints();
			gbc_lb_hostIP.insets = new Insets(0, 0, 5, 5);
			gbc_lb_hostIP.anchor = GridBagConstraints.EAST;
			gbc_lb_hostIP.gridx = 1;
			gbc_lb_hostIP.gridy = 4;
			this.frame.getContentPane().add(this.lb_hostIP, gbc_lb_hostIP);



			this.txt_ipAddress = new JTextField();
			GridBagConstraints gbc_txt_ipAddress = new GridBagConstraints();
			gbc_txt_ipAddress.fill = GridBagConstraints.HORIZONTAL;
			gbc_txt_ipAddress.insets = new Insets(0, 0, 5, 0);
			gbc_txt_ipAddress.gridx = 2;
			gbc_txt_ipAddress.gridy = 4;
			this.frame.getContentPane().add(this.txt_ipAddress, gbc_txt_ipAddress);
			this.txt_ipAddress.setColumns(10);
		}

		String btnText;
		if (_host) {
			btnText = "Create";
		}else {
			btnText = "Join";
		}
		this.btn_ConnectToLobby = new JButton(btnText);
		this.btn_ConnectToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				InetAddress localhost = null;
				if(_host) {
					try {
						localhost = InetAddress.getLocalHost();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					System.out.println("System IP Address : " + 
							(localhost.getHostAddress()).trim()); 
				}
				else {
					boolean invalidIP= true;
					while(invalidIP) {
						try {
							String ip = txt_ipAddress.getText();
							localhost= InetAddress.getByName(ip);
							invalidIP = false;
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							invalidIP = true;
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error Invalid IP address");
						}
					}
				}
				Game.gameInstance().InitializeGame(_host, localhost);
				Game.gameInstance().loadLobby(txt_username.getText());
			}
		});
		GridBagConstraints gbc_btn_ConnectToLobby = new GridBagConstraints();
		gbc_btn_ConnectToLobby.insets = new Insets(0, 0, 5, 0);
		gbc_btn_ConnectToLobby.gridx = 2;
		gbc_btn_ConnectToLobby.gridy = 5;
		this.frame.getContentPane().add(this.btn_ConnectToLobby, gbc_btn_ConnectToLobby);

	}
}
