package se.kth.ict.docaid.filters;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;

import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.stopwords.StopwordDictionairy;
import se.kth.ict.docaid.stopwords.FetchStopwords;

/**
 * 
 * Applies a set of filtered on the extracted list of keywords.
 * 
 * @author andrew
 * 
 */
public class KeywordFilterer {
	private static final Integer MIN_FREQUENCY = 1;
	private static final int MIN_WORD_LENGTH = 2;

	/**
	 * Filters keywords that are stopwords. Checks every stem and all terms of a keyword if they are stopwords.
	 * 
	 * @param keywords The keywords to be filtered
	 * @param connection The database connection to retrieve stopwords.
	 */
	public static void filterKeywordsDB(ArrayList<Keyword> keywords, Connection connection) {
		filterNonFrequent(keywords, MIN_FREQUENCY, MIN_WORD_LENGTH);
		filterWordsThatContainNumbers(keywords);
		filterStopWordsDB(keywords, connection);
	}

	/**
	 * Filters keywords that are stopwords. Checks every stem and all terms of a keyword if they are stopwords.
	 * 
	 * 
	 * @param keywords The keywords to be filtered.
	 * @param stopwords The list of stopwords to be used as filter.
	 */
	public static void filterKeywords(ArrayList<Keyword> keywords, StopwordDictionairy stopwords) {
		filterNonFrequent(keywords, MIN_FREQUENCY, MIN_WORD_LENGTH);
		filterWordsThatContainNumbers(keywords);
		filterStopWords(keywords, stopwords.getStopwordsSv(), stopwords.getStopwordsEn(), stopwords.getStopwordsCourse());
	}

	/**
	 * Identifies words such as lab1, lab1test etc and filters them out.
	 * 
	 * @param keywords The keywords to be filtered out
	 */
	public static void filterWordsThatContainNumbers(ArrayList<Keyword> keywords) {
		final String regex = "((\\w)*(\\d)+)((\\w)*(\\d)*)*"; // should contain at least one number
		ArrayList<Keyword> toRemove = new ArrayList<Keyword>();
		for (Keyword keyword : keywords)
			if (keyword.getStem().matches(regex))
				toRemove.add(keyword);
		for (Keyword keyword : toRemove)
			keywords.remove(keyword);
	}

	/**
	 * Removes words that are not frequent. MIN_FREQUENCY is a static variable that defines the lowest acceptable occurrence rate. Also removes words that have a minimum
	 * length of MIN_LENGTH.
	 * 
	 * @param keywords The keywords to be filtered out
	 */
	public static void filterNonFrequent(ArrayList<Keyword> keywords, int minFrequency, int minWordLength) {
		ArrayList<Keyword> toRemove = new ArrayList<Keyword>();
		for (Keyword keyword : keywords)
			if (keyword.getFrequency() <= minFrequency || keyword.getStem().length() < minWordLength)
				toRemove.add(keyword);
		for (Keyword keyword : toRemove)
			keywords.remove(keyword);
	}

	/**
	 * Iterated through all words and filters out the ones that match with the content of the method's parameters.
	 * 
	 * @param keywords The list of words extracted by KeywordsExtractor
	 * @param stopwordsEn The list of English stopwords to be filtered out.
	 * @param stopwordsSv The list of Swedish stopwords to be filtered out.
	 * @param stopwordsCourse The list of words relevant to courses and studying to be filtered out.
	 */
	private static void filterStopWords(ArrayList<Keyword> keywords, HashSet<String> stopwordsEn, HashSet<String> stopwordsSv, HashSet<String> stopwordsCourse) {
		ArrayList<Keyword> toRemove = new ArrayList<Keyword>();
		for (Keyword keyword : keywords) {
			if (stopwordsEn.contains(keyword.getStem()) || stopwordsSv.contains(keyword.getStem()) || stopwordsCourse.contains(keyword.getStem()))
				toRemove.add(keyword);
			else {
				for (String word : keyword.getTerms())
					if (stopwordsEn.contains(word) || stopwordsSv.contains(word) || stopwordsCourse.contains(word))
						toRemove.add(keyword);
			}
		}
		for (Keyword keyword : toRemove)
			keywords.remove(keyword);
	}

	/**
	 * Iterated through all words and filters out the ones that match with the content of the method's parameters.
	 * 
	 * @param keywords The list of words extracted by KeywordsExtractor
	 * @param stopwordsEn The list of English stopwords to be filtered out.
	 * @param stopwordsSv The list of Swedish stopwords to be filtered out.
	 * @param stopwordsCourse The list of words relevant to courses and studying to be filtered out.
	 */
	public static void filterStopWordsDB(ArrayList<Keyword> keywords, Connection connection) {
		ArrayList<Keyword> toRemove = new ArrayList<Keyword>();
		for (Keyword keyword : keywords) {
			if (FetchStopwords.StopwordEnExists(keyword.getStem(), connection) || FetchStopwords.StopwordSvExists(keyword.getStem(), connection) || FetchStopwords.StopwordCourseExists(keyword.getStem(), connection))
				toRemove.add(keyword);
			else {
				for (String word : keyword.getTerms())
					if (FetchStopwords.StopwordEnExists(word, connection) || FetchStopwords.StopwordSvExists(word, connection) || FetchStopwords.StopwordCourseExists(word, connection))
						toRemove.add(keyword);
			}
		}
		for (Keyword keyword : toRemove)
			keywords.remove(keyword);
	}
}
