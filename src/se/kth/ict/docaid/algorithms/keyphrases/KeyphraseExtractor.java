package se.kth.ict.docaid.algorithms.keyphrases;

import java.util.ArrayList;

import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import maui.main.MauiWrapper;
import weka.core.Instance;

public class KeyphraseExtractor {
	public static ArrayList<Keyphrase> getKeyphrases(String content) throws InvalidTextLengthException{
		ArrayList<Keyphrase> phrases = new ArrayList<Keyphrase>();
		MauiWrapper wrap = new MauiWrapper("", "", "keyphrextr");
		Instance[] keyphrases;
		try {
			keyphrases = wrap.extractTopicsFromText(content, 10);
		} catch (Exception e) {
			throw new InvalidTextLengthException(e.getMessage());
		}
		for (Instance s : keyphrases)
			phrases.add(new Keyphrase(s.stringValue(1), s.value(4)));
		return phrases;
	}

	public static void main(String[] args) {
		// String url = "http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en";
		// try {
		// WebDocument webDocument = new WebDocument(url);
		// ArrayList<String> keyphrases = KeyphraseExtractor.getKeyphrases(webDocument.getBody());
		// for(String string: keyphrases)
		// System.out.println(string);
		// // KeyphraseExtractor ke = new KeyphraseExtractor(webDocument.getBody());
		// // Instance[] st = ke.getKeyPhraseInstances();
		// // for (Instance s : st)
		// // System.out.println("xx " + s.stringValue(1) + " " + s.value(4));
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

}
