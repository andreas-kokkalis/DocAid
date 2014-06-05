package se.kth.ict.docaid.parser;

import me.champeau.ld.UberLanguageDetector;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.course.Course;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.filters.KeywordFilterer;
import se.kth.ict.docaid.reader.Reader;
import se.kth.ict.docaid.reader.WebReader;
import se.kth.ict.docaid.stopwords.StopwordDictionairy;
import ws.palladian.preprocessing.scraping.PageContentExtractorException;

/**
 * Parses a course home page to retrieve data such as keywords, keyphrases and acronyms.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
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
	 * @throws PageContentExtractorException
	 */
	public static void updateCourseInfo(Course course, StopwordDictionairy stopwords) throws PageContentExtractorException {
		String url = constructURL(course.getCode());

		WebDocument doc = new WebDocument(url);
		// Reader parses the document and extracts keywords, keyphrases, acronyms.
		course.setReader(new WebReader(doc));
		// Detect the document language.
		UberLanguageDetector detector = UberLanguageDetector.getInstance();
		String language = detector.detectLang(course.getReader().getContent());
		course.setLanguage(language);

		KeywordFilterer.filterKeywords(course.getReader().getKeywords(), stopwords);

		// Read the recruitment text if exists.
		if (course.getRecruitmentTextEn() != null && !course.getRecruitmentTextEn().equals("") && !course.getRecruitmentTextEn().isEmpty()) {
			Reader readerEn = new Reader(course.getRecruitmentTextEn(), true, false, true);
			KeywordFilterer.filterKeywords(readerEn.getKeywords(), stopwords);
			for (Keyword keyword : readerEn.getKeywords()) {
				if (!course.getReader().containsKeyword(keyword.getStem())) {
					course.getReader().getKeywords().add(keyword);
					System.out.println("Added\t" + keyword.getStem());
				}
			}
		}

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
