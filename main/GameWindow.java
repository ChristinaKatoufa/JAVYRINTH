package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameWindow {

	JFrame frame;
	
	GameWindow() {
		frame = new JFrame();

		//* https://stackoverflow.com/questions/17697719/jframe-icon-image-not-working
		//frame.setIconImage(new ImageIcon(getClass().getResource("/icons/logo.ico")).getImage());
		/*
		Image img = (new ImageIcon(getClass().getResource("/icons/logo.ico"))).getImage();
		JLabel lblIcon = new JLabel(new ImageIcon(img));
		frame.add(lblIcon);
		*/
		frame.setIconImage( new ImageIcon( "/icons/logo.ico" ).getImage() );
		//Image icon = Toolkit.getDefaultToolkit().getImage("/icons/logo.png");    
		//frame.setIconImage(icon);
		
		frame.setTitle("J@VYRINTH");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		GamePanel gamePanel = new GamePanel();
		frame.add(gamePanel);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//* https://coderedirect.com/questions/114356/keylistener-not-working-for-jpanel
		//* https://stackoverflow.com/questions/10359683/keylistener-not-responding-in-java-swing
		//frame.requestFocusInWindow(); // This might cause game keyListening to stuck
		
		gamePanel.startGameThread();
	}

}
