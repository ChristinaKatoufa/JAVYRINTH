import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class Tests {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("J@VYRINTH");
		frame.setSize(600, 400);
		frame.setResizable(false);
		ImageIcon img = new ImageIcon(".\\pics\\logo2.png");
		frame.setIconImage(img.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Theseas player = new Theseas();
		frame.add(player);
		frame.setVisible(true);
		player.requestFocusInWindow();
	}
}
