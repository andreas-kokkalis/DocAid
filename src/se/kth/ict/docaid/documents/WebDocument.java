package se.kth.ict.docaid.documents;

import ws.palladian.preprocessing.scraping.PageContentExtractorException;
import ws.palladian.preprocessing.scraping.ReadabilityContentExtractor;

/**
 * Document extracted from the content of a web page. It used the ReadabilityContentExtractor from palladian to parse the input.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class WebDocument {

	String title;
	String body;
	String url;

	/**
	 * Create a WebDocument from a website that contains a title and a body
	 * 
	 * @param url A String that represents the url of the website that stores the course directory, i.e.,
	 *            http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en
	 * @throws PageContentExtractorException if the url cannot be parsed.
	 */
	public WebDocument(final String url) throws PageContentExtractorException {
		this.url = url;
		generateContent(url);
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	private void setBody(String body) {
		this.body = body;
	}

	private void generateContent(String url) throws PageContentExtractorException {
		@SuppressWarnings("static-access")
		ReadabilityContentExtractor extractor = UtilClass.getInstance().getExtractor();
		extractor.setDocument(this.url);
		setTitle(extractor.getResultTitle());
		setBody(extractor.getResultText());
	}

	/**
	 * Returns a String representation of the WebDocument
	 */
	@Override
	public String toString() {
		return "WebDocument [url=" + url + ", title=" + title + ", body=" + body + "]";
	}

}
