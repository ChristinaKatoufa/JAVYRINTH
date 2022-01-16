package quiz;
/**
 * A simple class for outside modules to have access
 * of this module's selected data.
 */
public final class ModuleData {

	/**
	 * Fields shouldn't be accessible from outside
	 */
	private static int quizCorrectAnswers;
	
	/**
	 * Forbid the usage of the default constructor from outside the class
	 */
	private ModuleData() {}
	
	/**
	 * Method to use inside the module package to bring out selected data
	 * @param correctAnswers
	 */
	static void setQuizCorrectAnswers (int correctAnswers) {
		quizCorrectAnswers = correctAnswers;
	}
	
	/**
	 * Publicly available method for external modules to acquire selected data
	 * @return quizCorrectAnswers
	 */
	public static int getQuizCorrectAnswers() {
		return quizCorrectAnswers;
	}
}
