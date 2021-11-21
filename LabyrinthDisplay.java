import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class LabyrinthDisplay extends JFrame {
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int i = 0;
		int j = 0;
		for (i = 0; i < sizeX; i++) {
			for (j = 0; j < sizeY; j++) {
				Color colors;
				switch (Cells[i][j]) {
				case 1:
					colors = Color.WHITE;
					g.setColor(colors);
					g.drawRect(i, j, 20, 20);

					break;
				case 0:
					colors = Color.darkGray;
					g.setColor(colors);
				}
				g.setColor(colors);
				g.fillRect(i, j, 20, 20);
			}

		}

	}
}
