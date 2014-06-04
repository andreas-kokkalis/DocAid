package main.test;

import java.io.IOException;
import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.modules.KeywordWrp;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;

public class KeywordTest {
	public static void main(String[] args) {
		String url = "http://www.kth.se/student/kurser/kurs/IK1552?l=en";
//		String url = "/IK1552?l=en";

		WebDocument doc = null;
		try {
			doc = new WebDocument(url);
		} catch (PageContentExtractorException e1) {
			System.out.println("Invalid URL");
			return;
		}
		int minFrequency = 4;
		int minWordLength = 2;

		KeywordWrp wrp = new KeywordWrp(doc.getBody(), minFrequency, minWordLength, true, true, true);
		DatabaseConnection connection = new DatabaseConnection();
		try {
			ArrayList<Keyword> keywords = wrp.generateKeywords(connection.getConnection());
			for (Keyword k : keywords)
				System.out.println(k.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
