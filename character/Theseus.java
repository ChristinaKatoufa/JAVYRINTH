package character;

import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Theseus extends GameCharacter {
	
	public Theseus(GamePanel gamePanel) {
		super(gamePanel);
	}
	
	@Override
	public void setDefaultValues() {
		x = 5;
		//x = gamePanel.screenWidth/2 + 4*32;
		y = gamePanel.screenHeight/2 - gamePanel.tileSize/2;
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

	@Override
	protected void setMoveMechanism() {
		//hookKeyListener(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		hookKeyListener(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER);
		
	}
}
