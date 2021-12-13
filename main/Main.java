package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setIconImage( new ImageIcon( "/icons/logo.png" ).getImage() );
		window.setTitle("J@VYRINTH");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.requestFocusInWindow();
		
		gamePanel.startGameThread();
	}
}