package wraper;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * This is class who operates all three projects
 * Gets the username of player from login
 * Gets duration from labyrinth game
 * Gets score from the quiz
 * 
 * Overrides run methods for each project and starts the threads threads
 * Calculates the TotalScore in the end
 */
public class Director {

	private static String userName;
	private static long gameplayDuration = -2; // -2 Not any data yet, -1 request to Exit
	private static int quizCorrectAnswers = -2;
	
	static Thread threadLogin, threadGame, threadQuiz;

	public static void main(String[] args) {
		Runnable loginRunnable = new Runnable () {
			@Override
			public void run() {
				login.Main.main(args);
				do {
					userName = login.ModuleData.getUserName();
				} while (userName == null);
				if (userName.equals("EXIT->")) System.exit(0);
			}
		};
		
		Runnable gameRunnable = new Runnable () {
			@Override
			public void run() {
				game.main.Main.main(args);
				do {
					gameplayDuration = game.main.ModuleData.getGameplayDuration();
				} while (gameplayDuration == -2);
				if (gameplayDuration == -1) System.exit(0);
			}
		};
		
		Runnable quizRunnable = new Runnable () {
			@Override
			public void run() {
				quiz.Main.main(args);
				do {
					quizCorrectAnswers = quiz.ModuleData.getQuizCorrectAnswers();
				} while (quizCorrectAnswers == -2);
				if (quizCorrectAnswers == -1) System.exit(0);
			}
		};
			
		threadLogin = new Thread(loginRunnable);
		threadGame = new Thread(gameRunnable);
		threadQuiz = new Thread(quizRunnable);
		
		threadLogin.start();
		try {
			threadLogin.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		threadGame.start();
		try {
			threadGame.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		threadQuiz.start();
		try {
			threadQuiz.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int totalScore = (int) (quizCorrectAnswers * 100 + (100 - gameplayDuration) * 10);
		totalScore = totalScore > 0 ? totalScore : 0;
		
		DBConnections.insertScore(userName, totalScore);	
			
		JOptionPane.showMessageDialog(new JFrame(), 
				"Dear, "+userName+" your total score is: "+totalScore
				+"\nYou eliminated Minotaur in: "+gameplayDuration+" seconds."
				+"\nSuccessfully answered: "+quizCorrectAnswers+" quiz questions."
				+"\n[score = quizCorrectAnswers * 100 + (100 - gameplayDuration) * 10]");
		
		Audio audio = new Audio();
		audio.play(Audio.MENU);
		WindowScores window = new WindowScores();
		window.setWindowData(DBConnections.selectScores(userName));
	}
}
