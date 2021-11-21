
public class Labyrinth {

	public final static int sizeX = 20;
 	public final  static int sizeY = 20;
    
    public Labyrinth() {	
    
		int[][] Cells = (int[][]) Cell.initializeCells(sizeX, sizeY);
		printLabyrinth(sizeX, sizeY, Cells);
    }	
	
	
	 public static void printLabyrinth(int x, int y, int[][] z) {
		int i = 0;
		int j = 0;
		
		for (i = 0; i < x; i++) {
			for (j = 0; j < y; j++) {
				System.out.printf("%d", z[i][j]);
				
			}
			System.out.println();
		}
	}
}

