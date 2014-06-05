package se.kth.ict.docaid.modules;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;

/**
 * Extracts keywords, keyphrases, acronyms for a text source.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class DocAnalyzerWrp {
	private ArrayList<Keyword> keywords;
	private ArrayList<Keyphrase> keyphrases;
	private LinkedList<Acronym> acronyms;

	/**
	 * Constructor. Calls the KeywordWrp, KeyphraseWrp and AcronymWrp and sets 
	 * 
	  * @param content The text source
	 * @param minFrequency The minimum frequency of occurrence of a keyword to be used in KeywordFilterer
	 * @param minWordLength The minimum length in characters of a word to be considered as a keyword.
	 * @param filterNonFrequent If true non frequent filter is called.
	 * @param filterNumbers If true words that contain numbers are filtered.
	 * @param filterStopwords If true stopwords are filtered.
	 * @param connection The database connection
	 * @throws IOException
	 * @throws InvalidTextLengthException
	 */
	public DocAnalyzerWrp(String content, int minFrequency, int minWordLength, boolean filterNonFrequent, boolean filterNumbers, boolean filterStopwords, boolean filterStopPhrases, Connection connection) throws IOException, InvalidTextLengthException {
		KeywordWrp keywordWrp = new KeywordWrp(content, minFrequency, minWordLength, filterNonFrequent, filterNumbers, filterStopwords);
		keywords = keywordWrp.generateKeywords(connection);
		KeyphraseWrp keyphraseWrp = new KeyphraseWrp(content, filterStopPhrases);
		keyphrases = keyphraseWrp.generateKeyphrases(connection);
		AcronymWrp acronymWrp = new AcronymWrp(content);
		acronyms = acronymWrp.generateAcronyms();
	}

	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}

	public ArrayList<Keyphrase> getKeyphrases() {
		return keyphrases;
	}

	public LinkedList<Acronym> getAcronyms() {
		return acronyms;
	}

}
