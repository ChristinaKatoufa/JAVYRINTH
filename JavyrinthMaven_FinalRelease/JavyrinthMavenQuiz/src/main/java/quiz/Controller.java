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
			dbData.add(new String[] { "ÌÝ÷ñé ðïõ åêôåßíïíôáí ðñïò âïññÜ ïé åìðïñéêïß äñüìïé ðïõ ÷ñçóéìïðïéïýóáí ïé Ìéíùúôåò ìå ôá ðëïßá ôïõò;","ôçí Ìáêåäïíßá","ôçí ×áëêßäá","ôçí ÁèÞíá","ôéò ÌõêÞíåò","4" });
			dbData.add(new String[] { "×áñáêôçñéóôéêÜ ôïõ ÄùäåêÜèåïõ Þôáí ç áèáíáóßá êáé ç áéþíéá íåüôçôá. Óå ôé ïöåßëïíôáí êáôÜ ôç ìõèïëïãßá; ","Óôç èåúêÞ ôïõò õðüóôáóç.","Óôçí áìâñïóßá.","Óôï íÝêôáñ.","Ôï â, ã ìáæß.","4" });
			dbData.add(new String[] { "Ðïéá åßíáé ç åðéêñáôÝóôåñç åêäï÷Þ ôïõ ìýèïõ ôçò? áðüäñáóçò ôïõ Äáßäáëïõ êáé ôïõ ºêáñïõ;? ","ìå Ýíá ðëïßï ðïõ êùðçëáôïýóáí","ìå äýï ðëïßá?ðïõ êùðçëáôïýóáí","ìå éóôéïðëïúêü äéêÞò ôïõò åðéíüçóçò","ìå öôåñÜ óôåñåùìÝíá ìå êåñß","4" });
			dbData.add(new String[] { "Ðüôå îåêßíçóå ï Ðåëïðïííçóéáêüò ðüëåìïò êáé ðïéïò ôïí êÝñäéóå;","431 ð.×., ç ÐåëïðïííçóéáêÞ óõììá÷ßá","404 ð.×., ç ÁèçíáúêÞ óõììá÷ßá","431 ð.×., ç ÄçëéáêÞ óõììá÷ßá","430 ð.×., ç ÄçëéáêÞ óõììá÷ßá","1" });
			dbData.add(new String[] { "Ðïéïò ðñüôåéíå ôçí ëýóç  óôçí ùñáßá ÅëÝíç ãéá ôçí åðéëïãÞ ôïõ ìåëëïíôéêïý óõæýãïõ ôçò ÌåíÝëáïõ;  ","ï Èåüò Äßáò","Ï ÏäõóóÝáò ôçò ÉèÜêçò","Ï ÔõíäÜñåùò ôçò ÓðÜñôçò","ï ÁãáìÝìíïíáò ôùí Ìõêçíþí ","2" });
			dbData.add(new String[] { "Óå ðüóåò ðüëåéò – êñÜôç äéáéñïýíôáí ç ÓôåñåÜ ÅëëÜäá; ","10","11","12","9","3" });
			dbData.add(new String[] { "Ðïéá Þôáí ôá ðáéäéÜ ðïõ áðÝêôçóáí ï Ïéäßðïäáò êáé ç ÉïêÜóôç;","ï ÅôåïêëÞò êáé ï Ðïëõíåßêçò","ï Äáßäáëïò êáé ï ºêáñïò","ï ÇñáêëÞò êáé ï ÔÜëùò","ï Ìßíùáò êáé ç ÐáóéöÜç","1" });
			dbData.add(new String[] { "Áðü ðïõ êáôáãüôáí ï ÈçóÝáò;","áðü ôçí ÊñÞôç","áðü ôçí ÔñïéæÞíá","áðü ôçí ÁèÞíá","áðü ôçí ÈÞâá","2" });
			dbData.add(new String[] { "Ðüóá ÷ñüíéá äéÞñêçóå ï Ôñùéêüò ðüëåìïò; ","9 ÷ñüíéá","10 ÷ñüíéá","11 ÷ñüíéá","9.5 ÷ñüíéá","2" });
			dbData.add(new String[] { "Ðïõ ãåííÞèçêáí ïé 9 Ìïýóåò (èåüôçôåò, êüñåò ôïõ Äßá êáé ôçò Ìíçìïóýíçò);","óôïí ¼ëõìðï","óôïí Êßóáâï","óôá ÐéÝñéá ¼ñç","óôïí Øçëïñåßôç","3" });
			*/
		}
		String[] dbDataRow = dbData.get(askedQuestions - 1); 
		//String[] dbDataLine = dbData.remove(0); //The same as above, but we will mess correctAns below
		String question = dbDataRow[0], answer1 = dbDataRow[1], answer2 = dbDataRow[2], answer3 = dbDataRow[3], answer4 = dbDataRow[4];
		window.setWindowData("Åñþôçóç " + askedQuestions + " / " + totalQuestions, question, answer1, answer2, answer3, answer4);
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
	private void gameOver() {
		if (errors > errorsAllowed) {
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
		 *  Waits 2,5 sec and closes the window
		 */
		Timer close = new Timer(2500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.closeWindow();
				sounds.stop();
				//sounds.clip.close();
				ModuleData.setQuizCorrectAnswers(askedQuestions - errors);
				System.out.println("quizCorrectAnswers: "+(askedQuestions - errors));
				
			}

		});
		// if not used timer restarts itself every 2500msec! This command will cause timer to run once.
		close.setRepeats(false);
		close.start();
		
		ModuleData.setQuizCorrectAnswers(askedQuestions - errors);
	}
}
