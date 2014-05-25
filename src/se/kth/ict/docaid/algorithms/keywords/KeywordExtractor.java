package se.kth.ict.docaid.algorithms.keywords;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.lucene.analysis.ASCIIFoldingFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

/**
 * Guesses keywords from an input string, based on the frequency of the words.
 * 
 * @see <a href="http://lucene.apache.org/">http://lucene.apache.org/</a>
 */
public class KeywordExtractor {

	/** Lucene version. */
	private static Version LUCENE_VERSION = Version.LUCENE_35;

	/**
	 * Stemmize the given term.
	 * 
	 * @param term The term to stem.
	 * @return The stem of the given term.
	 * @throws IOException If an I/O error occured.
	 */
	private static String stemmize(String term) throws IOException { 
		// tokenize term
		TokenStream tokenStream = new ClassicTokenizer(LUCENE_VERSION, new StringReader(term));
		// stemmize
		tokenStream = new PorterStemFilter(tokenStream);

		Set<String> stems = new HashSet<String>();
		CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
		// for each token
		while (tokenStream.incrementToken()) {
			// add it in the dedicated set (to keep unicity)
			stems.add(token.toString());
		}
		
		tokenStream.close();
		
		// if no stem or 2+ stems have been found, return null
		if (stems.size() != 1) {
			return null;
		}

		String stem = stems.iterator().next();

		// if the stem has non-alphanumerical chars, return null
		if (!stem.matches("[\\w-]+")) {
			return null;
		}
		
		return stem;
	}

	/**
	 * Tries to find the given example within the given collection. If it hasn't been found, the example is automatically added in the collection and is then returned.
	 * 
	 * @param collection The collection to search into.
	 * @param example The example to search.
	 * @return The existing element if it has been found, the given example otherwise.
	 */
	private static <T> T find(Collection<T> collection, T example) {
		for (T element : collection) {
			if (element.equals(example)) {
				return element;
			}
		}
		collection.add(example);
		return example;
	}

	/**
	 * Guesses keywords from given input string.
	 * 
	 * @param input The input string.
	 * @return A set of potential keywords. The first keyword is the most frequent one, the last the least frequent.
	 * @throws IOException If an I/O error occured.
	 */
	public static LinkedList<Keyword> guessFromString(String input) throws IOException {

		// hack to keep dashed words (e.g. "non-specific" rather than "non" and "specific")
		input = input.replaceAll("-+", "-0");
		// replace any punctuation char but dashes and apostrophes and by a space
		input = input.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
		// replace most common english contractions
		input = input.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");

		// tokenize input
		TokenStream tokenStream = new ClassicTokenizer(LUCENE_VERSION, new StringReader(input));
		// to lower case
		tokenStream = new LowerCaseFilter(LUCENE_VERSION, tokenStream);
		// remove dots from acronyms (and "'s" but already done manually above)
		tokenStream = new ClassicFilter(tokenStream);
		// convert any char to ASCII
		tokenStream = new ASCIIFoldingFilter(tokenStream);
		// remove english stop words
		tokenStream = new StopFilter(LUCENE_VERSION, tokenStream, EnglishAnalyzer.getDefaultStopSet());

		LinkedList<Keyword> keywords = new LinkedList<Keyword>();
		CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);

		// for each token
		while (tokenStream.incrementToken()) {
			String term = token.toString();
			// stemmize
			String stem = stemmize(term);
			if (stem != null) {
				// create the keyword or get the existing one if any
				Keyword keyword = find(keywords, new Keyword(stem.replaceAll("-0", "-")));
				// add its corresponding initial token
				keyword.add(term.replaceAll("-0", "-"));
			}
		}

		// reverse sort by frequency
		Collections.sort(keywords);
		tokenStream.close();
		return keywords;
	}

}