package se.kth.ict.docaid.modules;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.algorithms.keywords.KeywordExtractor;
import se.kth.ict.docaid.filters.KeywordFilterer;

/**
 * Extracts keywords from a text source
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class KeywordWrp {
	private String content;
	private int minFrequency, minWordLength;
	private boolean filterNonFrequent, filterNumbers, filterStopwords;

	/**
	 * @param content The text source
	 * @param minFrequency The minimum frequency of occurrence of a keyword to be used in KeywordFilterer
	 * @param minWordLength The minimum length in characters of a word to be considered as a keyword.
	 * @param filterNonFrequent If true non frequent filter is called.
	 * @param filterNumbers If true words that contain numbers are filtered.
	 * @param filterStopwords If true stopwords are filtered.
	 */
	public KeywordWrp(String content, int minFrequency, int minWordLength, boolean filterNonFrequent, boolean filterNumbers, boolean filterStopwords) {
		this.content = content;
		this.minFrequency = minFrequency;
		this.minWordLength = minWordLength;
		this.filterNonFrequent = filterNonFrequent;
		this.filterNumbers = filterNumbers;
		this.filterStopwords = filterStopwords;
	}

	/**
	 * @param connection The database connection
	 * @return The generated list of Keywords
	 * @throws IOException
	 */
	public ArrayList<Keyword> generateKeywords(Connection connection) throws IOException {
		ArrayList<Keyword> keywords = KeywordExtractor.guessFromString(content);
		if(filterNonFrequent)
			KeywordFilterer.filterNonFrequent(keywords, minFrequency, minWordLength);
		if(filterNumbers)
			KeywordFilterer.filterWordsThatContainNumbers(keywords);
		if(filterStopwords)
			KeywordFilterer.filterStopWordsDB(keywords, connection);
		return keywords;
	}

}
