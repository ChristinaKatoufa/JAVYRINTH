package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import character.AI;
import character.DIR;
import character.Minotaur;
import character.Theseus;
import character.AI.Route;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	
	// Screen Settings
	final int originalTileSize = 32;
	final int scale = 1;
	
	public final int tileSize = originalTileSize*scale;
	public final int maxScreenRow = 18;
	public final int maxScreenCol = 24; // 18*(16/9)=32 , 18*(4/3)=24
	public final int screenWidth = tileSize*maxScreenCol;
	public final int screenHeight = tileSize*maxScreenRow;
	
	public final int STATE_START_SCREEN = 1;
	public final int STATE_RUNNING = 2;
	public final int STATE_PAUSED = 3; 
	public final int STATE_EXIT = 4;
	public final int STATE_GAMEOVER = 5;
	
	int gameFPS = 60;
	
	Thread gameThread;
	public TileManager tileManager = new TileManager(this);
	public Theseus theseus;
	public Minotaur minotaur;
	private UI ui;
	private Audio audioChannel;
	private GamePanel gamePanel = this;
	public int gameState = STATE_START_SCREEN;
	private boolean isPaused = true;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		
		theseus = new Theseus(this);
		minotaur = new Minotaur(this); 
		audioChannel = new Audio();
		addMainKeyListener();
		ui = new UI(this);
		
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000/gameFPS; // miliseconds 3 zeros
		double nextDrawTime = System.currentTimeMillis() + drawInterval;
		
		ui.toggleMessageRect();
		audioChannel.play(Audio.START);
		//audioChannel.playNext(Audio.MUSIC);
		//ui.startChronometer();
		//ui.toggleMessageRect();
		
		/*
		new Thread(new Runnable() {
			@Override
			public void run() {
				audioChannel.play(Audio.START);
				//audioChannel.playNext(Audio.MUSIC);
			}
		}).start();
		*/
		
		// Game Loop
		while(gameThread!=null && gameState!=STATE_GAMEOVER) {
			
			double distance = Math.sqrt(Math.pow(theseus.x-minotaur.x, 2) + Math.pow(theseus.y-minotaur.y, 2));
			if (distance>15*tileSize/16) {
				
				if(!isPaused) {
					update();
				}
				
				repaint();
			}
			else {
				//minotaur.lifeCounter = 0;
				//minotaur.update();
				audioChannel.stop();
				minotaur.dying ();
				repaint();
			}
			
			
			try {
				double remainingTime = nextDrawTime - System.currentTimeMillis();
				if (remainingTime<0) remainingTime = 0;
				Thread.sleep((long)remainingTime);
				nextDrawTime = nextDrawTime + drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Game Over! Elapsed Time(msec): "+ui.getElapsedTime());
		
	}
	
	public void update() {
		// update game assets
		
		ui.update();
		theseus.update();
		minotaur.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// Carefull, the order of drawing matters! The latest covers the earliest
		
		tileManager.draw(g2);
		theseus.draw(g2);
		//double distance = Math.sqrt(Math.pow(theseus.x-minotaur.x, 2) + Math.pow(theseus.y-minotaur.y, 2));
		//if (distance>7*tileSize/8) {
			minotaur.draw(g2);
		//}
		ui.draw(g2);
		
		g2.dispose();
	}
	
	private void addMainKeyListener() {
		
		KeyListener keyListener = new KeyListener() {
			//public String move = "";

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER :
					// start pause unpause game
					isPaused = !isPaused;
					audioChannel.stop();
					ui.togglePauseChronometer();
					if(isPaused) {
						if(gameState!=STATE_GAMEOVER) gameState = STATE_PAUSED;
					}
					else {
						if(gameState!=STATE_GAMEOVER) audioChannel.play(Audio.MUSIC);
						if(gameState==STATE_START_SCREEN) {
							gameState = STATE_RUNNING;
						}
					}
					ui.toggleMessageRect();
					break;
				case KeyEvent.VK_ESCAPE :
					// exit game
					if(gameState==STATE_EXIT) { gameState = STATE_GAMEOVER; break;}
					if(isPaused) {
						ui.toggleMessageRect();
					}
					else {
						ui.togglePauseChronometer();
						audioChannel.stop();
						isPaused = true;
					}
					gameState = STATE_EXIT;
					ui.toggleMessageRect();
					break;
				case KeyEvent.VK_B :
					// debug game
					System.out.println("--- Debug ---");
					ArrayList<Route> c1Routes = new AI(gamePanel).getRoutes(minotaur.x, minotaur.y, DIR.O, 6);
					ArrayList<Route> c2Routes = new AI(gamePanel).getRoutes(theseus.x, theseus.y, DIR.O, 2);
					System.out.println("- Minotaur getRoutes -");
					AI.printRoutes(c1Routes);
					System.out.println("----------------");
					System.out.println("- Minotaur Route -");
					System.out.println("- Theseus getRoutes -");
					AI.printRoutes(c2Routes);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			
		};
		
		this.addKeyListener(keyListener);
		
	}
	
	public boolean crash() {
		
		double distance = Math.sqrt(Math.pow(theseus.x-minotaur.x, 2) + Math.pow(theseus.y-minotaur.y, 2));
		return (distance<=tileSize/4) ? true : false;
		
	}
	
}
