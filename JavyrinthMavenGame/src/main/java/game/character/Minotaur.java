package game.character;

import java.io.IOException;
import javax.imageio.ImageIO;

import game.character.AI.ROLE;
import game.main.GamePanel;

public class Minotaur extends GameCharacter{
	
	public Minotaur(GamePanel gamePanel) {
		super(gamePanel);
	}
	
	@Override
	public void setDefaultValues() {
		setX(gamePanel.screenWidth/2 + gamePanel.screenWidth/4);
		setY(gamePanel.screenHeight/2 - gamePanel.tileSize);
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
	protected void setMoveMechanism() {
		//hookKeyListener(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		//hookKeyListener(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
		
		ai = new AI(gamePanel);
		//route = ai.evaluateNextMove(x, y, gamePanel.theseus.x, gamePanel.theseus.y, ROLE.HIDE);
		
		moveListener = new MoveListener() {

			@Override
			public void moveEmpty() {
				//when there're no moves, gets him new routes
				route = ai.evaluateNextMove(getX(), getY(), gamePanel.theseus.getX(), gamePanel.theseus.getY(), ROLE.HIDE);
			}
		};
		/*Removed second thread
		 * Everything works in the same thread now
		 * 
		new Thread(new Runnable() {
			
			AI ai = new AI(gamePanel);
			
			GameCharacter theseus = gamePanel.theseus;
			
			@Override
			public void run() {
				
				while (isAlive) {
					// By convention whenever the update function in GameCharacter finishes the move the route.moves will be empty.
					// in that case method sets route=null which signals this thread to feed the route by calling evaluateNextMove of AI class
					if(route==null) route = ai.evaluateNextMove(x, y, theseus.x, theseus.y, ROLE.HIDE);
					//else if(route.moves.isEmpty()) route = ai.evaluateNextMove(x, y, theseus.x, theseus.y, ROLE.HIDE); // Exception in thread "Thread-1" java.lang.NullPointerException: Cannot read field "moves" because "this.this$0.route" is null
				}
				
			}
			
		}).start();
		*/
	}
}
