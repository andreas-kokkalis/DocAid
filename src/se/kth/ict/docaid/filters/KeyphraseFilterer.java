package se.kth.ict.docaid.filters;

import java.sql.Connection;
import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.stopwords.StopwordDictionairy;
import se.kth.ict.docaid.stopwords.FetchStopwords;

/**
 * Filters keyphrases that contain stopwords from a given list.
 * <p>It is completely useless.</p>
 * 
 * @author andrew
 *
 */
public class KeyphraseFilterer {

	/**
	 * Filters keyphrases
	 * 
	 * @param keyphrases The list of keyphrases to be filtered out.
	 * @param stopwords The stopwords used to filter out the keyphrases.
	 */
	public static void filterKeyphrases(ArrayList<Keyphrase> keyphrases, StopwordDictionairy stopwords) {
		filterStopWords(keyphrases, stopwords);
	}
	
	/**
	 * Filters out keyphrases that their stems or words contain stopwords.
	 * 
	 * @param keyphrases The list of keyphrases to be filtered out.
	 * @param stopwords The stopwords used to filter out the keyphrases.
	 */
	private static void filterStopWords(ArrayList<Keyphrase> keyphrases, StopwordDictionairy stopwords) {
		ArrayList<Keyphrase> toRemove = new ArrayList<Keyphrase>();

		for(Keyphrase keyphrase: keyphrases) {
			for(String stem: keyphrase.getStems()) {
				if(stopwords.getStopwordsEn().contains(stem) || stopwords.getStopwordsSv().contains(stem) || stopwords.getStopwordsCourse().contains(stem))
					toRemove.add(keyphrase);
			}
			if(!toRemove.contains(keyphrase)) {
				for(String phraseWord: keyphrase.getPhraseWords()) {
					if(stopwords.getStopwordsEn().contains(phraseWord) || stopwords.getStopwordsSv().contains(phraseWord) || stopwords.getStopwordsCourse().contains(phraseWord))
						toRemove.add(keyphrase);
				}
			}
		}
		for (Keyphrase keyphrase : toRemove)
			keyphrases.remove(keyphrase);
	}
	
	/**
	 * Filters out keyphrases that their stems or words contain stopwords.
	 * 
	 * @param keyphrases The list of keyphrases to be filtered out.
	 * @param connection The database connection.
	 */
	public static void filterStopWordsDB(ArrayList<Keyphrase> keyphrases, Connection connection) {
		ArrayList<Keyphrase> toRemove = new ArrayList<Keyphrase>();

		for(Keyphrase keyphrase: keyphrases) {
			for(String stem: keyphrase.getStems()) {
				if(FetchStopwords.StopwordEnExists(stem, connection) || FetchStopwords.StopwordSvExists(stem, connection) || FetchStopwords.StopwordCourseExists(stem, connection))
					toRemove.add(keyphrase);
			}
			if(!toRemove.contains(keyphrase)) {
				for(String phraseWord: keyphrase.getPhraseWords()) {
					if(FetchStopwords.StopwordEnExists(phraseWord, connection) || FetchStopwords.StopwordSvExists(phraseWord, connection) || FetchStopwords.StopwordCourseExists(phraseWord, connection))
						toRemove.add(keyphrase);
				}
			}
		}
		for (Keyphrase keyphrase : toRemove)
			keyphrases.remove(keyphrase);
	}
}
