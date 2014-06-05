package se.kth.ict.docaid.reader;

import se.kth.ict.docaid.documents.WebDocument;

/**
 * WebReader takes a text input, and extracts keywords, keyphrases and acronyms.
 * <p>Parent class of the Document and Web reader</p>
 * <p>It is used only when extracting meta-data for courses to populate the database.</p>
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class WebReader extends Reader {
	@SuppressWarnings("unused")
	private WebDocument webDocument;
	
	public WebReader(WebDocument webDocument) {
		super(webDocument.getBody());
		this.webDocument = webDocument;
	}
}
