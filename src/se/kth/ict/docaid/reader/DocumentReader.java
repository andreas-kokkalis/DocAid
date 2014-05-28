package se.kth.ict.docaid.reader;

import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.filters.StopwordDictionairy;

public class DocumentReader extends Reader{
	@SuppressWarnings("unused")
	private InputDocument inputDocument;
	
	public DocumentReader(InputDocument inputDocument, StopwordDictionairy stopwords) {
		super(inputDocument.getBody());
		this.inputDocument = inputDocument;
	}
}
