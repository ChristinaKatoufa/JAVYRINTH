package wraper;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * This is class who operates all three projects
 * Gets the username of player from login
 * Gets duration from labyrinth game
 * Gets score from the quiz
 * 
 * Calls out one Main Class after the other
 * 
 * and calculates the TotalScore
 * 
 */
public class Director {

	private static String userName;
	private static long gameplayDuration = -2;
	private static int quizCorrectAnswers = -2;

	public static void main(String[] args) {
		System.out.println("Director of JavyrinthMaven");
		
		//System.out.println("Calling JavyrinthMaven loginModule");
		login.Main.main(args);
		userName = login.ModuleData.getUserName();
		if (userName == null) System.exit(0);
		
		//System.out.println("Calling JavyrinthMaven gameModule");
		game.main.Main.main(args);
		do {
			gameplayDuration = game.main.ModuleData.getGameplayDuration();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (gameplayDuration == -2);
		if (gameplayDuration == -1) System.exit(0);
		
		//System.out.println("Calling JavyrinthMaven quizModule");
		quiz.Main.main(args);
		do {
			quizCorrectAnswers = quiz.ModuleData.getQuizCorrectAnswers();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (quizCorrectAnswers == -2);
		if (quizCorrectAnswers == -1) System.exit(0);

		int totalScore = (int) (quizCorrectAnswers * 100 + (100-gameplayDuration) * 10);
		totalScore = totalScore > 0 ? totalScore : 0;
		
		DBConnections.insertScore(userName, totalScore);	
			
		JOptionPane.showMessageDialog(new JFrame(), 
				"Dear, "+userName+" your total score is: "+totalScore
				+"\nYou eliminated Minotaur in: "+gameplayDuration+" seconds."
				+"\nSuccessfully answered: "+quizCorrectAnswers+" quiz questions."
				+"\n[score = quizCorrectAnswers*100 + (100-gameplayDuration)*10]");
		
		Audio audio = new Audio();
		audio.play(Audio.MENU);
		WindowScores window = new WindowScores();
		window.setWindowData(DBConnections.selectScores(userName));
	}
}
