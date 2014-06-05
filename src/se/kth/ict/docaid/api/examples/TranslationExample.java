package se.kth.ict.docaid.api.examples;

import se.kth.ict.docaid.translator.Translator;

/**
 * Example to run the translator module
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class TranslationExample {
	public static void main(String[] args) {
		String input = "“The objective of our research was to develop a structural battery consisting of multifunctional lightweight materials that simultaneously manage mechanical loads, and store electrical energy,” says Eric Jacques, a researcher in Aeronautical and Vehicle Engineering at KTH.";
		
		System.out.println(Translator.translate("en", "sv", input));
	}
}
