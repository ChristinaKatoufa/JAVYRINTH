package game.main;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameWindow {

	JFrame frame;

	GameWindow() {
		frame = new JFrame();

		/*
		 * https://stackoverflow.com/questions/17697719/jframe-icon-image-not-working
		 * frame.setIconImage(new
		 * ImageIcon(getClass().getResource("/icons/logo_24.ico")).getImage());
		 */
		/*
		 * Image img = (new
		 * ImageIcon(getClass().getResource("/icons/logo.ico"))).getImage(); JLabel
		 * lblIcon = new JLabel(new ImageIcon(img)); frame.add(lblIcon);
		 */

		frame.setIconImage(new ImageIcon("/icons/logo_24.ico").getImage());
		// frame.setIconImage(Toolkit.getDefaultToolkit().getImage("/icons/logo.png"));

		frame.setTitle("J@VYRINTH");

		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);

		GamePanel gamePanel = new GamePanel();
		frame.add(gamePanel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//* https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				gamePanel.isPaused = true;
				int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
					//ModuleData.setGameplayDuration(-1);
					System.exit(0);
				}
				else gamePanel.isPaused = false;
			}
		});

		gamePanel.startGameThread();

		/*
		 * https://coderedirect.com/questions/114356/keylistener-not-working-for-jpanel
		 * https://stackoverflow.com/questions/10359683/keylistener-not-responding-in-
		 * java-swing frame.requestFocusInWindow(); // This might cause game
		 * keyListening to stuck
		 */
	}
}