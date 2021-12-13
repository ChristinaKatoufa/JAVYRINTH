package character;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Minotaur extends GameCharacter {
	public Minotaur(GamePanel gamePanel) {
		super(gamePanel);
	}
	@Override
	protected void setDefaultValues() {
		x = gamePanel.screenWidth/2 + gamePanel.screenWidth/4 - gamePanel.tileSize/2;
		y = gamePanel.screenHeight/2 - gamePanel.tileSize/2;
		speed = 4;
	}

	@Override
	protected void getCharacterImage() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/minotaur/minotaur.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void setMovementMechanism() {
		hookKeyListener(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		//hookKeyListener(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
		/*
		if (x - gamePanel.theseus.x>0) movement += " right"; else movement += " left";
		if (y - gamePanel.theseus.y>0) movement += " down"; else movement += " up";
		*/
	}

}
