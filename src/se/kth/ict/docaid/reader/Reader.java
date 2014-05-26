package se.kth.ict.docaid.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.AcronymDetector;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keyphrases.KeyphraseExtractor;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.algorithms.keywords.KeywordExtractor;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.filters.MyStopwords;

/**
 * @author andrew
 * 
 *         Reader takes a text input, and extracts keywords, keyphrases and acronyms.
 */
public class Reader {
	private String content;
	private ArrayList<Keyword> keywords;
	private ArrayList<Keyphrase> keyphrases;
	private LinkedList<String> acronyms;
	private ArrayList<String> wordList;
	private HashMap<String, String> translatedWordList;

	public Reader(String content, MyStopwords stopwords) {
		this.content = content;
		try {
			setKeywords(KeywordExtractor.guessFromString(content, stopwords));
			setKeyphrases(KeyphraseExtractor.getKeyphrases(content));
			setAcronyms(AcronymDetector.detectAcronyms(content));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTextLengthException e) {
			e.printStackTrace();
		}
		setWordList(new ArrayList<String>());
		setTranslatedWordList(new HashMap<String, String>());
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
		return keyphrases;
	}

	public void setKeyphrases(ArrayList<Keyphrase> keyphrases) {
		this.keyphrases = keyphrases;
	}

	public LinkedList<String> getAcronyms() {
		return acronyms;
	}

	public void setAcronyms(LinkedList<String> acronyms) {
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
}
