package se.kth.ict.docaid.modules;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;

public class DocAnalyzerWrp {
	private ArrayList<Keyword> keywords;
	private ArrayList<Keyphrase> keyphrases;
	private LinkedList<Acronym> acronyms;

	public DocAnalyzerWrp(String content, int minFrequency, int minWordLength, boolean filterNonFrequent, boolean filterNumbers, boolean filterStopwords, boolean filterStopPhrases, Connection connection) throws IOException, InvalidTextLengthException {
		KeywordWrp keywordWrp = new KeywordWrp(content, minFrequency, minWordLength, filterNonFrequent, filterNumbers, filterStopwords);
		keywords = keywordWrp.generateKeywords(connection);
		KeyphraseWrp keyphraseWrp = new KeyphraseWrp(content, filterStopPhrases);
		keyphrases = keyphraseWrp.generateKeyphrases(connection);
		AcronymWrp acronymWrp = new AcronymWrp(content);
		acronyms = acronymWrp.generateAcronyms();
	}

	public DocAnalyzerWrp(String content,  Connection connection) throws IOException, InvalidTextLengthException {
		int minFrequency = 1;
		int minWordLength = 2;
		KeywordWrp keywordWrp = new KeywordWrp(content, minFrequency, minWordLength, true, true, true);
		keywords = keywordWrp.generateKeywords(connection);
		KeyphraseWrp keyphraseWrp = new KeyphraseWrp(content, false);
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
