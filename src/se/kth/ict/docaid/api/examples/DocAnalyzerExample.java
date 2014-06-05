package se.kth.ict.docaid.api.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.documents.UtilClass;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.modules.AcronymWrp;
import se.kth.ict.docaid.modules.KeyphraseWrp;
import se.kth.ict.docaid.modules.KeywordWrp;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;

/**
 * Summarizes the functionality of Keyword Keyphrase Acronym extraction
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class DocAnalyzerExample {
	private static void extractFromURL() {
		WebDocument doc = null;
		try {
			doc = new WebDocument("http://www.kth.se/student/kurser/kurs/IK1552?l=en");
		} catch (PageContentExtractorException e1) {
			e1.printStackTrace();
		}
		int minFrequency = 4;
		int minWordLength = 2;
		boolean filterNonFrequent = true, filterNumbers = true, filterStopwords = true;

		KeywordWrp wrp = new KeywordWrp(doc.getBody(), minFrequency, minWordLength, filterNonFrequent, filterNumbers, filterStopwords);
		DatabaseConnection connection = new DatabaseConnection();
		ArrayList<Keyword> keywords;
		try {
			keywords = wrp.generateKeywords(connection.getConnection());
			for (Keyword k : keywords)
				System.out.println(k.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		KeyphraseWrp keyphraseWrp = new KeyphraseWrp(doc.getBody(), false);
		ArrayList<Keyphrase> keyphrases;
		try {
			keyphrases = keyphraseWrp.generateKeyphrases(connection.getConnection());
			for (Keyphrase keyphrase : keyphrases) {
				System.out.println(keyphrase.getFactor() + "\t" + keyphrase.getPhrase());
			}
		} catch (InvalidTextLengthException e) {
			e.printStackTrace();
		}
		
		AcronymWrp acronymWrp = new AcronymWrp(doc.getBody());
		LinkedList<Acronym> acronyms;
		acronyms = acronymWrp.generateAcronyms();
		for (Acronym a : acronyms) {
			System.out.println(a.toString());
		}
	}

	private static void extractFromDoc() {
		LinkedList<File> files = UtilClass.getAcronymTestFiles();
		for (File f : files) {
			System.out.println("====================================================");
			System.out.println("TESTING DOCUMENT " + f.getName());
			if (f.getName().equalsIgnoreCase("test3.pdf")) {
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
				ArrayList<Keyword> keywords;
				try {
					keywords = wrp.generateKeywords(connection.getConnection());
					for (Keyword k : keywords)
						System.out.println(k.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}

				KeyphraseWrp keyphraseWrp = new KeyphraseWrp(doc.getBody(), false);
				ArrayList<Keyphrase> keyphrases;
				try {
					keyphrases = keyphraseWrp.generateKeyphrases(connection.getConnection());
					for (Keyphrase keyphrase : keyphrases) {
						System.out.println(keyphrase.getFactor() + "\t" + keyphrase.getPhrase());
					}
				} catch (InvalidTextLengthException e) {
					e.printStackTrace();
				}
				
				AcronymWrp acronymWrp = new AcronymWrp(doc.getBody());
				LinkedList<Acronym> acronyms;
				acronyms = acronymWrp.generateAcronyms();
				for (Acronym a : acronyms) {
					System.out.println(a.toString());
				}
			}
		}
	}

	public static void main(String[] args) {
		// extractFromURL();
		extractFromDoc();
	}
}
