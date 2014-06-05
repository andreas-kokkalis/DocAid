package se.kth.ict.docaid.modules;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import se.kth.ict.docaid.Recommender.DatabaseRecommender;
import se.kth.ict.docaid.Recommender.RecommendedCourse;
import se.kth.ict.docaid.Recommender.RecommendedTutor;
import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.reader.RegistrationReader;

/**
 * Takes input documents and extracts meta data, a registration file and extracts course codes, a preference list and runs the recommender to get course and tutor
 * suggestions.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class CourseAdvisorWrp {
	private ArrayList<Keyword> keywords;
	private ArrayList<Keyphrase> keyphrases;
	private LinkedList<Acronym> acronyms;
	private HashMap<RecommendedCourse, Double> courseReccomedations;
	private HashMap<RecommendedTutor, Double> instructorRecommendations;

	/**
	 * @param docInput The list of files to extract meta data
	 * @param registrationDoc The file with course registrations
	 * @param preferencesInput The preference list to extract keywords
	 * @param minFrequency The minimum frequency of occurrence of a keyword to be used in KeywordFilterer
	 * @param minWordLength The minimum length in characters of a word to be considered as a keyword.
	 * @param filterNonFrequent If true non frequent filter is called.
	 * @param filterNumbers If true words that contain numbers are filtered.
	 * @param filterStopwords If true stopwords are filtered.
	 * @param filterStopPhrases Always give false
	 * @param connection The database connection
	 * @param limitCourse limit the course results
	 * @param limitTutor limit of tutor results
	 * @throws IOException
	 * @throws InvalidTextLengthException
	 */
	public CourseAdvisorWrp(ArrayList<String> docInput, InputDocument registrationDoc, String preferencesInput, int minFrequency, int minWordLength, boolean filterNonFrequent, boolean filterNumbers, boolean filterStopwords,
			boolean filterStopPhrases, Connection connection, int limitCourse, int limitTutor) throws IOException, InvalidTextLengthException {

		keywords = new ArrayList<Keyword>();
		keyphrases = new ArrayList<Keyphrase>();
		acronyms = new LinkedList<Acronym>();

		if (limitCourse < 0)
			limitCourse = 10;
		if (limitTutor < 0)
			limitTutor = 10;

		// Parse the preference list
		ArrayList<String> preferenceList = new ArrayList<String>(Arrays.asList(preferencesInput.split(",")));
		for (String phrase : preferenceList) {
			Keyphrase keyphrase = new Keyphrase(phrase, 0, new ArrayList<String>());
			keyphrases.add(keyphrase);
		}

		// Parse the registration document if exists
		LinkedList<String> courseCodes = new LinkedList<String>();
		if (registrationDoc != null) {
			RegistrationReader reader = new RegistrationReader(registrationDoc);
			courseCodes = reader.getCourseCodes();
		}

		// Parse the document input
		if (docInput.size() > 0) {
			for (String input : docInput) {
				DocAnalyzerWrp wrp = new DocAnalyzerWrp(input, minFrequency, minWordLength, filterNonFrequent, filterNumbers, filterStopwords, false, connection);
				this.keywords.addAll(wrp.getKeywords());
				this.keyphrases.addAll(wrp.getKeyphrases());
				this.acronyms.addAll(wrp.getAcronyms());
			}
		}
		courseReccomedations = DatabaseRecommender.getInstance().getCourseRecommendation(courseCodes, acronyms, keywords, keyphrases, limitCourse);
		instructorRecommendations = DatabaseRecommender.getInstance().getTutorRecommendation(courseCodes, acronyms, keywords, keyphrases, limitTutor);

	}

	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}

	public ArrayList<Keyphrase> getKeyphrases() {
		return keyphrases;
	}

	public void setKeyphrases(ArrayList<Keyphrase> keyphrases) {
		this.keyphrases = keyphrases;
	}

	public LinkedList<Acronym> getAcronyms() {
		return acronyms;
	}

	public void setAcronyms(LinkedList<Acronym> acronyms) {
		this.acronyms = acronyms;
	}

	public HashMap<RecommendedCourse, Double> getCourseReccomedations() {
		return courseReccomedations;
	}

	public void setCourseReccomedations(HashMap<RecommendedCourse, Double> courseReccomedations) {
		this.courseReccomedations = courseReccomedations;
	}

	public HashMap<RecommendedTutor, Double> getInstructorRecommendations() {
		return instructorRecommendations;
	}

	public void setInstructorRecommendations(HashMap<RecommendedTutor, Double> instructorRecommendations) {
		this.instructorRecommendations = instructorRecommendations;
	}
}
