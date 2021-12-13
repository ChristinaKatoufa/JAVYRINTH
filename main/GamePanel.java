package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import character.Minotaur;
import character.Theseus;
import labyrinth.Labyrinth;

public class GamePanel extends JPanel implements Runnable {
	/**Screen Settings
	 * tile 32x32
	 * column/row 18*(16/9)=32 , 18*(4/3)=24
	 */
	public final int tileSize = 32;
	public final int maxScreenRow = 18;
	public final int maxScreenCol = 24; 
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	/**
	 * Instatiate the characters and the labyrinth
	 *		all of them will know the screen settings
	 *		and will images will be resized to tileSize
	 *Create a thread so everything will work at the same time
	 */
	public Labyrinth labyrinth = new Labyrinth(this);
	public Theseus theseus = new Theseus(this);
	Minotaur minotaur = new Minotaur(this);
	Thread gameThread;
	int gameFPS = 60;
	/**
	 * The class is a JPanel
	 * has to implement run method
	 */
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	/**Game Loop
	 * updates the position of characters
	 * and repaints them
	 * this loop goes really fast as long as the thread is running
	 * (searched on the internet for the following code)
	 * GameFPS = 60
	 * make the thread sleep on the remainingTime
	 */
	@Override
	public void run() {
		double drawInterval = 1000/gameFPS; // miliseconds 3 zeros
		double nextDrawTime = System.currentTimeMillis() + drawInterval;

		while(gameThread!=null) {
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.currentTimeMillis();
				if (remainingTime < 0) remainingTime = 0;
				Thread.sleep((long)remainingTime);
				nextDrawTime = nextDrawTime + drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * when Theseus/Minotaur aren't too close
	 * update their position
	 */
	public void update() {
		double distance = Math.sqrt(Math.pow(theseus.x-minotaur.x, 2) + Math.pow(theseus.y-minotaur.y, 2));
		if (distance>7*tileSize/8) {
			theseus.update();
			minotaur.update();
		}
	}
	/**
	 * We call everyone's draw method
	 * when Theseus's icon is too close on Minotaur's icon
	 * minotaur stops from being painted 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		// Carefull, the order of drawing matters!
		labyrinth.draw(g2);
		theseus.draw(g2);
		double distance = Math.sqrt(Math.pow(theseus.x-minotaur.x, 2) + Math.pow(theseus.y-minotaur.y, 2));
		if (distance>7*tileSize/8) {
			minotaur.draw(g2);
		}
		g2.dispose();
	}
}
