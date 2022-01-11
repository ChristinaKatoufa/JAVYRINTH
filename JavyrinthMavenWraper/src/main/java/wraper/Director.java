package wraper;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Director {

	private static String userName;
	private static long gameplayDuration;
	private static int quizCorrectAnswers;

	public static void main(String[] args) {
		System.out.println("Director of JavyrinthMaven");
		
		System.out.println("Calling JavyrinthMaven loginModule");
		login.Main.main(args);
		userName = login.ModuleData.getUserName();
		if (userName == null) System.exit(0);
		System.out.println("Calling JavyrinthMaven gameModule");
		game.main.Main.main(args);
		do {
			gameplayDuration = game.main.ModuleData.getGameplayDuration();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (gameplayDuration==0);
		
		System.out.println("userName: " + userName + " , gameplayDuration: " + gameplayDuration
				+ " , quizCorrectAnswers: " + quizCorrectAnswers);
		
		if (gameplayDuration == -1) System.exit(0);
		System.out.println("Calling JavyrinthMaven quizModule");
		quiz.Main.main(args);
		do {
			quizCorrectAnswers = quiz.ModuleData.getQuizCorrectAnswers();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (quizCorrectAnswers==0);
		if (quizCorrectAnswers == -1) System.exit(0);
		
		System.out.println("userName: " + userName + " , gameplayDuration: " + gameplayDuration
				+ " , quizCorrectAnswers: " + quizCorrectAnswers);

		int totalScore = (int) (quizCorrectAnswers * 100 + (100-gameplayDuration) * 10);
		totalScore = totalScore > 0 ? totalScore : 0;
		
		System.out.println("Total Score: " + totalScore);
		
		JOptionPane.showMessageDialog(new JFrame(), 
				"Dear, "+userName+" your total score is: "+totalScore
				+"\nYou eliminated Minotaur in: "+gameplayDuration+" seconds."
				+"\nSuccessfully answered: "+quizCorrectAnswers+" quiz questions."
				+"\n[score = quizCorrectAnswers*100 + (100-gameplayDuration)*10]");
		

	}

	/*
	 * public class Event { String data; public Event(String data) { this.data =
	 * data; } }
	 * 
	 * public interface EventListener { public void dispatchEvent(Event e); }
	 */

}
