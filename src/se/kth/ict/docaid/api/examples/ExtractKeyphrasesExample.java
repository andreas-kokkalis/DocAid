package se.kth.ict.docaid.api.examples;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.documents.UtilClass;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.modules.KeyphraseWrp;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;

/**
 * Example on how to use the KeyphraseWrp
 * Size of extracted keyphrase list is always 50.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class ExtractKeyphrasesExample {
	
	private static void extractFromURL() {
		WebDocument doc = null;
		try {
			doc = new WebDocument("http://www.kth.se/student/kurser/kurs/IK1552?l=en");
		} catch (PageContentExtractorException e1) {
			e1.printStackTrace();
		}
		KeyphraseWrp wrp = new KeyphraseWrp(doc.getBody(), false);
		DatabaseConnection connection = new DatabaseConnection();
		ArrayList<Keyphrase> keyphrases;
		try {
			keyphrases = wrp.generateKeyphrases(connection.getConnection());
			for (Keyphrase keyphrase : keyphrases) {
				System.out.println(keyphrase.getFactor() + "\t" + keyphrase.getPhrase());
			}
		} catch (InvalidTextLengthException e) {
			e.printStackTrace();
		}
	}
	
	private static void extractFromDoc() {
		LinkedList<File> files = UtilClass.getTestFiles();
		for (File f : files) {
			if (f.getName().equalsIgnoreCase("testn3.pdf")) {
				System.out.println("====================================================");
				System.out.println("TESTING DOCUMENT " + f.getName());
				InputDocument doc = null;
				try {
					doc = UtilClass.getInstance().getInputDocument(f);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//filter stopwords set to false. Better not change.
				KeyphraseWrp wrp = new KeyphraseWrp(doc.getBody(), false);
				DatabaseConnection connection = new DatabaseConnection();
				ArrayList<Keyphrase> keyphrases;
				try {
					keyphrases = wrp.generateKeyphrases(connection.getConnection());
					for (Keyphrase k : keyphrases)
						System.out.println(k.getFactor() + "\t" + k.getPhrase());
				} catch (InvalidTextLengthException e1) {
					e1.printStackTrace();
				}
				try {
					connection.getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
//		extractFromURL();
		extractFromDoc();
	}
}
