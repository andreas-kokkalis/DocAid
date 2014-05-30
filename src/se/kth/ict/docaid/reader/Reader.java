package se.kth.ict.docaid.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.acronyms.AcronymDetector;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keyphrases.KeyphraseExtractor;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.algorithms.keywords.KeywordExtractor;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;

/**
 * @author andrew
 * 
 *         Reader takes a text input, and extracts keywords, keyphrases and acronyms.
 */
public class Reader {
	private String content;
	private ArrayList<Keyword> keywords;
	private ArrayList<Keyphrase> keyphrases;
	private LinkedList<Acronym> acronyms;
	private ArrayList<String> wordList;
	private HashMap<String, String> translatedWordList;

	public Reader(String content) {
		this.content = content;
		try {
			setKeywords(KeywordExtractor.guessFromString(content));
			setKeyphrases(KeyphraseExtractor.getKeyphrases(content));
			setAcronyms(AcronymDetector.checkAcronymsOnSight(content));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTextLengthException e) {
			e.printStackTrace();
		}
		setWordList(new ArrayList<String>());
		setTranslatedWordList(new HashMap<String, String>());
	}

	public Reader(String content, boolean keyW, boolean keyP, boolean acronym) {
		this.content = content;
		try {
			if (keyW)
				setKeywords(KeywordExtractor.guessFromString(content));
			else
				setKeywords(new ArrayList<Keyword>());
			if (keyP)
				setKeyphrases(KeyphraseExtractor.getKeyphrases(content));
			else
				setKeyphrases(new ArrayList<Keyphrase>());
			if (acronym)
				setAcronyms(AcronymDetector.checkAcronymsOnSight(content));
			else
				setAcronyms(new LinkedList<Acronym>());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTextLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setWordList(new ArrayList<String>());
		setTranslatedWordList(new HashMap<String, String>());
	}

	public boolean containsKeyword(String stem) {
		for(Keyword keyword: getKeywords()) 
			if(keyword.getStem().equals(stem))
				return true;
		return false;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}

	public ArrayList<Keyphrase> getKeyphrases() {
		
		if (keyphrases==null) return new ArrayList<Keyphrase>();
		
		return keyphrases;
	}

	public void setKeyphrases(ArrayList<Keyphrase> keyphrases) {
		this.keyphrases = keyphrases;
	}

	public LinkedList<Acronym> getAcronyms() {
		return acronyms;
	}

	public void setAcronyms(LinkedList<Acronym> acronyms) {
		this.acronyms = acronyms;
	}

	public ArrayList<String> getWordList() {
		return wordList;
	}

	public void setWordList(ArrayList<String> wordList) {
		this.wordList = wordList;
	}

	public HashMap<String, String> getTranslatedWordList() {
		return translatedWordList;
	}

	public void setTranslatedWordList(HashMap<String, String> translatedWordList) {
		this.translatedWordList = translatedWordList;
	}

	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		st.append("Keywords: ").append("\n");
		for (Keyword keyword : getKeywords())
			st.append(keyword.toString()).append("\n");
		st.append("Keyphrases: ").append("\n");
		for (Keyphrase keyphrase : getKeyphrases())
			st.append(keyphrase.toString()).append("\n");
		st.append("Acronyms: ").append("\n");
		for (Acronym acronym : getAcronyms())
			st.append(acronym.getAcronym()).append("\n");
		st.append("-------------------------------------------------").append("\n\n");
		return st.toString();
	}
}
