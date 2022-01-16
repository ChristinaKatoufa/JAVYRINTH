package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * This method gets connection with the database
 * gets the questions for the quiz game stores them in an ArrayList<String[]>
 * and closes the connection
 * @throws SQLException
 */
public class DBConnections {

	public static ArrayList<String[]> getData(int totalQuestions) {

		ArrayList<String[]> dbData = new ArrayList<String[]>();
		
		Statement stmt;
		ResultSet rs;
		String question;
		String option1;
		String option2;
		String option3;
		String option4;
		String answer;

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452008", "sql11452008", "lsngacLCVA");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT question_id, question, option1, option2, option3, option4, answer FROM QUIZ order by rand() limit " + totalQuestions);
			while (rs.next()) {
				question = rs.getString(2);
				option1 = rs.getString(3);
				option2 = rs.getString(4);
				option3 = rs.getString(5);
				option4 = rs.getString(6);
				answer = rs.getString(7);

				dbData.add(new String[] { question, option1, option2, option3, option4, answer });
			}
			
			rs.close();
			stmt.close();
			con.close();
			
			// The following can print dbData as CSV
			/*
			for (String[] line : dbData) {
				String lineString = "";
				for (String str : line) {
					lineString += "\"" + str + "\",";
				}
				lineString = lineString.substring(0, lineString.length() - 1);
				System.out.println(lineString);
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dbData;
	}
}
