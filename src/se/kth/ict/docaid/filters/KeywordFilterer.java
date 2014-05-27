package se.kth.ict.docaid.filters;

import java.util.ArrayList;
import java.util.HashSet;

import se.kth.ict.docaid.algorithms.keywords.Keyword;

/**
 * 
 * Applies a set of filtered on the extracted list of keywords.
 * 
 * @author andrew
 * 
 */
public class KeywordFilterer {
	private static final Integer MIN_FREQUENCY = 1;
	private static final int MIN_LENGTH = 2;

	public static void filterKeywords(ArrayList<Keyword> keywords, StopwordDictionairy stopwords) {
		filterStopWords(keywords, stopwords.getStopwordsSv(), stopwords.getStopwordsEn(), stopwords.getStopwordsCourse());
		filterNonFrequent(keywords);
		filterWordsThatContainNumbers(keywords);
	}

	/**
	 * Identifies words such as lab1, lab1test etc and filters them out.
	 * 
	 * @param keywords The keywords to be filtered out
	 */
	private static void filterWordsThatContainNumbers(ArrayList<Keyword> keywords) {
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
	private static void filterNonFrequent(ArrayList<Keyword> keywords) {
		ArrayList<Keyword> toRemove = new ArrayList<Keyword>();
		for (Keyword keyword : keywords)
			if (keyword.getFrequency() <= MIN_FREQUENCY || keyword.getStem().length() < MIN_LENGTH)
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
				for(String word: keyword.getTerms()) 
					if (stopwordsEn.contains(word) || stopwordsSv.contains(word) || stopwordsCourse.contains(word))
						toRemove.add(keyword);
			}
		}
		for (Keyword keyword : toRemove)
			keywords.remove(keyword);
	}
}
