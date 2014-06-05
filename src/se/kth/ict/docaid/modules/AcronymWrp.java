package se.kth.ict.docaid.modules;

import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.acronyms.AcronymDetector;

/**
 * Extracts keyphrases from a text source
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class AcronymWrp {
	private String content;
	
	/**
	 * @param content The text source
	 */
	public AcronymWrp(String content) {
		this.content = content;
	}
	
	/**
	 * Generates the acronyms
	 * 
	 * @return The list of acronyms
	 */
	public LinkedList<Acronym> generateAcronyms() {
		LinkedList<Acronym> acronyms = AcronymDetector.checkAcronymsOnSight(content);
		return acronyms;
	}
}
