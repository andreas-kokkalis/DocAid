package se.kth.ict.docaid.modules;

import java.sql.Connection;
import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keyphrases.KeyphraseExtractor;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.filters.KeyphraseFilterer;

/**
 * Extracts keyphrases from a text source
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class KeyphraseWrp {
	private String content;
	private boolean filterStopWords;

	/**
	 * Constructor
	 * 
	 * @param content The text source
	 * @param filterStopWords If true keyphrases that contain stopwords are filtered. Use with cautions. The filter may alter the results unexpectedly.
	 */
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

	/**
	 * @param connection The database connection
	 * @return The list of generated keyphrases.
	 * @throws InvalidTextLengthException
	 */
	public ArrayList<Keyphrase> generateKeyphrases(Connection connection) throws InvalidTextLengthException {
		ArrayList<Keyphrase> keyphrases = KeyphraseExtractor.getKeyphrases(content);
		if(filterStopWords)
			KeyphraseFilterer.filterStopWordsDB(keyphrases, connection);
		return keyphrases;
	}
	
	public static void main(String[] args) {
		
	}
}
