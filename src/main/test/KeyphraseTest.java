package main.test;

import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.modules.KeyphraseWrp;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;

public class KeyphraseTest {
	public static void main(String[] args) {
		WebDocument doc = null;
		try {
			doc = new WebDocument("http://www.kth.se/student/kurser/kurs/IK1552?l=en");
		} catch (PageContentExtractorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		KeyphraseWrp wrp = new KeyphraseWrp(doc.getBody(), false);
		DatabaseConnection connection = new DatabaseConnection();
		ArrayList<Keyphrase> keyphrases;
		try {
			keyphrases = wrp.generateKeyphrases(connection.getConnection());
			for (Keyphrase keyphrase : keyphrases) {
				System.out.println(keyphrase.getPhrase());
			}
		} catch (InvalidTextLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
