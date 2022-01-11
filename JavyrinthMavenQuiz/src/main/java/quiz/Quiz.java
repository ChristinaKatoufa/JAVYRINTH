package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz implements ActionListener {
	int score2 = 0;
	int falseAnswers = 0;
	int totalquestions = 10;
	int secs = 60;
	int askedquestions = 0;
	int playerssays;
	String correctans;
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;
	
	JFrame quizframe = new JFrame();
	JTextArea questiontext = new JTextArea();
	JButton button_1 = new JButton();
	JButton button_2 = new JButton();
	JButton button_3 = new JButton();
	JButton button_4 = new JButton();
	JTextArea ans_1 = new JTextArea();
	JTextArea ans_2 = new JTextArea();
	JTextArea ans_3 = new JTextArea();
	JTextArea ans_4 = new JTextArea();
	JLabel time_left = new JLabel();
	JTextField score = new JTextField();
	JTextField finalresult = new JTextField();

	Timer timer = new Timer(900, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			secs--;
			time_left.setText(String.valueOf(secs));
			if (secs <= 0) {
				falseAnswers++;
				try {
					nextQuestion();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	});
	private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answer;
	private int qID;
	
	public Quiz() throws SQLException {

		quizframe.setSize(300, 400);
		quizframe.getContentPane().setBackground(new Color(232, 211, 185));
		quizframe.setTitle("Javyrinth");
		//quizframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		quizframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		quizframe.setLayout(null);
		quizframe.setResizable(false);
		quizframe.setLocationRelativeTo(null);
		// Handling if user tries to close the window
		quizframe.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int option = JOptionPane.showConfirmDialog(quizframe, "Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
				
		questiontext.setBounds(25, 50, 235, 50);
		questiontext.setLineWrap(true);
		questiontext.setWrapStyleWord(true);
		questiontext.setForeground(Color.BLACK);
		questiontext.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		questiontext.setBorder(BorderFactory.createEmptyBorder());
		

		button_1.setBounds(0, 110, 20, 20);
		button_1.setFocusable(false);
		button_1.addActionListener(this);

		button_2.setBounds(0, 140, 20, 20);
		button_2.setFocusable(false);
		button_2.addActionListener(this);

		button_3.setBounds(0, 170, 20, 20);
		button_3.setFocusable(false);
		button_3.addActionListener(this);

		button_4.setBounds(0, 200, 20, 20);
		button_4.setFocusable(false);
		button_4.addActionListener(this);

		ans_1.setBounds(25, 110, 250, 20);
		ans_1.setForeground(new Color(99, 69, 51));
		ans_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		ans_1.setLineWrap(true);
		ans_1.setEditable(false);

		ans_2.setBounds(25, 140, 250, 20);
		ans_2.setForeground(new Color(99, 69, 51));
		ans_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		ans_2.setLineWrap(true);
		ans_2.setEditable(false);

		ans_3.setBounds(25, 170, 250, 20);
		ans_3.setForeground(new Color(99, 69, 51));
		ans_3.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		ans_3.setLineWrap(true);
		ans_3.setEditable(false);

		ans_4.setBounds(25, 200, 250, 20);
		ans_4.setForeground(new Color(99, 69, 51));
		ans_4.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		ans_4.setLineWrap(true);
		ans_4.setEditable(false);

		time_left.setBounds(245, 5, 40, 40);
		time_left.setBackground(new Color(99, 69, 51));
		time_left.setForeground(Color.WHITE);
		time_left.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		time_left.setBorder(BorderFactory.createBevelBorder(1));
		time_left.setOpaque(true);
		time_left.setHorizontalAlignment(JTextField.CENTER);
		time_left.setText(String.valueOf(secs));

		score.setBounds(0, 5, 40, 40);
		score.setBackground(new Color(99, 69, 51));
		score.setForeground(Color.WHITE);
		score.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		score.setBorder(BorderFactory.createBevelBorder(1));
		score.setHorizontalAlignment(JTextField.CENTER);
		score.setText(String.valueOf(score2));
		score.setEditable(false);
		
		finalresult.setBounds(95, 120, 90, 90);
		finalresult.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		finalresult.setBorder(BorderFactory.createBevelBorder(1));
		finalresult.setBackground(new Color(43, 29, 20));
		finalresult.setForeground(Color.WHITE);
		finalresult.setHorizontalAlignment(JTextField.CENTER);
		finalresult.setEditable(false);

		quizframe.add(questiontext);
		quizframe.add(button_1);
		quizframe.add(button_2);
		quizframe.add(button_3);
		quizframe.add(button_4);
		quizframe.add(ans_1);
		quizframe.add(ans_2);
		quizframe.add(ans_3);
		quizframe.add(ans_4);
		quizframe.add(time_left);
		quizframe.add(score);
		quizframe.setVisible(true);

		nextQuestion();
	}
	
	public void nextQuestion() throws SQLException {
			
		if (falseAnswers < 3 && askedquestions < totalquestions) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452008",
						"sql11452008", "lsngacLCVA");
				stmt = con.createStatement();
				rs = stmt.executeQuery(
						"SELECT question_id, question, option1, option2, option3, option4, answer FROM QUIZ order by rand() limit 1");
				while(rs.next()){
					qID = rs.getInt(1);
					question = rs.getString(2);
					option1 = rs.getString(3);
					option2 = rs.getString(4);
					option3 = rs.getString(5);
					option4 = rs.getString(6);
					answer = rs.getInt(7);
			        
			    }
				questiontext.setText(String.valueOf(question));
				ans_1.setText(String.valueOf(option1));
				ans_2.setText(String.valueOf(option2));
				ans_3.setText(String.valueOf(option3));
				ans_4.setText(String.valueOf(option4));
	
					
			}
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			timer.start();
					
		} else {
	
			finalScore();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		button_1.setEnabled(false);
		button_2.setEnabled(false);
		button_3.setEnabled(false);
		button_4.setEnabled(false);
		
		if (e.getSource() == button_1) playerssays = 1;
		else if (e.getSource() == button_2) playerssays = 2;
		else if (e.getSource() == button_3) playerssays = 3;
		else if (e.getSource() == button_4) playerssays = 4;
		
		if (playerssays == answer) {
			score2 = score2 + 100;
			score.setText(String.valueOf(score2));
			System.out.println(score2);
		} else {
			falseAnswers++;
		}
		System.out.println();
		PlayerAnswer();
	}
	
	public void PlayerAnswer() {
	
		timer.stop();
	
		Timer pause = new Timer(60, new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
	
				playerssays = 0;
				secs = 60;
				time_left.setText(String.valueOf(secs));
				button_1.setEnabled(true);
				button_2.setEnabled(true);
				button_3.setEnabled(true);
				button_4.setEnabled(true);
				askedquestions++;
				try {
					nextQuestion();
				} catch (SQLException e1) {
	
					e1.printStackTrace();
				}
			}
		});
		pause.setRepeats(false);
		pause.start();
	}
	
	public void finalScore() throws SQLException {
		
	button_1.setEnabled(false);
	button_2.setEnabled(false);
	button_3.setEnabled(false);
	button_4.setEnabled(false);
	
	if (falseAnswers == 3) {
		finalresult.setText("You lose!");
	} else {
		
		//EDW THELEI ULOPOIHSH TOU TI THA APOTHIKEUEI GIA TO HIGH SCORE
		finalresult.setText("You win! \n" + String.valueOf(score2));
		//ps = con.prepareStatement("INSERT INTO Score(ScoreGame2) VALUES(?)");
		//ps.setInt(1, score2);
		//ps.executeUpdate(); 
	}
	
	quizframe.add(finalresult);
	con.close();
	
	// Wait 1sec and close the window
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	quizframe.dispose();
	
	ModuleData.setQuizCorrectAnswers(score2/100);
	
	}

}



