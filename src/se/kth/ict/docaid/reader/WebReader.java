package se.kth.ict.docaid.reader;

import se.kth.ict.docaid.documents.WebDocument;

public class WebReader extends Reader {
	@SuppressWarnings("unused")
	private WebDocument webDocument;
	
	public WebReader(WebDocument webDocument) {
		super(webDocument.getBody());
		this.webDocument = webDocument;
	}
}
