package se.kth.ict.docaid.reader;

import se.kth.ict.docaid.filters.MyStopwords;
import aid.project.recovery.InputDocument;

public class DocumentReader extends Reader{
	@SuppressWarnings("unused")
	private InputDocument inputDocument;
	
	public DocumentReader(InputDocument inputDocument, MyStopwords stopwords) {
		super(inputDocument.getBody(), stopwords);
		this.inputDocument = inputDocument;
	}
}
