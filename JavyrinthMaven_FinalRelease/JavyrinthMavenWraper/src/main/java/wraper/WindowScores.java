package wraper;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class WindowScores {

	private JFrame jFrame;
	private JLabel lblScoresTable;
	
	public WindowScores() {
		jFrame = new JFrame();
		jFrame.setResizable(false);
		jFrame.setBounds(100, 100, 460, 560);
		jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Handling if user tries to close the window
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
		
		JPanel backgroundPanel = getBackgroundPanel("/wraper/icons/papyrus.jpg");
		addWindowComponents(backgroundPanel);
		jFrame.setContentPane(backgroundPanel);
	}

	/**
	 * Creates the Background JPanel with the desired background image.
	 */
	private JPanel getBackgroundPanel(String backgroundImagePath) {

		@SuppressWarnings("serial")
		class ImagePanel extends JPanel {

			private BufferedImage image;

			public ImagePanel(String imagePath) {
				// setOpaque(false);
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
			/*
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(460, 600);
			}
			*/
		}

		ImagePanel backgroundPanel = new ImagePanel(backgroundImagePath);

		return backgroundPanel;
	}

	/**
	 * Creates the Window Controls and adds them to the Background JPanel.
	 */
	private void addWindowComponents(JPanel backgroundPanel) {

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {30, 50, 100, 100, 50, 30};
		gbl_contentPane.rowHeights = new int[] {50, 50, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 50};
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		
		backgroundPanel.setLayout(gbl_contentPane);
		
		JLabel lblTitle = new JLabel("Top 10 High Scores");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 4;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 1;
		backgroundPanel.add(lblTitle, gbc_lblTitle);
						
		lblScoresTable = new JLabel();
		lblScoresTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblScores = new GridBagConstraints();
		gbc_lblScores.gridheight = 12;
		gbc_lblScores.gridwidth = 4;
		gbc_lblScores.insets = new Insets(0, 0, 5, 5);
		gbc_lblScores.gridx = 1;
		gbc_lblScores.gridy = 2;
		backgroundPanel.add(lblScoresTable, gbc_lblScores);
		
	}
	/**
	 * Table with all player's scores
	 * @param scores
	 */
	public void setWindowData(ArrayList<String[]> scores) {
		String pRank = null, pUsername = null, pTime = null, pScore = null;
		int playerPosition = scores.size();
		for (int i = scores.size() ; i >= 1; i--) {
			String[] tableRow = scores.get(i-1);
			String rank = tableRow[0], username = tableRow[1], time = tableRow[2], score = tableRow[3];
			if(i == scores.size()) {
				pRank =  rank; pUsername = username; pTime = time; pScore = score;
				continue;
			}
			if(rank.equals(pRank) && username.equals(pUsername) && time.equals(pTime) && score.equals(pScore)) {
				playerPosition = i;
				break;
			}
		}
		String scoresTableRows = "";
		int counter = 0;
		for (String[] tableRow : scores) {
			counter++;
			String rank = tableRow[0], username = tableRow[1], time = tableRow[2], score = tableRow[3];
			
			if (counter == scores.size()) {
				if (playerPosition > scores.size()-1) {
					scoresTableRows += "<tr>"
							+ "<td style=\"text-align:center; font-weight:bold\">......</td>"
							+ "<td style=\"text-align:center; font-weight:bold\">............</td>"
							+ "<td style=\"text-align:center; font-weight:bold\">............</td>"
							+ "<td style=\"text-align:center; font-weight:bold\">......</td>"
							+ "</tr>";
					//rank = pRank; username = pUsername; time = pTime; score = pScore;
				}
				else break;
			}
			
			if (counter == playerPosition) scoresTableRows += "<tr style=\"background-color:#FFD700;\">";
			else scoresTableRows += "<tr>";
			scoresTableRows += ""
					+ "<td style=\"text-align:center; font-weight:bold\">"+rank+"</td>"
					+ "<td style=\"font-weight:bold\">"+username+"</td>"
					+ "<td style=\"font-size:9px; font-weight:bold\">"+time+"</td>"
					+ "<td style=\"text-align:center; font-weight:bold\">"+score+"</td>"
					+ "</tr>";
		}
		//"<td>"+tableRow[2].split(" ")[0]+"<br>"+tableRow[2].split(" ")[1]+"</td>"
		
		final String scoresTable1 = "<html>"
				+ "<table class=\"myTable\">"
				+ "<thead>"
				+ "<tr>"
				+ "<th>Rank</th>"
				+ "<th>Username</th>"
				+ "<th>Time</th>"
				+ "<th>Score</th>"
				+ "</tr>"
				+ "</thead>"
				+ "<tbody>";
		
		final String scoresTable2 = "</tbody>"
				+ "</table>"
				+ "</html>";
		
		final String scoresTable = scoresTable1 + scoresTableRows + scoresTable2;
		
		lblScoresTable.setText(scoresTable);
		
		// Make the window visible, until is filled with data. Use this here so you don't hava a window with empty components.
		if (!jFrame.isVisible()) {
			jFrame.setLocationRelativeTo(null);
			jFrame.setVisible(true);
			jFrame.setVisible(true);
		}
	}

}
