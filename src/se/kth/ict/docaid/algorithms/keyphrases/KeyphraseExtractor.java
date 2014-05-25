package se.kth.ict.docaid.algorithms.keyphrases;

import maui.main.MauiWrapper;
import weka.core.Instance;
import aid.project.recovery.WebDocument;

public class KeyphraseExtractor {

	public static void main(String[] args) {

		String url = "http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en";

		WebDocument doc = new WebDocument(url);
//		WebReader reader = new WebReader(doc);

		MauiWrapper wrap = new MauiWrapper("", "", "keyphrextr");
		try {
			Instance[] st = wrap.extractTopicsFromText(doc.getBody(), 10);
			for (Instance s : st)
				System.out.println("xx " + s.stringValue(1) + " " + s.value(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
