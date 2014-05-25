package se.kth.ict.docaid.reader;

import aid.project.recovery.InputDocument;

public class DocumentReader extends Reader{
	@SuppressWarnings("unused")
	private InputDocument inputDocument;
	
	public DocumentReader(InputDocument inputDocument) {
		super(inputDocument.getBody());
		this.inputDocument = inputDocument;
	}
}
