package se.kth.ict.docaid.stopwords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Stores stopwords in the database.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class StoreStopwords {
	/**
	 * Stores a list of english stopwords .
	 * 
	 * @param stopwords The list of stopwords to store.
	 * @param connection The database connection
	 */
	public static void storeStopwordsEn(HashSet<String> stopwords, Connection connection) {
		String query = "INSERT INTO stopwords_en(word) VALUES(?)";
		try {
			connection.setAutoCommit(false);
			PreparedStatement st = connection.prepareStatement(query);
			for (String stopword : stopwords) {
				System.out.println(stopword);
				st.setString(1, stopword);
				st.addBatch();
			}
			st.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores a list of swedish stopwords .
	 * 
	 * @param stopwords The list of stopwords to store.
	 * @param connection The database connection
	 */
	public static void storeStopwordsSv(HashSet<String> stopwords, Connection connection) {
		String query = "INSERT INTO stopwords_sv(word) VALUES(?)";
		try {
			connection.setAutoCommit(false);
			PreparedStatement st = connection.prepareStatement(query);
			for (String stopword : stopwords) {
				System.out.println(stopword);
				st.setString(1, stopword);
				st.addBatch();
			}
			st.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores stopwords relevant to a course and teaching.
	 * 
	 * @param stopwords The list of stopwords to store.
	 * @param connection The database connection.
	 */
	public static void storeStopwordsCourse(HashSet<String> stopwords, Connection connection) {
		String query = "INSERT INTO stopwords_course(word) VALUES(?)";
		try {
			connection.setAutoCommit(false);
			PreparedStatement st = connection.prepareStatement(query);
			for (String stopword : stopwords) {
				System.out.println(stopword);

				st.setString(1, stopword);
				st.addBatch();
			}
			st.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
