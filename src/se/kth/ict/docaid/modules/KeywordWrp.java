package se.kth.ict.docaid.modules;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.algorithms.keywords.KeywordExtractor;
import se.kth.ict.docaid.filters.KeywordFilterer;

public class KeywordWrp {
	private String content;
	private int minFrequency, minWordLength;
	private boolean filterNonFrequent, filterNumbers, filterStopwords;

	public KeywordWrp(String content, int minFrequency, int minWordLength, boolean filterNonFrequent, boolean filterNumbers, boolean filterStopwords) {
		this.content = content;
		this.minFrequency = minFrequency;
		this.minWordLength = minWordLength;
		this.filterNonFrequent = filterNonFrequent;
		this.filterNumbers = filterNumbers;
		this.filterStopwords = filterStopwords;
	}

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
