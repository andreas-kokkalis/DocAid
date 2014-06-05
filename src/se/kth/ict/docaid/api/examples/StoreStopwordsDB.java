package se.kth.ict.docaid.api.examples;

import java.sql.SQLException;

import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.stopwords.StopwordDictionairy;
import se.kth.ict.docaid.stopwords.StoreStopwords;

/**
 * Example on how to load the stopwords from files and store the in the database.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class StoreStopwordsDB {
	public static void main(String[] args) {
		StopwordDictionairy stopwordDictionairy = new StopwordDictionairy();
		DatabaseConnection connection = new DatabaseConnection();
		StoreStopwords.storeStopwordsEn(stopwordDictionairy.getStopwordsEn(), connection.getConnection());
		StoreStopwords.storeStopwordsSv(stopwordDictionairy.getStopwordsSv(), connection.getConnection());
		StoreStopwords.storeStopwordsCourse(stopwordDictionairy.getStopwordsCourse(), connection.getConnection());
		try {
			connection.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
