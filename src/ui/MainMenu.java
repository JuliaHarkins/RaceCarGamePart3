package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu {

	private JFrame frame;
	private JButton btnJoinLobby;
	private JButton btnCreateLobby;



	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void openWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 241, 163);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.frame.getContentPane().setLayout(gridBagLayout);

		this.btnCreateLobby = new JButton("Create Lobby");
		this.btnCreateLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UserDetails userDetails = new UserDetails(true);
				userDetails.openWindow();
				frame.setVisible(false);
				frame.dispose();


			}
		});
		GridBagConstraints gbc_btnCreateLobby = new GridBagConstraints();
		gbc_btnCreateLobby.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreateLobby.gridx = 3;
		gbc_btnCreateLobby.gridy = 2;
		this.frame.getContentPane().add(this.btnCreateLobby, gbc_btnCreateLobby);

		this.btnJoinLobby = new JButton("Join Lobby");
		this.btnJoinLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDetails userDetails = new UserDetails(false);
				userDetails.openWindow();
				frame.setVisible(false);
				frame.dispose();
			}
		});
		GridBagConstraints gbc_btnJoinLobby = new GridBagConstraints();
		gbc_btnJoinLobby.insets = new Insets(0, 0, 5, 5);
		gbc_btnJoinLobby.gridx = 3;
		gbc_btnJoinLobby.gridy = 3;
		this.frame.getContentPane().add(this.btnJoinLobby, gbc_btnJoinLobby);
	}

}
