package se.kth.ict.docaid.modules;

import java.sql.Connection;
import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keyphrases.KeyphraseExtractor;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.filters.KeyphraseFilterer;

public class KeyphraseWrp {
	private String content;
	private boolean filterStopWords;

	public KeyphraseWrp(String content, boolean filterStopWords) {
		this.content = content;
		this.filterStopWords = filterStopWords;
	}

	public String getDoc() {
		return content;
	}

	public void setDoc(String doc) {
		this.content = doc;
	}

	public ArrayList<Keyphrase> generateKeyphrases(Connection connection) throws InvalidTextLengthException {
		ArrayList<Keyphrase> keyphrases = KeyphraseExtractor.getKeyphrases(content);
		if(filterStopWords)
			KeyphraseFilterer.filterStopWordsDB(keyphrases, connection);
		return keyphrases;
	}
	
	public static void main(String[] args) {
		
	}
}
