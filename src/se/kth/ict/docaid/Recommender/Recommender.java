package se.kth.ict.docaid.Recommender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

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

		float acronym = 0;
		float keyword = 0;
		float keyphrase = 0;

		if (canAcc != null && reqAcc != null)
			acronym = getAcronymWeight(reqAcc, canAcc);
		if (reqKeyword != null && canKeyword != null)
			keyword = getKeywordWeight(reqKeyword, reqKeyword);
		if (reqKeyphrases != null && canKeyphrase != null)
			keyphrase = getKeyphraseWeight(reqKeyphrases, canKeyphrase);

		return acronym + keyword + keyphrase;

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
	public static float getWeight(WebReader reqReader, Course canDoc) {

		return getWeight(reqReader.getAcronyms(), canDoc.getAcronyms2(),
				reqReader.getKeywords(), canDoc.getKeywords(),
				reqReader.getKeyphrases(), canDoc.getKeyphrases());
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
	public static float getWeight(DocumentReader reqReader, Course canDoc) {

		return getWeight(reqReader.getAcronyms(), canDoc.getAcronyms2(),
				reqReader.getKeywords(), canDoc.getKeywords(),
				reqReader.getKeyphrases(), canDoc.getKeyphrases());
	}

	public static HashMap<String, Float> getCourseRecommendation(
			InputDocument inputDoc, HashMap<String, Course> courses) {

		HashMap<String, Float> selectedCourses = new HashMap<String, Float>();

		DocumentReader inputReader = new DocumentReader(inputDoc,
				new StopwordDictionairy());

		for (String s : courses.keySet()) {
			selectedCourses.put(s, getWeight(inputReader, courses.get(s)));
		}

		return sortHashMapByValuesD(selectedCourses);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static LinkedHashMap sortHashMapByValuesD(HashMap passedMap) {
		List mapKeys = new ArrayList(passedMap.keySet());
		List mapValues = new ArrayList(passedMap.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		Collections.reverse(mapValues);
		Collections.reverse(mapKeys);

		LinkedHashMap sortedMap = new LinkedHashMap();

		Iterator valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				String comp1 = passedMap.get(key).toString();
				String comp2 = val.toString();

				if (comp1.equals(comp2)) {
					passedMap.remove(key);
					mapKeys.remove(key);
					sortedMap.put((String) key, (Float) val);
					break;
				}

			}

		}
		return sortedMap;
	}

	public static HashMap<String, Float> getCourseRecommendation(
			WebDocument inputDoc, HashMap<String, Course> courses) {

		HashMap<String, Float> selectedCourses = new HashMap<String, Float>();
		WebReader reqReader = new WebReader(inputDoc);
		for (String s : courses.keySet()) {
			if (courses.get(s) != null)
				selectedCourses.put(s, getWeight(reqReader, courses.get(s)));
		}

		return sortHashMapByValuesD(selectedCourses);
	}

	public static HashMap<String, Float> getCourseRecommendation(
			LinkedList<Course> reqCourses, HashMap<String, Course> canCourses) {

		HashMap<String, Float> selectedCourses = new HashMap<String, Float>();

		for (Course c : reqCourses) {
			for (String s : canCourses.keySet()) {
				if (canCourses.get(s) != null)
					selectedCourses.put(s,
							getWeight(c, canCourses.get(s), reqCourses));
			}
		}

		return sortHashMapByValuesD(selectedCourses);
	}

	public static Float getWeight(Course c2, Course canCourse,
			LinkedList<Course> reqCourses) {

		// do not recommend the same courses
		for (Course c : reqCourses)
			if (c.getCode().equalsIgnoreCase(canCourse.getCode()))
				return 0f;
		return getWeight(c2.getAcronyms2(), canCourse.getAcronyms2(),
				c2.getKeywords(), canCourse.getKeywords(), c2.getKeyphrases(),
				canCourse.getKeyphrases());
	}
}
