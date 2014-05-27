package se.kth.ict.docaid.parser;

import se.kth.ict.docaid.course.Course;
import se.kth.ict.docaid.filters.KeyphraseFilterer;
import se.kth.ict.docaid.filters.KeywordFilterer;
import se.kth.ict.docaid.filters.StopwordDictionairy;
import se.kth.ict.docaid.reader.WebReader;
import aid.project.recovery.WebDocument;

/**
 * Parses a course home page to retrieve data such as keywords, keyphrases and acronyms.
 * 
 * @author andrew
 *
 */
public class CourseHomePageParser {
	private static String uri = "http://www.kth.se/student/kurser/kurs/";
	private static String uriLang = "?l=en";
	
	/**
	 * Reads the course home page, extracts the content and then identifies keywords, keyphrases and acronyms.
	 * 
	 * @param course The course to be updated with data retrieved for its' homepage. 
	 * @param stopwords The list of stopwords used to filter out keywords and keyphrases.
	 */
	public static void updateCourseInfo(Course course, StopwordDictionairy stopwords) {
		String url = constructURL(course.getCode());
		
		WebDocument doc = new WebDocument(url);
		course.setReader(new WebReader(doc));
		KeywordFilterer.filterKeywords(course.getReader().getKeywords(), stopwords);
		KeyphraseFilterer.filterKeyphrases(course.getReader().getKeyphrases(), stopwords);
	}
	
	/**
	 * Constructs the url of the course's home page. It is based on the KTH API.
	 * 
	 * @param code The course code
	 * @return The url of the course home page.
	 */
	private static String constructURL(String code) {
		final String url = uri + code + uriLang;
		return url;
	}
}
