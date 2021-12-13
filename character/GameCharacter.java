package character;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import main.GamePanel;
/** Creating our game's characters superClass
 * 
 * @author Christina Katoufa
 * @author christinakatoufa@gmail.com
 * 
 */
public abstract class GameCharacter {
	/**FIELDS
	 * (x,y) Character's stage position (pixels)
	 * (speed)How many pixels should add for every character's movement
	 * gamePanel is our main Arena that will store everything
	 * (keylistener) Theseas is our player 
	 * 		but temporarily Minotaur will also move using keyboard
	 * (images) Character's images
	 * (solidArea) Character's collision solid area rectangle
	 * (movement) Holds the movement direction as Strings
	 * 		Values: up, down, left, right. Separated with space for more than one.
	 * (collision) Holds the sides that collide with solid areas. 
	 * 		Values: up, down, left, right. Separated with space for more than one.
	 */
	public int x;
	public int y;
	protected int speed; 
	protected GamePanel gamePanel;
	protected KeyListener keyListener;
	protected BufferedImage image;
	protected Rectangle solidArea;
	protected String movement = "";
	private String collision = "";
	
	public GameCharacter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		setDefaultValues();
		setCollisionRect();
		getCharacterImage();
		setMovementMechanism();
	}
	/**
	 * Theseas and Minotaur will extend this class
	 * and each one will have a different starting point
	 * and a different image
	 */
	abstract protected void setDefaultValues();
	
	abstract protected void getCharacterImage();
	/**MovementMechanism
	 * Assigns the mechanism that updates the movement String
	 *  1. Use hookKeyListener method for Human Player
	 *  2. Implement AI for computer Player
	 */
	abstract protected void setMovementMechanism();
	
	/** Updates the character's position
	 * if no key pressed just idle
	 * (remember this update method is called many times per second)
	 */
	public void update() {
		if (!movement.contentEquals("")) {
			collisionDetection();
			//System.out.println("movement inside update: " + movement);
			//System.out.println("collision: " + collision);
			if (movement.contains("up") && !collision.contains("up")
					&& y - speed > 0 ) y -= speed;
			if (movement.contains("down") && !collision.contains("down")
					&& y + speed < gamePanel.screenHeight) y += speed;
			if (movement.contains("left") && !collision.contains("left")
					&& x - speed > 0) x -= speed;
			if (movement.contains("right") && !collision.contains("right")
					&& x + speed < gamePanel.screenWidth) x += speed;
		}
	}
	/**
	 * This method paints our character's image
	 * tile size is 32x32
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
	}
	/**
	 * Defines the collision detection rectangle
	 */
	protected void setCollisionRect() {
		solidArea = new Rectangle();
		solidArea.x = gamePanel.tileSize/4;
		solidArea.y = gamePanel.tileSize/4;
		solidArea.width = gamePanel.tileSize/2 + gamePanel.tileSize/8;
		//solidArea.width = Math.round(1 - 2*solidArea.x);
		solidArea.height = gamePanel.tileSize/2 + gamePanel.tileSize/8;
	}
	/**
	 * 
	 */
	protected void collisionDetection() {
		int top = this.y + this.solidArea.y;
		int bottom = top + this.solidArea.height;
		int left = this.x + this.solidArea.x;
		int right = left + this.solidArea.width;
		
		int characterTop = top/gamePanel.tileSize;
		int characterBottom = bottom/gamePanel.tileSize;
		int characterLeft = left/gamePanel.tileSize;
		int characterRight = right/gamePanel.tileSize;
		int characterNext;
			
		int tileNum1, tileNum2;
		
		collision = "";
		
		/**Checks for surrounding solid tiles
		 * 		based on character's movement direction
		 */
		if (this.movement.contains(" up")) {
			characterNext = (top - this.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.labyrinth.labyrinthMap[characterLeft][characterNext];
			tileNum2 = gamePanel.labyrinth.labyrinthMap[characterRight][characterNext];
			if(gamePanel.labyrinth.tile[tileNum1].solid == true 
					|| gamePanel.labyrinth.tile[tileNum2].solid == true) {
				if(!this.collision.contains(" up"))
					this.collision = this.collision + " up";
			}/*else {
				if(this.collision.contains(" up")) this.collision = this.collision.replace(" up", "");
			}*/
		}
		if (this.movement.contains(" down")) {
			characterNext = (bottom + this.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.labyrinth.labyrinthMap[characterLeft][characterNext];
			tileNum2 = gamePanel.labyrinth.labyrinthMap[characterRight][characterNext];
			
			if(gamePanel.labyrinth.tile[tileNum1].solid == true 
					|| gamePanel.labyrinth.tile[tileNum2].solid == true) {
				if(!this.collision.contains(" down"))
					this.collision = this.collision + " down";
			}/*else {
				if(this.collision.contains(" down")) this.collision = this.collision.replace(" down", "");
			}*/
		}
		if (this.movement.contains(" left")) {
			characterNext = (left - this.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.labyrinth.labyrinthMap[characterNext][characterTop];
			tileNum2 = gamePanel.labyrinth.labyrinthMap[characterNext][characterBottom];
			
			if(gamePanel.labyrinth.tile[tileNum1].solid == true
					|| gamePanel.labyrinth.tile[tileNum2].solid == true) {
				if(!this.collision.contains(" left"))
					this.collision = this.collision + " left";
			}/*else {
				if(this.collision.contains(" left")) this.collision = this.collision.replace(" left", "");
			}*/
			
		}
		if (this.movement.contains(" right")) {
			characterNext = (right + this.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.labyrinth.labyrinthMap[characterNext][characterTop];
			tileNum2 = gamePanel.labyrinth.labyrinthMap[characterNext][characterBottom];
			
			if(gamePanel.labyrinth.tile[tileNum1].solid == true
					|| gamePanel.labyrinth.tile[tileNum2].solid == true) {
				if(!this.collision.contains(" right"))
					this.collision = this.collision + " right";
			}/*else {
				if(this.collision.contains(" right")) this.collision = this.collision.replace(" right", "");
			}*/
		}
	}
	public KeyListener hookKeyListener(int keyUp, int keyDown, int keyLeft, int keyRight) {
		KeyListener keyListener = new KeyListener() {
			/**
			 * In case many keys pressed simultaneously (captures diagonal movement).
			 * movement string contains all movement directions 
			 * separated with space
			 */
			@Override
			public void keyTyped(KeyEvent e) {		
			}
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code==keyUp) {
					if(!movement.contains(" up")) movement = movement + " up";
				}
				if (code==keyDown) {
					if(!movement.contains(" down")) movement = movement + " down";
				}
				if (code==keyLeft) {
					if(!movement.contains(" left")) movement = movement + " left";
				}
				if (code==keyRight) {
					if(!movement.contains(" right")) movement = movement + " right";
				}
				//System.out.println("movement inside KeyListener: " + movement);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				if (code==keyUp) {
					if(movement.contains(" up")) movement = movement.replace(" up", "");
				}
				if (code==keyDown) {
					if(movement.contains(" down")) movement = movement.replace(" down", "");
				}
				if (code==keyLeft) {
					if(movement.contains(" left")) movement = movement.replace(" left", "");
				}
				if (code==keyRight) {
					if(movement.contains(" right")) movement = movement.replace(" right", "");
				}
			}
		};
		/**
		 * NOT NESSESARY! A non-static inner class can access
		 * the variables of its outer class, even if they're private.
		 * 		https://stackoverflow.com/questions/53855612/cannot-access-a-field-from-a-static-extending-class-contained-in-the-same-java
				https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html
				movement = keyListener.movement;
		 */
				
		this.keyListener = keyListener;
		gamePanel.addKeyListener(keyListener);
		
		return keyListener;
	}
}
