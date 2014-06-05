package se.kth.ict.docaid.api.examples;

import java.io.File;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.documents.UtilClass;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.modules.AcronymWrp;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;

/**
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class ExtractAcronymsExample {
	private static void extractFromURL() {
		WebDocument doc = null;
		try {
			doc = new WebDocument("http://www.kth.se/student/kurser/kurs/IK1552?l=en");
		} catch (PageContentExtractorException e1) {
			e1.printStackTrace();
		}
		AcronymWrp wrp = new AcronymWrp(doc.getBody());
		LinkedList<Acronym> acronyms;
		acronyms = wrp.generateAcronyms();
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

				AcronymWrp wrp = new AcronymWrp(doc.getBody());
				LinkedList<Acronym> acronyms;
				acronyms = wrp.generateAcronyms();
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
