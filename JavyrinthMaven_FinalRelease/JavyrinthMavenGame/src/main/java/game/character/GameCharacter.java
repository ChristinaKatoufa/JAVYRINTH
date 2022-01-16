package game.character;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

import game.character.AI.Route;
import game.character.AI.Station;
import game.main.Audio;
import game.main.GamePanel;

public abstract class GameCharacter {

	protected GamePanel gamePanel;
	protected KeyListener keyListener;
	protected MoveListener moveListener;
	private int x; // Character's stage position (pixels)
	protected int y;
	protected int speed; // How many pixels should add for every character's move
	protected BufferedImage image; // Character's sprite images
	protected Rectangle collisionRectangle; // Character's collision solid area rectangle
	//Changed these two fields from Strings to ArrayLists
	//protected String collision = ""; // Holds the sides that collide with solid tiles. Values: up, down, left, right. Seperate with space for more than one.
	//protected String move = ""; // Holds the move direction.
	protected ArrayList<DIR> collision = new ArrayList<DIR>();
	protected ArrayList<DIR> move = new ArrayList<DIR>();
	volatile Route route = null;
	protected AI ai;
	public int lifeCounter = 1;
	private int animationCounter = 90;
	volatile boolean isAlive = true;

	public GameCharacter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		setCollisionRectangle();
		setDefaultValues();
		getCharacterImage();
		setMoveMechanism();
	}
	/**
	 * Theseus and Minotaur implements these methods in their own way
	 */
	abstract protected void setDefaultValues();
	
	abstract protected void getCharacterImage();
	/**
	 * Defines the collision detection rectangle
	 * (character's solid area that collides with other solid objects)
	 */
	protected void setCollisionRectangle() {
		// Define the collision detection rectangle 
		// (character's solid area that collides with other solid objects)
		collisionRectangle = new Rectangle();
		collisionRectangle.x = gamePanel.tileSize/4;
		collisionRectangle.y = gamePanel.tileSize/4;
		collisionRectangle.width = gamePanel.tileSize/2;
		collisionRectangle.height = gamePanel.tileSize/2;
	}
	/**
	 * Updates character's position
	 * 
	 * Theseus has null route,
	 * so not null works for Minotaur
	 * 
	 *  if no key pressed just idle
	 * (remember this update method called many times per second)
	 */
	public void update() {
		if (route != null) { //Theseus has null route, so this works for Minotaur
			if (!route.moves.isEmpty()) { // Mipws tin route na min tin kanw null alla isEmpty?
				DIR dir = route.moves.get(0);
				Station station = route.stations.get(0);
				//System.out.println("dir: "+dir);
				if (dir.equals(DIR.N)) {
					//System.out.println("move N"+" , y: "+y+" , station.y: " +station.y);
					if (y > station.y) {
						if (!move.contains(DIR.N)) move.add(DIR.N);
					}
					else {
						//move.replace(DIR.N, "");
						move.clear();
						route.moves.remove(0);
						route.stations.remove(0);
					}
				}
				if (dir.equals(DIR.S)) {
					//System.out.println("move S"+" , y: "+y+" , station.y: " +station.y);
					if (y < station.y) {
						if(!move.contains(DIR.S)) move.add(DIR.S);
					}
					else {
						move.clear();
						route.moves.remove(0);
						route.stations.remove(0);
					}
				}
				if (dir.equals(DIR.W)) {
					//System.out.println("move W"+" , x: "+x+" , station.x: " +station.x);
					if (x > station.x) {
						if (!move.contains(DIR.W)) move.add(DIR.W);
					}
					else {
						move.clear();
						route.moves.remove(0);
						route.stations.remove(0);
					}
				}
				if (dir.equals(DIR.E)) {
					//System.out.println("move E"+" , x: "+x+" , station.x: " +station.x);
					if (x < station.x) {
						if (!move.contains(DIR.E)) move.add(DIR.E);
					}
					else {
						move.clear();
						route.moves.remove(0);
						route.stations.remove(0);
					}
				}
				//if(route.moves.size()==0) route=null; // By convention signals the AI thread to feed new route
				//if(movesCounter==2) route=null; movesCounter=0;
			}
			//else route = ai.evaluateNextMove(x, y, gamePanel.theseus.x, gamePanel.theseus.y, ROLE.HIDE);
		}
		//else route = ai.evaluateNextMove(x, y, gamePanel.theseus.x, gamePanel.theseus.y, ROLE.HIDE);
		// if no key pressed just idle (remember this update method called many times per second)
		if (!move.isEmpty()) {
			
			collision.clear();
			collision =
					new CollisionDetection(gamePanel).getCollisions(move, this.x, this.y, speed);
			
			//System.out.println(this.getClass()+" move inside update: " + move);
			//System.out.println("this.collision: " + this.collision);
			//System.out.println("tileCollisionDetection: " + tileCollisionDetection(x, y));
			
			if (move.contains(DIR.N) && !collision.contains(DIR.N)
					&& y - speed > 0 ) y -= speed;
			if (move.contains(DIR.S) && !collision.contains(DIR.S)
					&& y + speed < gamePanel.screenHeight) y += speed;
			if (move.contains(DIR.W) && !collision.contains(DIR.W) &&
					x - speed > 0) x -= speed;
			if (move.contains(DIR.E) && !collision.contains(DIR.E)
					&& x + speed < gamePanel.screenWidth) x += speed;
		}
		// Signals for empty moves
		else if (moveListener != null) moveListener.moveEmpty();
	}
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
	}
	/**
	 * Initializes keylistener and overrides its methods
	 * @param keyUp
	 * @param keyDown
	 * @param keyLeft
	 * @param keyRight
	 * @param keyEnter
	 * @return keylistener
	 */
	public KeyListener hookKeyListener(int keyUp, int keyDown, int keyLeft, int keyRight, int keyEnter) {
		KeyListener keyListener = new KeyListener() {
			//public String move = "";
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				// In case many keys pressed simultanously (captures diagonal move).
				int code = e.getKeyCode();
				if (code == keyUp) {
					if (!move.contains(DIR.N)) move.add(DIR.N);
				}
				if (code == keyDown) {
					if (!move.contains(DIR.S)) move.add(DIR.S);
				}
				if (code == keyLeft) {
					if(!move.contains(DIR.W)) move.add(DIR.W);
				}
				if (code == keyRight) {
					if(!move.contains(DIR.E)) move.add(DIR.E);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == keyUp) {
					if (move.contains(DIR.N)) move.remove(DIR.N);
				}
				if (code == keyDown) {
					if(move.contains(DIR.S)) move.remove(DIR.S);
				}
				if (code == keyLeft) {
					if(move.contains(DIR.W)) move.remove(DIR.W);
				}
				if (code == keyRight) {
					if(move.contains(DIR.E)) move.remove(DIR.E);
				}
			}
		};
		/*
		 *  NOT NESSESARY!
		 *  A non-static inner class can access the variables of its outer class, 
		 *  even if they're private.
		 *  https://stackoverflow.com/questions/53855612/cannot-access-a-field-from-a-static-extending-class-contained-in-the-same-java
		 *  https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html
		 *  move = keyListener.move;
		 */
		this.keyListener = keyListener;
		this.gamePanel.addKeyListener(keyListener);
		return keyListener;
	}
	/**
	 * Assigns the mechanism that updates the move String
	 * 1. Use hookKeyListener method for Human Player
	 * 2. Implement AI for computer Player
	 */
	abstract protected void setMoveMechanism();
	
	public interface MoveListener {
		public void moveEmpty();
	}
	/**
	 * Substitute for update method to animate character dying
	 */
	public void dying() {
		if (animationCounter == 90) {
			isAlive = false;
			//gamePanel.getAudioChannel().play(Audio.DYING);
			new Audio().play(Audio.DYING);
		}
		if (animationCounter > 60 && animationCounter <= 90) {
			int[] num = {-1, 0, 1};
			Random randX = new Random();
			x += num[randX.nextInt(num.length)];
			Random randY = new Random();
			y += num[randY.nextInt(num.length)];
		}
		if (animationCounter == 60) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/minotaur/transparent.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (animationCounter == 30) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/minotaur/grave1.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//gamePanel.getAudioChannel().play(Audio.WIN);
			new Audio().play(Audio.WIN);
		}
		if (animationCounter == 0) gamePanel.gameState = gamePanel.STATE_GAMEOVER;
		animationCounter--;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}