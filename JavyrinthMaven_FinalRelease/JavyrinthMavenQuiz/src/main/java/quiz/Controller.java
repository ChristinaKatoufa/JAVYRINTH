package quiz;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import quiz.Window.PlayerAnsListener;

public class Controller {

	private Window window;
	private PlayerAnsListener playerAnsListener;
	private int errors;
	private final int totalQuestions = 10;
	private final int errorsAllowed = 2;
	private final int coundown = 20;
	private int askedQuestions;
	private ArrayList<String[]> dbData;
	private Sound sounds;

	public Controller() {
		
		sounds = new Sound();
		
		//window = new Window();
		
		//* https://stackoverflow.com/questions/22534356/java-awt-eventqueue-invokelater-explained
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Window();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		nextQuestion();

		playerAnsListener = new Window.PlayerAnsListener() {
			@Override
			public void onPlayerAns(String playerAns) {
				PlayerAnswer(playerAns);
			}
		};

		window.addPlayerAnsListener(playerAnsListener);	

		window.timerStart(coundown);
		
	}
	/**
	 * When the list of questions is empty
	 * gets randomly 10 questions from our DB
	 */
	private void nextQuestion() {
		
		askedQuestions++;
		
		if (dbData == null) {
			this.dbData = DBConnections.getData(totalQuestions);
			/*
			// For testing purposes
			this.dbData = new ArrayList<String[]>();
			dbData.add(new String[] { "����� ��� ����������� ���� ����� �� ��������� ������ ��� ��������������� �� �������� �� �� ����� ����;","��� ���������","��� �������","��� �����","��� �������","4" });
			dbData.add(new String[] { "�������������� ��� ���������� ���� � �������� ��� � ������ �������. �� �� ���������� ���� �� ���������; ","��� ����� ���� ��������.","���� ��������.","��� ������.","�� �, � ����.","4" });
			dbData.add(new String[] { "���� ����� � ������������� ������ ��� ����� ���? ��������� ��� �������� ��� ��� ������;? ","�� ��� ����� ��� ������������","�� ��� �����?��� ������������","�� ����������� ����� ���� ���������","�� ����� ���������� �� ����","4" });
			dbData.add(new String[] { "���� �������� � ��������������� ������� ��� ����� ��� �������;","431 �.�., � �������������� ��������","404 �.�., � �������� ��������","431 �.�., � ������� ��������","430 �.�., � ������� ��������","1" });
			dbData.add(new String[] { "����� �������� ��� ����  ���� ����� ����� ��� ��� ������� ��� ����������� ������� ��� ��������;  ","� ���� ����","� �������� ��� ������","� ��������� ��� �������","� ����������� ��� ������� ","2" });
			dbData.add(new String[] { "�� ����� ������ � ����� ����������� � ������ ������; ","10","11","12","9","3" });
			dbData.add(new String[] { "���� ���� �� ������ ��� ��������� � ��������� ��� � �������;","� �������� ��� � ����������","� �������� ��� � ������","� ������� ��� � �����","� ������ ��� � �������","1" });
			dbData.add(new String[] { "��� ��� ��������� � ������;","��� ��� �����","��� ��� ��������","��� ��� �����","��� ��� ����","2" });
			dbData.add(new String[] { "���� ������ �������� � ������� �������; ","9 ������","10 ������","11 ������","9.5 ������","2" });
			dbData.add(new String[] { "��� ���������� �� 9 ������ (��������, ����� ��� ��� ��� ��� ����������);","���� ������","���� ������","��� ������ ���","���� ���������","3" });
			*/
		}
		String[] dbDataRow = dbData.get(askedQuestions - 1); 
		//String[] dbDataLine = dbData.remove(0); //The same as above, but we will mess correctAns below
		String question = dbDataRow[0], answer1 = dbDataRow[1], answer2 = dbDataRow[2], answer3 = dbDataRow[3], answer4 = dbDataRow[4];
		window.setWindowData("������� " + askedQuestions + " / " + totalQuestions, question, answer1, answer2, answer3, answer4);
	}
	/**
	 * Gets player answer, plays SoundFX, 
	 * shows errors on window, so player will know when he's about to lose
	 * @param playerAns
	 */
	private void PlayerAnswer(String playerAns) {

		String correctAns = dbData.get(askedQuestions - 1)[dbData.get(askedQuestions - 1).length - 1];
		int soundID;
		
		//System.out.println("askedQuestions: " + askedQuestions + " , playerAns: " + playerAns + " , correctAns: " + correctAns);

		if (playerAns.equals(correctAns)) {
			soundID = Sound.CORRECT;
		} else {
			errors++;
			window.setErrors(String.valueOf(errors));
			soundID = Sound.WRONG;
		}

		sounds.play(soundID);
		
		// wait for a few seconds
		// DOES NOT UPDATE WINDOW COMPONENTS! Not good practice to use with JFrame,
		//it stops refresh its components (timer freeze at 1)
		/*
		try {
			Thread.sleep(1700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		// pause for a a seconds
		Timer pause = new Timer(1700, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (errors <= errorsAllowed && askedQuestions < totalQuestions) {
					nextQuestion();
					window.timerStart(coundown);
				}
				else {
					gameOver();
				}
			}
		});
		pause.setRepeats(false); // if used with JFrame timer restarts itself every 1700msec! 
		//This command will cause timer to run once.
		pause.start();
	}
	/**
	 * window pop up when game is over
	 * gets you the score
	 */
	private void gameOver () {
		if (errors>=3) {
			window.setWindowData("<html>You Lose!<br>Score: "+(askedQuestions-errors)*100+" Points</html>", "GAME OVER!", "", "", "", "");
		}
		else {
			window.setWindowData("<html>You Win!<br>Score: "+(askedQuestions-errors)*100+" Points</html>", "GAME OVER!", "", "", "", "");
		}
		
		/*
		// DOES NOT UPDATE WINDOW COMPONENTS!
		// Wait 3sec and close the window
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		window.closeWindow();
		*/
		
		/**
		 *  Waits 2sec and closes the window
		 */
		new Timer(2500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.closeWindow();
			}

		}).start();
		
		ModuleData.setQuizCorrectAnswers(askedQuestions-errors);
	}
}
