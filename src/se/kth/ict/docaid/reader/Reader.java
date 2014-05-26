package se.kth.ict.docaid.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.algorithms.keywords.KeywordExtractor;

public class Reader {
	private String content;
	private LinkedList<Keyword> keywords;
	private ArrayList<String> keyPhrases;
	private ArrayList<String> acronyms;
	private ArrayList<String> abbreviations;
	private ArrayList<String> wordList;
	private HashMap<String, String> translatedWordList;
	
	public Reader(String content) {
		this.content = content;
		
		try {
			setKeywords(KeywordExtractor.guessFromString(content));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setKeyPhrases(new ArrayList<String>());
		setAcronyms(new ArrayList<String>());
		setAbbreviations(new ArrayList<String>());
		setWordList(new ArrayList<String>());
		setTranslatedWordList(new HashMap<String, String>());
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LinkedList<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(LinkedList<Keyword> keywords) {
		this.keywords = keywords;
	}

	public ArrayList<String> getKeyPhrases() {
		return keyPhrases;
	}
	public void setKeyPhrases(ArrayList<String> keyPhrases) {
		this.keyPhrases = keyPhrases;
	}
	public ArrayList<String> getAcronyms() {
		return acronyms;
	}
	public void setAcronyms(ArrayList<String> acronyms) {
		this.acronyms = acronyms;
	}
	public ArrayList<String> getAbbreviations() {
		return abbreviations;
	}
	public void setAbbreviations(ArrayList<String> abbreviations) {
		this.abbreviations = abbreviations;
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
