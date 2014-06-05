package se.kth.ict.docaid.api.examples;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.documents.UtilClass;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.modules.KeywordWrp;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;

/**
 * Example on how to invoke the keywordWrp module
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class ExtractKeywordsExample {

	private static void extractKeywordsFromURL() {
		String url = "http://www.kth.se/student/kurser/kurs/IK1552?l=en";

		WebDocument doc = null;
		try {
			doc = new WebDocument(url);
		} catch (PageContentExtractorException e1) {
			System.out.println("Invalid URL");
			e1.printStackTrace();
			return;
		}
		int minFrequency = 4;
		int minWordLength = 2;
		boolean filterNonFrequent = true, filterNumbers = true, filterStopwords = true;

		KeywordWrp wrp = new KeywordWrp(doc.getBody(), minFrequency, minWordLength, filterNonFrequent, filterNumbers, filterStopwords);
		DatabaseConnection connection = new DatabaseConnection();
		try {
			ArrayList<Keyword> keywords = wrp.generateKeywords(connection.getConnection());
			for (Keyword k : keywords)
				System.out.println(k.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			connection.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void extractKeywordsFromDocument() {
		LinkedList<File> files = UtilClass.getTestFiles();
		for (File f : files) {
			System.out.println("====================================================");
			System.out.println("TESTING DOCUMENT " + f.getName());
			if (f.getName().equalsIgnoreCase("testn3.pdf")) {
				InputDocument doc = null;
				try {
					doc = UtilClass.getInstance().getInputDocument(f);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				int minFrequency = 4;
				int minWordLength = 2;
				boolean filterNonFrequent = true, filterNumbers = true, filterStopwords = true;
				
				KeywordWrp wrp = new KeywordWrp(doc.getBody(), minFrequency, minWordLength, filterNonFrequent, filterNumbers, filterStopwords);
				DatabaseConnection connection = new DatabaseConnection();
				try {
					ArrayList<Keyword> keywords = wrp.generateKeywords(connection.getConnection());
					for (Keyword k : keywords)
						System.out.println(k.toString());
				} catch (IOException e) {
					e.printStackTrace();
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
//		extractKeywordsFromURL();
		extractKeywordsFromDocument();
	}
}
