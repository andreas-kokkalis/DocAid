package se.kth.ict.docaid.stopwords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves stopwords from the database.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class FetchStopwords {
	/**
	 * Checks if a word is an english stopword.
	 * 
	 * @param word The word to check.
	 * @param connection The database connection.
	 * @return true if is a stopword, else false.
	 */
	public static boolean StopwordEnExists(String word, Connection connection) {
		String query = "Select word from stopwords_en where word=?";
		try {
			PreparedStatement st = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, word);
			ResultSet results = st.executeQuery();
			results.last();
			if (results.getRow() == 0) {
				return false;
			}
			else
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks if a word is a swedish stopword.
	 * 
	 * @param word The word to check.
	 * @param connection The database connection.
	 * @return true if is a stopword, else false.
	 */
	public static boolean StopwordSvExists(String word, Connection connection) {
		String query = "Select word from stopwords_sv where word=?";
		try {
			PreparedStatement st = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, word);
			ResultSet results = st.executeQuery();
			results.last();
			if (results.getRow() == 0) {
				return false;
			}
			else
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checks if a word is a stopword relevant to a course or teaching.
	 * 
	 * @param word The word to check.
	 * @param connection The database connection.
	 * @return true if is a stopword, else false.
	 */
	public static boolean StopwordCourseExists(String word, Connection connection) {
		String query = "Select word from stopwords_course where word=?";
		try {
			PreparedStatement st = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, word);
			ResultSet results = st.executeQuery();
			results.last();
			if (results.getRow() == 0) {
				return false;
			}
			else
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
