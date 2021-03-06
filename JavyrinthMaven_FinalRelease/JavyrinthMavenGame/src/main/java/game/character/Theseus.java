package game.character;

import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.main.GamePanel;

public class Theseus extends GameCharacter {
	
	public Theseus(GamePanel gamePanel) {
		super(gamePanel);
	}
	/**
	 * Starting position of Theseus
	 */
	@Override
	public void setDefaultValues() {
		setX(5);
		//x = gamePanel.screenWidth/2 + 4*32;
		setY(gamePanel.screenHeight/2 - gamePanel.tileSize/2);
		speed = 2;
	}
	
	@Override
	public void getCharacterImage() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/theseus/theseus.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Theseus moves with the keyboard
	 * W,S,A,D
	 * Enter for pause/unpause
	 */
	@Override
	protected void setMoveMechanism() {
		//hookKeyListener(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		hookKeyListener(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER);
	}
}