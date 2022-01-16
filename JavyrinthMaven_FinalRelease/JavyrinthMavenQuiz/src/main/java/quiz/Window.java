package quiz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Window implements ActionListener {
	
	private PlayerAnsListener playerAnsListener;
	private Timer timer;
	private Sound sound;
	private JFrame jFrame;
	private JLabel lblErrors, lblTimeLeft, lblQuestion;
	private JTextArea questionBox, answer1Box, answer2Box, answer3Box, answer4Box;
	private JButton button1, button2, button3, button4;
	private int timeLeft = 30;
	private String errors = "0";

	public Window() {
		
		sound = new Sound();
		
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timeLeft--;
				lblTimeLeft.setText(String.valueOf(timeLeft));
				if (timeLeft == 10) {
					sound.play(Sound.TICKTOCK);
				}
				if (timeLeft == 0) {
					sound.stop();
					timer.stop();
					playerAnsListener.onPlayerAns("Out_of_Time"); // Dispatch event externally
				}
			}

		});
		timer.setRepeats(true);

		jFrame = new JFrame();
		jFrame.setResizable(false);
		jFrame.setBounds(100, 100, 460, 560);
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/**
		 * Handling if user tries to close the window
		 */
		jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int option = JOptionPane.showConfirmDialog(jFrame, "Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		JPanel backgroundPanel = getBackgroundPanel("/quiz/icons/papyrus.jpg");
		addWindowComponents(backgroundPanel);
		jFrame.setContentPane(backgroundPanel);
		
		// jFrame.pack();
		// jFrame.setVisible(true); // Do not use this here, will raise a window with empty text areas, preferably use it inside setWindowData

	}

	/**
	 * Creates the Background JPanel with the desired background image.
	 */
	private JPanel getBackgroundPanel(String backgroundImagePath) {

		@SuppressWarnings("serial")
		class ImagePanel extends JPanel {

			private BufferedImage image;

			public ImagePanel(String imagePath) {
				setLayout(new GridBagLayout());
				try {
					image = ImageIO.read(getClass().getResourceAsStream(imagePath));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		}

		ImagePanel backgroundPanel = new ImagePanel(backgroundImagePath);

		// backgroundPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// backgroundPanel.setBackground(new Color(232, 211, 185));

		return backgroundPanel;
	}

	/**
	 * Creates the Window Controls and adds them to the Background JPanel.
	 */
	private void addWindowComponents(JPanel backgroundPanel) {

		GridBagLayout gbl_backgroundPanel = new GridBagLayout();
		gbl_backgroundPanel.columnWidths = new int[] { 60, 80, 100, 80, 60 };
		gbl_backgroundPanel.rowHeights = new int[] { 40, 50, 30, 30, 30, 30, 50, 50, 50, 50, 40 };
		gbl_backgroundPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_backgroundPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

		backgroundPanel.setLayout(gbl_backgroundPanel);

		lblErrors = new JLabel(errors);
		lblErrors.setBackground(new Color(99, 69, 51));
		lblErrors.setOpaque(true);
		lblErrors.setForeground(Color.WHITE);
		lblErrors.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblErrors.setBorder(BorderFactory.createBevelBorder(1));
		lblErrors.setHorizontalAlignment(JTextField.CENTER);
		GridBagConstraints gbc_lblErrors = new GridBagConstraints();
		gbc_lblErrors.fill = GridBagConstraints.BOTH;
		gbc_lblErrors.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrors.gridx = 0;
		gbc_lblErrors.gridy = 1;
		backgroundPanel.add(lblErrors, gbc_lblErrors);

		JLabel lblErrLabel = new JLabel("Λάθη");
		lblErrLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblErrLabel = new GridBagConstraints();
		gbc_lblErrLabel.anchor = GridBagConstraints.WEST;
		gbc_lblErrLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblErrLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrLabel.gridx = 1;
		gbc_lblErrLabel.gridy = 1;
		backgroundPanel.add(lblErrLabel, gbc_lblErrLabel);

		JLabel lblTLeftLabel = new JLabel("<html>Υπολ.<br/>Χρόνος<html>");
		lblTLeftLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTLeftLabel = new GridBagConstraints();
		gbc_lblTLeftLabel.anchor = GridBagConstraints.EAST;
		gbc_lblTLeftLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblTLeftLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTLeftLabel.gridx = 3;
		gbc_lblTLeftLabel.gridy = 1;
		backgroundPanel.add(lblTLeftLabel, gbc_lblTLeftLabel);

		lblTimeLeft = new JLabel("" + timeLeft); // Equivalent to String.valueOf(timeLeft);
		lblTimeLeft.setBackground(new Color(99, 69, 51));
		lblTimeLeft.setOpaque(true);
		lblTimeLeft.setForeground(Color.WHITE);
		lblTimeLeft.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblTimeLeft.setBorder(BorderFactory.createBevelBorder(1));
		lblTimeLeft.setHorizontalAlignment(JTextField.CENTER);
		GridBagConstraints gbc_lblTimeLeft = new GridBagConstraints();
		gbc_lblTimeLeft.fill = GridBagConstraints.BOTH;
		gbc_lblTimeLeft.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimeLeft.gridx = 4;
		gbc_lblTimeLeft.gridy = 1;
		backgroundPanel.add(lblTimeLeft, gbc_lblTimeLeft);

		lblQuestion = new JLabel("Ερώτηση");
		lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblQuestion = new GridBagConstraints();
		gbc_lblQuestion.anchor = GridBagConstraints.SOUTH;
		gbc_lblQuestion.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuestion.gridx = 2;
		gbc_lblQuestion.gridy = 2;
		backgroundPanel.add(lblQuestion, gbc_lblQuestion);

		questionBox = new JTextArea();
		questionBox.setEditable(false);
		questionBox.setLineWrap(true);
		questionBox.setWrapStyleWord(true);
		questionBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_questionBox = new GridBagConstraints();
		gbc_questionBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_questionBox.gridwidth = 5;
		gbc_questionBox.insets = new Insets(0, 0, 5, 0);
		gbc_questionBox.gridx = 0;
		gbc_questionBox.gridy = 3;
		backgroundPanel.add(questionBox, gbc_questionBox);

		JLabel lblAnswer = new JLabel("Απάντηση");
		lblAnswer.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblAnswer = new GridBagConstraints();
		gbc_lblAnswer.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnswer.gridx = 2;
		gbc_lblAnswer.gridy = 5;
		backgroundPanel.add(lblAnswer, gbc_lblAnswer);

		button1 = new JButton("1.");
		button1.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_button1 = new GridBagConstraints();
		gbc_button1.insets = new Insets(0, 0, 5, 5);
		gbc_button1.gridx = 0;
		gbc_button1.gridy = 6;
		button1.setFocusable(false);
		backgroundPanel.add(button1, gbc_button1);
		button1.addActionListener(this);

		answer1Box = new JTextArea();
		answer1Box.setEditable(false);
		answer1Box.setLineWrap(true);
		answer1Box.setWrapStyleWord(true);
		// answer1Box.setHorizontalAlignment(SwingConstants.LEFT);
		answer1Box.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_answer1Box = new GridBagConstraints();
		gbc_answer1Box.fill = GridBagConstraints.HORIZONTAL;
		gbc_answer1Box.gridwidth = 4;
		gbc_answer1Box.insets = new Insets(0, 0, 5, 0);
		gbc_answer1Box.gridx = 1;
		gbc_answer1Box.gridy = 6;
		backgroundPanel.add(answer1Box, gbc_answer1Box);

		button2 = new JButton("2.");
		button2.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_button2 = new GridBagConstraints();
		gbc_button2.insets = new Insets(0, 0, 5, 5);
		gbc_button2.gridx = 0;
		gbc_button2.gridy = 7;
		button2.setFocusable(false);
		backgroundPanel.add(button2, gbc_button2);
		button2.addActionListener(this);

		answer2Box = new JTextArea();
		answer2Box.setEditable(false);
		answer2Box.setLineWrap(true);
		answer2Box.setWrapStyleWord(true);
		answer2Box.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_answer2Box = new GridBagConstraints();
		gbc_answer2Box.fill = GridBagConstraints.HORIZONTAL;
		gbc_answer2Box.gridwidth = 4;
		gbc_answer2Box.insets = new Insets(0, 0, 5, 0);
		gbc_answer2Box.gridx = 1;
		gbc_answer2Box.gridy = 7;
		backgroundPanel.add(answer2Box, gbc_answer2Box);

		button3 = new JButton("3.");
		button3.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_button3 = new GridBagConstraints();
		gbc_button3.insets = new Insets(0, 0, 5, 5);
		gbc_button3.gridx = 0;
		gbc_button3.gridy = 8;
		button3.setFocusable(false);
		backgroundPanel.add(button3, gbc_button3);
		button3.addActionListener(this);

		answer3Box = new JTextArea();
		answer3Box.setEditable(false);
		answer3Box.setLineWrap(true);
		answer3Box.setWrapStyleWord(true);
		answer3Box.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_answer3Box = new GridBagConstraints();
		gbc_answer3Box.fill = GridBagConstraints.HORIZONTAL;
		gbc_answer3Box.gridwidth = 4;
		gbc_answer3Box.insets = new Insets(0, 0, 5, 0);
		gbc_answer3Box.gridx = 1;
		gbc_answer3Box.gridy = 8;
		backgroundPanel.add(answer3Box, gbc_answer3Box);

		button4 = new JButton("4.");
		button4.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_button4 = new GridBagConstraints();
		gbc_button4.insets = new Insets(0, 0, 5, 5);
		gbc_button4.gridx = 0;
		gbc_button4.gridy = 9;
		button4.setFocusable(false);
		backgroundPanel.add(button4, gbc_button4);
		button4.addActionListener(this);

		answer4Box = new JTextArea();
		answer4Box.setEditable(false);
		answer4Box.setLineWrap(true);
		answer4Box.setWrapStyleWord(true);
		answer4Box.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_answer4Box = new GridBagConstraints();
		gbc_answer4Box.insets = new Insets(0, 0, 5, 0);
		gbc_answer4Box.fill = GridBagConstraints.HORIZONTAL;
		gbc_answer4Box.gridwidth = 4;
		gbc_answer4Box.gridx = 1;
		gbc_answer4Box.gridy = 9;
		backgroundPanel.add(answer4Box, gbc_answer4Box);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ActionListener (this) hooked to the buttons with .addActionListener(this);
		
		String playerAns = null;

		sound.stop();
		timerStop(); // Can also be used in Controller but here offers better automation

		button1.setEnabled(false);
		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);

		if (e.getSource() == button1)
			playerAns = "1";
		else if (e.getSource() == button2)
			playerAns = "2";
		else if (e.getSource() == button3)
			playerAns = "3";
		else if (e.getSource() == button4)
			playerAns = "4";

		playerAnsListener.onPlayerAns(playerAns); // Dispatch playerAns externally
	}
	/**
	 * Actually Starts or Re-starts timer
	 * @param duration
	 */
	void timerStart(int duration) {
		timeLeft = duration;
		lblTimeLeft.setText(String.valueOf(timeLeft));
		timer.restart();
	}

	void timerStop() {
		timer.stop();
	}
	/**
	 * This listener's method should implemented by others 
	 * to dispatch externally playerAns and Out_of_Time
	 */
	public interface PlayerAnsListener {
		void onPlayerAns(String playerAns);
	}

	public void addPlayerAnsListener(PlayerAnsListener playerAnsListener) {
		this.playerAnsListener = playerAnsListener;
	}
	
	public void setErrors(String errors) {
		lblErrors.setText(errors);
	}
/**
 * Sets the quiz data in our window
 * @param askedQuestions
 * @param question
 * @param answer1
 * @param answer2
 * @param answer3
 * @param answer4
 */
	public void setWindowData(String askedQuestions, String question, String answer1, String answer2, String answer3, String answer4) {
		
		button1.setEnabled(true);
		button2.setEnabled(true);
		button3.setEnabled(true);
		button4.setEnabled(true);
		
		lblQuestion.setText(askedQuestions);
		questionBox.setText(question);
		answer1Box.setText(answer1);
		answer2Box.setText(answer2);
		answer3Box.setText(answer3);
		answer4Box.setText(answer4);
		// Make the wihdow visible, until is filled with data.
		//Use this here so you don't have a window with empty components.
		if (!jFrame.isVisible()) { 
			jFrame.setLocationRelativeTo(null);
			jFrame.setVisible(true);
			jFrame.setVisible(true);
		}
		
		// timer.start(); // Not good in here, we also use this method when game is over.
	}

	public void closeWindow() {
		this.jFrame.dispose();
	}
}
