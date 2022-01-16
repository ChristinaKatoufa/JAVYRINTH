package game.main;

/**
 * A simple class for outside modules to have access
 * of this module's selected data.
 */
public final class ModuleData {
	/**
	 *  Fields shouldn't be accessible from outside
	 *  By convention if game is at STATE_EXIT this should be -1. 
	 *  By default even if it hasn't instansiated = 0. 
	 *  Thus we can distinct the cases between it hasn't been set yet, 
	 *  and asked to quit game.
	 */
	private static volatile long gameplayDuration; // By convention if game is at STATE_EXIT this should be -1. By default even if it hasn't instansiated = 0. Thus we can distinct the cases between it hasn't been set yet, and asked to quit game.
	
	/**
	 * Forbid the usage of the default constructor from outside the class
	 */
	private ModuleData() {}
	
	/**
	 * Method to use inside the module package to bring out selected data
	 * @param duration
	 */
	static void setGameplayDuration (long duration) {
		gameplayDuration = duration;
	}
	
	/**
	 * Publicly available method for external modules to acquire selected data
	 * @return duration
	 */
	public static long getGameplayDuration() {
		return gameplayDuration;
	}
}
