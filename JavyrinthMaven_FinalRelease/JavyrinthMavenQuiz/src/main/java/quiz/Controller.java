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
			dbData.add(new String[] { "Μέχρι που εκτείνονταν προς βορρά οι εμπορικοί δρόμοι που χρησιμοποιούσαν οι Μινωϊτες με τα πλοία τους;","την Μακεδονία","την Χαλκίδα","την Αθήνα","τις Μυκήνες","4" });
			dbData.add(new String[] { "Χαρακτηριστικά του Δωδεκάθεου ήταν η αθανασία και η αιώνια νεότητα. Σε τι οφείλονταν κατά τη μυθολογία; ","Στη θεϊκή τους υπόσταση.","Στην αμβροσία.","Στο νέκταρ.","Το β, γ μαζί.","4" });
			dbData.add(new String[] { "Ποια είναι η επικρατέστερη εκδοχή του μύθου της? απόδρασης του Δαίδαλου και του Ίκαρου;? ","με ένα πλοίο που κωπηλατούσαν","με δύο πλοία?που κωπηλατούσαν","με ιστιοπλοϊκό δικής τους επινόησης","με φτερά στερεωμένα με κερί","4" });
			dbData.add(new String[] { "Πότε ξεκίνησε ο Πελοποννησιακός πόλεμος και ποιος τον κέρδισε;","431 π.Χ., η Πελοποννησιακή συμμαχία","404 π.Χ., η Αθηναϊκή συμμαχία","431 π.Χ., η Δηλιακή συμμαχία","430 π.Χ., η Δηλιακή συμμαχία","1" });
			dbData.add(new String[] { "Ποιος πρότεινε την λύση  στην ωραία Ελένη για την επιλογή του μελλοντικού συζύγου της Μενέλαου;  ","ο Θεός Δίας","Ο Οδυσσέας της Ιθάκης","Ο Τυνδάρεως της Σπάρτης","ο Αγαμέμνονας των Μυκηνών ","2" });
			dbData.add(new String[] { "Σε πόσες πόλεις – κράτη διαιρούνταν η Στερεά Ελλάδα; ","10","11","12","9","3" });
			dbData.add(new String[] { "Ποια ήταν τα παιδιά που απέκτησαν ο Οιδίποδας και η Ιοκάστη;","ο Ετεοκλής και ο Πολυνείκης","ο Δαίδαλος και ο Ίκαρος","ο Ηρακλής και ο Τάλως","ο Μίνωας και η Πασιφάη","1" });
			dbData.add(new String[] { "Από που καταγόταν ο Θησέας;","από την Κρήτη","από την Τροιζήνα","από την Αθήνα","από την Θήβα","2" });
			dbData.add(new String[] { "Πόσα χρόνια διήρκησε ο Τρωικός πόλεμος; ","9 χρόνια","10 χρόνια","11 χρόνια","9.5 χρόνια","2" });
			dbData.add(new String[] { "Που γεννήθηκαν οι 9 Μούσες (θεότητες, κόρες του Δία και της Μνημοσύνης);","στον Όλυμπο","στον Κίσαβο","στα Πιέρια Όρη","στον Ψηλορείτη","3" });
			*/
		}
		String[] dbDataRow = dbData.get(askedQuestions - 1); 
		//String[] dbDataLine = dbData.remove(0); //The same as above, but we will mess correctAns below
		String question = dbDataRow[0], answer1 = dbDataRow[1], answer2 = dbDataRow[2], answer3 = dbDataRow[3], answer4 = dbDataRow[4];
		window.setWindowData("Ερώτηση " + askedQuestions + " / " + totalQuestions, question, answer1, answer2, answer3, answer4);
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
