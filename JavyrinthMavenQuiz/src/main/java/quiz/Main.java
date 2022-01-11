package quiz;

import java.sql.SQLException;

public class Main {
		public static void main(String[] args) {
			try {
				new Quiz();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
