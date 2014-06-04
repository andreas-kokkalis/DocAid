package se.kth.ict.docaid.modules;

import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.acronyms.AcronymDetector;

public class AcronymWrp {
	private String content;
	
	public AcronymWrp(String content) {
		this.content = content;
	}
	
	public LinkedList<Acronym> generateAcronyms() {
		LinkedList<Acronym> acronyms = AcronymDetector.checkAcronymsOnSight(content);
		return acronyms;
	}
}
