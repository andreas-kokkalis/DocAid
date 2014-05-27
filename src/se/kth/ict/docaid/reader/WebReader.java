package se.kth.ict.docaid.reader;

import aid.project.recovery.WebDocument;

public class WebReader extends Reader {
	@SuppressWarnings("unused")
	private WebDocument webDocument;
	
	public WebReader(WebDocument webDocument) {
		super(webDocument.getBody());
		this.webDocument = webDocument;
	}
	
	
}
