package se.kth.ict.docaid.Recommender;


import java.util.ArrayList;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.algorithms.stringcomparison.StringCompare;
import se.kth.ict.docaid.course.Course;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.filters.StopwordDictionairy;
import se.kth.ict.docaid.reader.DocumentReader;
import se.kth.ict.docaid.reader.WebReader;

public class Recommender {

	public static float ThreshString = 0.7f;
	public static float ThreshKeyphraseStem = 0.6f;

	/**
	 * Get weight based on acronyms
	 * 
	 * @param requestRec
	 *            - list of acronyms that need a recommendation
	 * @param candidateRec
	 *            - list of acronyms that are a candidate for recommendation
	 * @return a value [0..1], where 1 represents a perfect match
	 */
	public static float getAcronymWeight(LinkedList<Acronym> requestRec,
			LinkedList<Acronym> candidateRec) {
		int requestSize = requestRec.size();

		int numberOfMatchedAcronyms = 0;

		for (Acronym reqAcc : requestRec) {
			for (Acronym canAcc : candidateRec) {
				if (reqAcc.getAcronym().equalsIgnoreCase(canAcc.getAcronym())) {
					if (reqAcc.isSpelledOut()) {
						if (StringCompare.getComparisonRatcliff(
								reqAcc.spelloutString(),
								canAcc.spelloutString()) > ThreshString) {
							System.out.println(StringCompare
									.getComparisonLevenshtein(
											reqAcc.spelloutString(),
											canAcc.spelloutString()));
							// increment if acronyms represent the same word
							numberOfMatchedAcronyms++;
						}
					} else
						// increment if acronyms are not spelled
						numberOfMatchedAcronyms++;
				}
			}
		}

		// System.out.println(numberOfMatchedAcronyms);
		// System.out.println(requestSize);

		return (float) numberOfMatchedAcronyms / requestSize;
	}

	/**
	 * Get weight based on keywords
	 * 
	 * @param requestKeywords
	 *            - list of keywords that need a recommendation
	 * @param candidateKeywords
	 *            - list of keywords that are a candidate for recommendation
	 * @return a value [0..1], where 1 represents a perfect match
	 */
	public static float getKeywordWeight(ArrayList<Keyword> requestKeywords,
			ArrayList<Keyword> candidateKeywords) {
		int requestSize = requestKeywords.size();

		int numberOfMatchedKeywords = 0;

		for (Keyword reqKey : requestKeywords) {
			for (Keyword canKey : candidateKeywords) {

				if (StringCompare.getComparisonRatcliff(reqKey.getStem(),
						canKey.getStem()) > ThreshString) {
					/*
					 * System.out.println(reqKey + " " + canKey);
					 * System.out.println
					 * (StringCompare.getComparisonLevenshtein(
					 * reqKey.getStem(), canKey.getStem()));
					 * System.out.println(StringCompare.getComparisonRatcliff(
					 * reqKey.getStem(), canKey.getStem()));
					 */
					numberOfMatchedKeywords++;
				}
			}
		}

		// System.out.println(numberOfMatchedKeywords);
		// System.out.println(requestSize);

		return (float) numberOfMatchedKeywords / requestSize;
	}

	/**
	 * Get weight based on keyphrases
	 * 
	 * @param requestKeyphrases
	 *            - list of keyphrases that need a recommendation
	 * @param candidateKeyphrases
	 *            - list of keyphrases that are a candidate for recommendation
	 * @return a value [0..1], where 1 represents a perfect match
	 */
	public static float getKeyphraseWeight(
			ArrayList<Keyphrase> requestKeyphrases,
			ArrayList<Keyphrase> candidateKeyphrases) {
		int requestSize = requestKeyphrases.size();
		int numberOfMatchedKeyphrases = 0;

		for (Keyphrase reqKey : requestKeyphrases) {
			for (Keyphrase canKey : candidateKeyphrases) {
				int requestStems = reqKey.getStems().size();

				int matchedStems = 0;

				for (String reqStem : reqKey.getStems())
					for (String canStem : canKey.getStems()) {
						if (StringCompare.getComparisonRatcliff(reqStem,
								canStem) > ThreshString) {
							/*
							 * System.out.println("MATCHED STEM " + reqStem +
							 * " to " + canStem);
							 */
							matchedStems++;
						}
					}
				// System.out.println("======================================");
				if ((float) matchedStems / requestStems > ThreshKeyphraseStem) {
					/*
					 * System.out.println(canKey.toString() + " " +
					 * reqKey.toString());
					 */
					numberOfMatchedKeyphrases++;
				}
			}
		}

		// System.out.println(numberOfMatchedKeyphrases);
		// System.out.println(requestSize);

		return (float) numberOfMatchedKeyphrases / requestSize;
	}

	/**
	 * 
	 * @param reqAcc
	 *            - list of acronyms that need a recommendation
	 * @param canAcc
	 *            - list of acronyms that are a candidate for recommendation
	 * @param reqKeyword
	 *            - list of keywords that need a recommendation
	 * @param canKeyword
	 *            - list of keywords that are a candidate for recommendation
	 * @param reqKeyphrases
	 *            - list of keyphrases that need a recommendation
	 * @param canKeyphrase
	 *            - list of keyphrases that are a candidate for recommendation
	 * @return a value [0..3], where 3 represents a perfect match
	 */
	public static float getWeight(LinkedList<Acronym> reqAcc,
			LinkedList<Acronym> canAcc, ArrayList<Keyword> reqKeyword,
			ArrayList<Keyword> canKeyword, ArrayList<Keyphrase> reqKeyphrases,
			ArrayList<Keyphrase> canKeyphrase) {

		return getAcronymWeight(reqAcc, canAcc)
				+ getKeywordWeight(reqKeyword, reqKeyword)
				+ getKeyphraseWeight(reqKeyphrases, canKeyphrase);

	}

	/**
	 * Get weight based on documents
	 * 
	 * @param reqDoc
	 *            - the document that needs a recommendation
	 * @param canDoc
	 *            - the document that is a candidate for recommendation
	 * @return a value [0..3], where 3 represents a perfect match
	 */
	public static float getWeight(InputDocument reqDoc, InputDocument canDoc) {
		StopwordDictionairy stopwords = new StopwordDictionairy();
		DocumentReader reqReader = new DocumentReader(reqDoc, stopwords);
		DocumentReader canReader = new DocumentReader(canDoc, stopwords);

		return getWeight(reqReader.getAcronyms(), canReader.getAcronyms(),
				reqReader.getKeywords(), canReader.getKeywords(),
				reqReader.getKeyphrases(), canReader.getKeyphrases());
	}

	/**
	 * Get weight based on web documents
	 * 
	 * @param reqDoc
	 *            - the document that needs a recommendation
	 * @param canDoc
	 *            - the document that is a candidate for recommendation
	 * @return a value [0..3], where 3 represents a perfect match
	 */
	public static float getWeight(WebDocument reqDoc, WebDocument canDoc) {
		WebReader reqReader = new WebReader(reqDoc);
		WebReader canReader = new WebReader(canDoc);

		return getWeight(reqReader.getAcronyms(), canReader.getAcronyms(),
				reqReader.getKeywords(), canReader.getKeywords(),
				reqReader.getKeyphrases(), canReader.getKeyphrases());
	}

	/**
	 * Get weight based on web document and a course
	 * 
	 * @param reqDoc
	 *            - the document that needs a recommendation
	 * @param canDoc
	 *            - the course that is a candidate for recommendation
	 * @return a value [0..3], where 3 represents a perfect match
	 */
	public static float getWeight(WebDocument reqDoc, Course canDoc) {
		WebReader reqReader = new WebReader(reqDoc);
		WebReader canReader = canDoc.getReader();
		return getWeight(reqReader.getAcronyms(), canReader.getAcronyms(),
				reqReader.getKeywords(), canReader.getKeywords(),
				reqReader.getKeyphrases(), canReader.getKeyphrases());
	}

	/**
	 * Get weight based on web document and a course
	 * 
	 * @param reqDoc
	 *            - the document that needs a recommendation
	 * @param canDoc
	 *            - the course that is a candidate for recommendation
	 * @return a value [0..3], where 3 represents a perfect match
	 */
	public static float getWeight(InputDocument reqDoc, Course canDoc) {
		StopwordDictionairy stopwords = new StopwordDictionairy();
		DocumentReader reqReader = new DocumentReader(reqDoc, stopwords);
		WebReader canReader = canDoc.getReader();
		return getWeight(reqReader.getAcronyms(), canReader.getAcronyms(),
				reqReader.getKeywords(), canReader.getKeywords(),
				reqReader.getKeyphrases(), canReader.getKeyphrases());
	}

}
