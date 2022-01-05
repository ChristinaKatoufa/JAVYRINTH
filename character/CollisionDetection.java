package character;

import java.util.ArrayList;
import java.util.Collections;

import main.GamePanel;

public class CollisionDetection {
	
	GamePanel gamePanel;
	GameCharacter gameCharacter;
	
	//final ArrayList<String> adjacentTile = new ArrayList<String>( Arrays.asList("N", "S", "W", "E") );
	//enum DIR { N, S, W, E, O; }; // North, South, East, West, O : point zero
	//enum DIR { N, S, W, E; }; // North, South, East, West
	
	public CollisionDetection(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public CollisionDetection(GamePanel gamePanel, GameCharacter gameCharacter) {
		this.gamePanel = gamePanel;
		this.gameCharacter = gameCharacter;
	}

	protected DIR getCollision(DIR movement, int x, int y, int speed) {
		/*
		int top = y + this.solidArea.y;
		int bottom = top + this.solidArea.height;
		int left = x + this.solidArea.x;
		int right = left + this.solidArea.width;
		*/
		int top = y + gamePanel.tileSize/4;
		int bottom = top + gamePanel.tileSize/2;
		int left = x + gamePanel.tileSize/4;
		int right = left + gamePanel.tileSize/2;
		
		int characterTop = top/gamePanel.tileSize;
		int characterBottom = bottom/gamePanel.tileSize;
		int characterLeft = left/gamePanel.tileSize;
		int characterRight = right/gamePanel.tileSize;
		int characterNext;
			
		int tileNum1, tileNum2;
		
		DIR collision = null;
		
		// Check for surrounding solid tiles based on character's movement direction
		if (movement.equals(DIR.N)) {
			characterNext = (top - speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.labyrinth[characterLeft][characterNext];
			tileNum2 = gamePanel.tileManager.labyrinth[characterRight][characterNext];
			if(gamePanel.tileManager.tile[tileNum1].isSolid==true || gamePanel.tileManager.tile[tileNum2].isSolid==true) {
				collision = DIR.N;
			}
		}
		else if (movement.equals(DIR.S)) {
			characterNext = (bottom + speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.labyrinth[characterLeft][characterNext];
			tileNum2 = gamePanel.tileManager.labyrinth[characterRight][characterNext];
			if(gamePanel.tileManager.tile[tileNum1].isSolid==true || gamePanel.tileManager.tile[tileNum2].isSolid==true) {
				collision = DIR.S;
			}
		}
		else if (movement.equals(DIR.W)) {
			if(left-speed>0) { // Ean ta borders den exoun ola collision tiles
				characterNext = (left - speed)/gamePanel.tileSize;
				tileNum1 = gamePanel.tileManager.labyrinth[characterNext][characterTop];
				tileNum2 = gamePanel.tileManager.labyrinth[characterNext][characterBottom];
				if(gamePanel.tileManager.tile[tileNum1].isSolid==true || gamePanel.tileManager.tile[tileNum2].isSolid==true) {
					collision = DIR.W;
				}
			}
			else collision = DIR.W;
			
		}
		else if (movement.equals(DIR.E)) {
			characterNext = (right + speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.labyrinth[characterNext][characterTop];
			tileNum2 = gamePanel.tileManager.labyrinth[characterNext][characterBottom];
			if(gamePanel.tileManager.tile[tileNum1].isSolid==true || gamePanel.tileManager.tile[tileNum2].isSolid==true) {
				collision = DIR.E;
			}
		}
		//System.out.println("collision: "+collision);
		return collision;
	}
	
	protected ArrayList<DIR> getCollisions(int x, int y, int speed) {
		
		ArrayList<DIR> collisions = new ArrayList<DIR>();
		
		for(DIR direction : DIR.values()) {
			collisions.add(getCollision(direction, x, y, speed));
		}
		
		//* https://howtodoinjava.com/java/collections/arraylist/arraylist-removeall/
		collisions.removeAll(Collections.singleton(null)); // may cause java.lang.ArrayIndexOutOfBoundsException
		/*
		while(collisions.contains(null)) {
			collisions.remove(null);
		}
		*/
		
		return collisions;
	}
	
	protected ArrayList<DIR> getCollisions(ArrayList<DIR> directions, int x, int y, int speed) {
		
		ArrayList<DIR> collisions = new ArrayList<DIR>();
		
		for(DIR direction : directions) {
			collisions.add(getCollision(direction, x, y, speed));
		}
		
		//* https://howtodoinjava.com/java/collections/arraylist/arraylist-removeall/
		collisions.removeAll(Collections.singleton(null)); // may cause java.lang.ArrayIndexOutOfBoundsException
		/*
		while(collisions.contains(null)) {
			collisions.remove(null);
		}
		*/
		
		return collisions;
	}
	
}
