package se.kth.ict.docaid.reader;

import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.stopwords.StopwordDictionairy;

/**
 * Reads a document and extracts keywords, keyphrases and acronyms from the document's text source.
 * 
 * <p>It is used only when extracting meta data for courses to populate the database.</p>
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class DocumentReader extends Reader{
	@SuppressWarnings("unused")
	private InputDocument inputDocument;
	
	public DocumentReader(InputDocument inputDocument, StopwordDictionairy stopwords) {
		super(inputDocument.getBody());
		this.inputDocument = inputDocument;
	}
}
