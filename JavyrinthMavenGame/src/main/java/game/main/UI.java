package game.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class UI {

	GamePanel gamePanel;
	protected BufferedImage image;
	private Graphics2D g2;
	Font chronometerFont;
	Color chronometerFrameColor;
	long startTime;
	long elapsedTime;
	long seconds2display = 0;
	int minutes2display = 0;
	boolean chronometerON = false;
	boolean messageRectON = false;
	int messageRectX;
	int messageRectY;
	int messageRectWidth;
	int messageRectHeight;
	Color messageRectBorderColor;
	Color messageRectFillColor;
	Font messageRectFont;
	private int blinkingCounter = 30;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/icons/chronometer.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		chronometerFrameColor = new Color(0, 0, 0, 100);
		chronometerFont = new Font("Arial", Font.PLAIN, 36);
		
		messageRectX = gamePanel.screenWidth/4;
		messageRectY = gamePanel.screenHeight/3;
		messageRectWidth = gamePanel.screenWidth/2;
		messageRectHeight = gamePanel.screenHeight/3;
		
		 // Old Silver #808588 https://www.color-name.com/hex/808588
		messageRectBorderColor = new Color(128, 133, 136, 220);
		// Young Gold #DFC026 https://www.color-name.com/young-gold.color
		messageRectFillColor = new Color(223, 192, 38, 220);
		messageRectFont = new Font("Arial", Font.BOLD, 20);
	}
	public void update() {
		//elapsedTime = System.currentTimeMillis() - startTime;
		if (chronometerON) {
	        seconds2display = (System.currentTimeMillis() - startTime) / 1000;
	        if (seconds2display == 60) {
	        	startTime = System.currentTimeMillis();
	        	minutes2display++;
	        	seconds2display = 0;
	        }
		}
	}
	
	public void toggleMessageRect() {
		messageRectON = !messageRectON;
	}
	
	public void togglePauseChronometer() {
		chronometerON = !chronometerON;
		elapsedTime = System.currentTimeMillis() - elapsedTime;
		if (chronometerON) startTime = elapsedTime;
	}
	
	public long getGameplayDutation() {
		long gameplayDuration = 60*minutes2display + seconds2display;
		return gameplayDuration;
	}
	public void draw(Graphics2D g2) {	
		this.g2 = g2;
		drawChronometer();
		if(messageRectON) drawMessageRect();
	}
	
	private void drawChronometer() {
		g2.setColor(chronometerFrameColor);
		g2.fillRoundRect(3 * 32 - 10, 0, 5 * 32 + 10, 32, 32, 32);
		g2.drawImage(image, 3 * gamePanel.tileSize, 0, gamePanel.tileSize,
				gamePanel.tileSize, null);
		g2.setFont(chronometerFont);
		g2.setColor(Color.WHITE);
		g2.drawString(new DecimalFormat("00").format(minutes2display)+ ":"
		+ new DecimalFormat("00").format(seconds2display),
		4 * gamePanel.tileSize+gamePanel.tileSize/2, gamePanel.tileSize - 3);
	}
	
	private void drawMessageRect() {
		g2.setColor(messageRectBorderColor);
		g2.fillRoundRect(messageRectX, messageRectY, messageRectWidth, messageRectHeight, 32, 32);
		g2.setColor(messageRectFillColor);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(messageRectX, messageRectY, messageRectWidth, messageRectHeight, 32, 32);
		g2.setFont(messageRectFont);
		g2.setColor(Color.WHITE);
		g2.setFont( g2.getFont().deriveFont(Font.BOLD, 11F) );
		//g2.setFont(new Font("Arial", Font.BOLD, 11));
		g2.drawString("<W>", messageRectX + messageRectWidth*20/100, messageRectY+messageRectHeight*85/100);
		g2.drawString("<S>", messageRectX + messageRectWidth*20/100, messageRectY+messageRectHeight*95/100);
		g2.drawString("<A>", messageRectX + messageRectWidth*15/100, messageRectY+messageRectHeight*90/100);
		g2.drawString("<D>", messageRectX + messageRectWidth*25/100, messageRectY+messageRectHeight*90/100);
		g2.drawString("(Un)Pause: <Enter>", messageRectX + messageRectWidth*65/100, messageRectY+messageRectHeight*85/100);
		g2.drawString("Exit: <Esc>", messageRectX + messageRectWidth*65/100, messageRectY+messageRectHeight*95/100);
		
		if (gamePanel.gameState == gamePanel.STATE_START_SCREEN) {
			String text = "Catch & Defeat Minotaur";
			g2.setFont(messageRectFont);
			g2.drawString(text, getXforCenteredText(text), messageRectY + messageRectHeight/4);
			text = "As Soon As Possible!";
			g2.drawString(text, getXforCenteredText(text), messageRectY + messageRectHeight/4 + 
					(int) g2.getFontMetrics().getStringBounds(text, g2).getHeight());
			text = "Press <enter> to start.";
			g2.setColor(Color.ORANGE);
			if (blinkingCounter >= 15) {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
				g2.drawString(text, getXforCenteredText(text), messageRectY + messageRectHeight*1/2
						+ (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight());
			}
			blinkingCounter--;
			if (blinkingCounter == 0) blinkingCounter = 30;
		}
		
		if (gamePanel.gameState == gamePanel.STATE_PAUSED) {
			String text = "Game Paused";
			g2.setFont(messageRectFont);
			g2.drawString(text, getXforCenteredText(text), messageRectY + messageRectHeight/2);
		}
		
		if (gamePanel.gameState == gamePanel.STATE_EXIT) {
			String text = "Are you sure you want to Exit?";
			g2.setFont(messageRectFont);
			g2.drawString(text, getXforCenteredText(text), messageRectY + messageRectHeight/4);
			
			text = "YES: <ESC>";
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
			g2.drawString(text, getXforCenteredText(text) - messageRectWidth/4, messageRectY + messageRectHeight*1/2
					+ (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight());
			text = "NO: <Enter>";
			//g2.setFont(new Font("Arial", Font.BOLD, 16));
			g2.drawString(text, getXforCenteredText(text) + messageRectWidth/4, messageRectY + messageRectHeight*1/2
					+ (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight());
		}
	}
	
	int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = messageRectWidth - length/2;
		return x;
	}
}