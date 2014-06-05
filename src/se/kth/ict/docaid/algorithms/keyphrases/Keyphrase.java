package se.kth.ict.docaid.algorithms.keyphrases;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class stores information about a key phrase.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class Keyphrase {
	private String phrase;
	private double factor;
	private ArrayList<String> stems;
	private ArrayList<String> phraseWords;

	public Keyphrase(String phrase, double factor, ArrayList<String> stems) {
		this.phrase = phrase;
		this.factor = factor;
		this.stems = stems;
		phraseWords = new ArrayList<String>();
		for(String string: Arrays.asList(phrase.split(" "))) {
			phraseWords.add(new String(string.toLowerCase()));
		}
	}

	public String getPhrase() {
		return phrase;
	}

	public double getFactor() {
		return factor;
	}

	public ArrayList<String> getStems() {
		return stems;
	}

	public ArrayList<String> getPhraseWords() {
		return phraseWords;
	}
	
	public String phraseWordsToString() {
		StringBuilder st = new StringBuilder();
		for (String string : getPhraseWords())
			st.append(string).append(" ");
		return st.toString();
	}
	
	public String phraseStemsToString() {
		StringBuilder st = new StringBuilder();
		for (String string : getStems())
			st.append(string).append(" ");
		return st.toString();
	}

	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		st.append(phrase).append("\t").append("xx").append("\t").append(factor).append("\t");
//		for (String string : phraseWords)
//			st.append(string).append(", ");
		return st.toString();
	}
}
