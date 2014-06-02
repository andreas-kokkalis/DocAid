package se.kth.ict.docaid.reader;


import java.util.LinkedList;

import se.kth.ict.docaid.documents.InputDocument;

public class RegistrationReader{
	private InputDocument inputDocument;

	public RegistrationReader(InputDocument inputDocument) {
		this.inputDocument = inputDocument;
	}

	public LinkedList<String> getCourseCodes() {
		LinkedList<String> courseCodes = new LinkedList<String>();

/*		for (String code : detectCourseCodes(inputDocument.getBody()))
			System.out.println(code);;
		*/
		return detectCourseCodes(inputDocument.getBody());
	}
	

	/**
	 * Generates a list of acronyms for the text
	 * 
	 * @param text Input text from which acronyms are extracted.
	 * @return The list of acronyms if exist, or an empty list.
	 */
	public static LinkedList<String> detectCourseCodes(String text) {
		LinkedList<String> codeList = new LinkedList<String>();

		// System.out.println("text "+ text);

		text = text.replaceAll("\n", " ").replaceAll("\r", " ");

		String[] sentences = text.split("\\. ");

		// System.out.println("checking "+sentences.length);
		for (String s : sentences) {
			// System.out.println(s);
			// s.replaceAll(",", "").replaceAll(":", "").replaceAll(";", "");
			String[] words;
			try {
				words = s.replaceAll(",", "").replaceAll(":", "").replaceAll(";", "").replaceAll("\\(", " ").replaceAll("\\)", " ").replaceAll("-", " ").split(" ");
				for (String w : words) {
					if (possiblyCourseCode(w)) {
						if (!codeList.contains(w))
							codeList.add(w);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return codeList;

	}
	
	private static boolean possiblyCourseCode(String w) {
		if (w.length() == 1)
			return false;
		return isCourseCode(w);

	}

	/**
	 * @param s
	 * @return
	 */
	private static boolean isCourseCode(String s) {
		boolean isAcronym = true;
		for (char c : s.toCharArray()) {
			if (Character.isLetter(c) && Character.isLowerCase(c)) {
				isAcronym = false;
			}
		}

		boolean containsLetters = false;
		for (char c : s.toCharArray()) {
			if (Character.isLetter(c))
				containsLetters = true;
		}
		
		boolean containsNumbers = false;
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c))
				containsNumbers = true;
		}

		boolean isSizeCorrect = (s.toCharArray().length==6 || s.toCharArray().length==5);
		
		return (isAcronym && containsLetters && containsNumbers && isSizeCorrect);
	}

}
