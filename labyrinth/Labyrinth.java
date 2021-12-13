package labyrinth;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;
/**
 * 
 * @author ChristinaKatoufa
 * Labyrinth fields
 * 	Main GamePanel (Screen settings)
 * 	1 dimensional array of tiles (stores the images of every tile)
 * 	2 dimensional array (stores the number ids of map tiles)
 */
public class Labyrinth {
	/**Inner Class Tile
	 *that needs icons for every tile
	 *that has/hasn't solid tiles
	 */
public class Tile {
	public BufferedImage image;
	public boolean solid = false;
}
	GamePanel gp;
	public Tile[] tile;
	public int labyrinthMap[][];
	
	public Labyrinth(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[8];
		labyrinthMap = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap("/maps/map.txt");
	}
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].solid = true;
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pillar.png"));
			tile[2].solid = true;
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png"));
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fire.png"));
			tile[4].solid = true;
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grave.png"));
			tile[5].solid = true;
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/throne.png"));
			tile[6].solid = true;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void loadMap(String map) {
		try {
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			while(col<gp.maxScreenCol && row<gp.maxScreenRow) {
				String line = br.readLine();
				while(col<gp.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					labyrinthMap[col][row] = num;
					col++;
				}
				if(col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			int tileNum = labyrinthMap[col][row];
			// (carpet)Background support for added transparent tiles on top 
			g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null); 
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x = x + gp.tileSize;
			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y = y + gp.tileSize;
			}
		}
	}	
}
