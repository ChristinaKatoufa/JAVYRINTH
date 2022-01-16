package wraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnections {

	/*
	 * An oristei koini gia oles tis methodoys kai arxikopoieitai apo ton costructor, 
	 * kai kapoia methodos tin kleisei con.close()
	 * tote an kalesoyme kapoia methodo poy xreiazetai tin connection pairnoume exception:
	 * java.sql.SQLNonTransientConnectionException: No operations allowed after connection closed.
	 * private Connection con;
	 */

	/**
	 * @param username
	 * @param score
	 * Creates a query to update the SCORES table of our BD
	 */
	public static void insertScore(String username, int score) {
		
		PreparedStatement ps;
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452008", "sql11452008", "lsngacLCVA");
			String sql = "INSERT INTO SCORES (username, score) VALUES ((SELECT signup.username FROM signup WHERE signup.username='"+username+"'), "+score+")";
			ps = con.prepareStatement(sql);
		    ps.executeUpdate();
		    
		    con.close();
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method returns the list of the top 10 scores and the recent game score performance 
	 * of the targetUsername (at the end of the list).
	 * @param targetUsername
	 * @return ArrayList<String[]> 
	 * 		( String[]: rank = String[0], username = String[1], time = String[2], score = String[3] )
	 */
	public static ArrayList<String[]> selectScores(String targetUsername) {
		
		ArrayList<String[]> dbData = new ArrayList<String[]>();
		
		Statement stmt;
		ResultSet rs;
		
		int rank;
		String username;
		String time;
		String score;
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452008", "sql11452008", "lsngacLCVA");
			stmt = con.createStatement();
			//* https://dba.stackexchange.com/questions/18316/sorting-the-table-and-getting-the-position
			rs = stmt.executeQuery("SELECT username, time, score, (SELECT count(*) FROM SCORES WHERE score > s.score) AS rank FROM SCORES s ORDER BY score DESC, time DESC LIMIT 10");
					
			while (rs.next()) {
				rank =  rs.getInt(4) + 1 ; // Ranking numbers begin from zero
				username = rs.getString(1);
				time = rs.getString(2);
				score = rs.getString(3);

				dbData.add(new String[] { String.valueOf(rank), username, time, score });
			}
			//* https://stackoverflow.com/questions/7745609/sql-select-only-rows-with-max-value-on-a-column
			String q = "SELECT ranked.* FROM "
					+ "(SELECT ranked1.* "
					+ "FROM (SELECT username, time, score, (SELECT count(*) FROM SCORES WHERE score > s.score) AS rank FROM SCORES s ORDER BY score DESC) AS ranked1 "
					+ "LEFT OUTER JOIN (SELECT username, time, score, (SELECT count(*) FROM SCORES WHERE score > s.score) AS rank FROM SCORES s ORDER BY score DESC) AS ranked2 "
					+ "ON ranked1.username = ranked2.username AND ranked1.time < ranked2.time "
					+ "WHERE ranked2.username IS NULL) AS ranked "
					+ "WHERE ranked.username=\""+targetUsername+"\"";
			
			rs = stmt.executeQuery(q);
			
			if (rs.next()) { // Be careful if targetUsername exists in the table Scores or else : java.sql.SQLException: Illegal operation on empty result set.
				rank =  rs.getInt(4) + 1; 
				username = rs.getString(1);
				time = rs.getString(2);
				score = rs.getString(3);
				dbData.add(new String[] { String.valueOf(rank), username, time, score });
			}
			
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dbData;
	}
}
