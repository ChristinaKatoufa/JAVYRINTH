import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
/** Creating an animated object using the arrows in keyboard
 * 
 * @author Christina Katoufa
 * @author christinakatoufa@gmail.com
 * 
 *
 */
	public class MovingObjects extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
		/** The class is a JPanel 
		 * 	HAS TO override methods of the keyListener interface (keyPressed , keyReleased , keyTyped)
		 * HAS TO override the method of ActionListener (actionPerformed)
		 * 
		 * Initializing fields x,y (position of object), and speed of x,y (no movement) 
		 * Creating a Timer object to trigger an action event
		 * Timer: 1st parameter is delay (a key is pressed and there's a delay (milliseconds)at the beginning and between events)
		 * 2nd parameter refers to the fields (coordinates, speed)
		*/ 
		protected int x = 0, y = 0, speedOfx = 0,speedOfy = 0;
		private Timer timer = new Timer(0, this);
		/** Constructor
		 * timer starts sending action events to its listeners
		 * KeyEvents added to this class and fields
		 * needs focus to work (can't explain more on that yet :P)
		*/ 
		public MovingObjects() {
			timer.start();
			addKeyListener(this);
			setFocusable(true);
		}
		/**Paints an object on our panel 
		 * we use the Graphics library for that
		 * just in order for it to work, 
		 * need to call the method of the superClass to paint correctly
		 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	/**Player on keyboard
	 * stores the keyEvent of player to the variable movement
	 * and we divide the cases to the arrow keys
	 * each arrow calls a method and breaks out of the 'switch'
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		int movement = key.getKeyCode();
		switch (movement) {
			case KeyEvent.VK_UP: up();
				break;
			case KeyEvent.VK_DOWN: down();
				break;
			case KeyEvent.VK_RIGHT: right();
				break;
			case KeyEvent.VK_LEFT: left();
				break;
		}
	}
	public void up() {
		speedOfx = 0;
		speedOfy = -1;
	}
	public void down() {
		speedOfx = 0;
		speedOfy = 1;
	}
	public void right() {
		speedOfx = 1;
		speedOfy = 0;
	}
	public void left() {
		speedOfx = -1;
		speedOfy = 0;
	}
	/**We have to override these methods
	 * but I don't want my object to stop moving
	 * when I release the key
	 * The object will change direction only when ArrowKeys are pressed
	 */
	@Override
	public void keyReleased(KeyEvent key) {
	}
	@Override
	public void keyTyped(KeyEvent key) {
	}
	/**Action
	 * The timer starts, the keyPress triggers the actionEvent
	 * This method shows:
	 * the change of positionX,posisitionY of the shape
	 * Calling method repaint of SuperClass JPanel
	 * to paint our shape all over again in the new position its in
	 * Calling our method notOffLimits
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		x += speedOfx;
		y += speedOfy;
		repaint();
		notOffLimits();
	}

	/**This method:
	 * prevents the shape to move more than the window limits
	 */
	public void notOffLimits() {
		if (x < 0) {
			speedOfx = 0;
			x=0;
		}
		if (x >= 550) {
			speedOfx = 0;
			x = 550;
		}
		if (y < 0) {
			speedOfy = 0;
			y=0;
		}
		if (y >= 300) {
			speedOfy = 0;
			y = 300;
		}
	}
	public int getPosX() {
		return x;
	}
	public int getPosY() {
		return y;
	}
	public void setPosX(int x) {
		this.x = x;
	}
	public void setPosY(int y) {
		this.y = y;
	}
}
