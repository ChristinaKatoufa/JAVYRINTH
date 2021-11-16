import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/** Creating the player of our game
 * 
 * @author Christina Katoufa
 * @author christinakatoufa@gmail.com
 * 
 *
 */
public class Theseas extends MovingObjects implements Characters {
	/**
	 * Theseas Is a MovingObject from the user (keyboardUser)
	 * Has to implement methods from Interface Characters
	 */
	
	private Image image;
	/**Theseas Constructor
	 * calls method image of the player
	 * Change x,y variables from SuperClass MovingoObject
	 * setting this way, the starting point of Theseas inside the labyrinth
	 */
	
	public Theseas() {
		image();
		setPosX(80);
		setPosY(40);
	}
	public void image() {
		 this.image = Toolkit.getDefaultToolkit().getImage(".\\pics\\logo2.png");
	}
	public void move() {
		actionPerformed(null);
	}
	/**
	 * We will create an effect in our image of Theseas
	 * to show that the player loses the game
	 */
	public void dies() {

	}
	/**Overrides the paint method of SuperClass
	 * Paints Theseas Icon on our panel 
	 * Needs superClass again (leaves marks when moving without it)
	 * @param image, our coordinates, ImageObserver(null)
	 */
	@Override
	public void paint(Graphics g) {
	    super.paint(g);
	    g.drawImage(image, x, y, null);
	}
}
