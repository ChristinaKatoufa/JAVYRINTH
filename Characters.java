public interface Characters {
	/**  This is the blueprint of Classes that have Characters
	 * 
	 * @author Christina Katoufa
	 * @author christinakatoufa@gmail.com
	 *
	 *Our game has 2 Characters (Theseas and Minotaur)
	 * Here we create methods, that our Characters will have to implement
	 * each one in a different way
	 * Their common functionalities are:
	 */
		public abstract void image();
		public abstract void move();
		public abstract void dies();
}