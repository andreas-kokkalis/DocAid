package se.kth.ict.docaid.reader;

import se.kth.ict.docaid.filters.MyStopwords;
import aid.project.recovery.WebDocument;

public class WebReader extends Reader {
	@SuppressWarnings("unused")
	private WebDocument webDocument;
	
	public WebReader(WebDocument webDocument, MyStopwords stopwords) {
		super(webDocument.getBody(), stopwords);
		this.webDocument = webDocument;
	}
	
	
}
