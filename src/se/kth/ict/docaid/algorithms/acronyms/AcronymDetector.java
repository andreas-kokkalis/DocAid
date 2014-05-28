package se.kth.ict.docaid.algorithms.acronyms;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author adrian, andrew
 *  
 */
public class AcronymDetector {
	private static HashMap<String, Boolean> acronymCheck = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> acronymCheckFirstUse = new HashMap<String, Boolean>();

	/**
	 * Generates a list of acronyms for the text
	 * 
	 * @param text Input text from which acronyms are extracted.
	 * @return The list of acronyms if exist, or an empty list.
	 */
	public static LinkedList<String> detectAcronyms(String text) {
		LinkedList<String> acronymList = new LinkedList<String>();

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
					if (possiblyAcronym(w)) {
						if (!acronymList.contains(w))
							acronymList.add(w);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return acronymList;

	}

	/**
	 * @param w
	 * @return
	 */
	private static boolean possiblyAcronym(String w) {
		if (w.length() == 1)
			return false;
		return isAcronym(w);

	}

	/**
	 * @param s
	 * @return
	 */
	private static boolean isAcronym(String s) {
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

		return (isAcronym && containsLetters);
	}

	/**
	 * @param listOfAcronyms
	 * @param text
	 * @return
	 */
	public static LinkedList<Acronym> checkAcronymsOnSight(String text) {

		LinkedList<String> listOfAcronyms = detectAcronyms(text);
		
		LinkedList<Acronym> checkedAcronyms = new LinkedList<Acronym>();

		text = text.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll(",", " ").replaceAll(":", " ").replaceAll(";", " ").replaceAll("\\(", " ").replaceAll("\\)", " ").replaceAll("-", " ");

		text = text.replaceAll("( )+", " ");

		String[] sentences = text.split("\\.\n");

		for (String acronym : listOfAcronyms) {
			Acronym currentAcronym = new Acronym(acronym);
			boolean acronymIsSpelled = false;
			boolean acronymIsSpelledOnFirstUse = true;
			for (int i = 0; i < sentences.length; i++) {
				String s = sentences[i];
				String[] words = s.split(" ");
				for (int j = 0; j < words.length; j++) {
					String w = words[j];
					if (w.equalsIgnoreCase(acronym)) {
						/*
						 * System.out.println(" found " + w + " at "+j);
						 */boolean definitionBefore = true;
						boolean definitionAfter = true;
						boolean spelledBefore = false;
						boolean spelledAfter = false;

						int sizeOfAcronym = acronym.length();
						if (j < sizeOfAcronym)
							definitionBefore = false;
						if (j > words.length - sizeOfAcronym)
							definitionAfter = false;

						/*
						 * System.out.println("CHECKING ACRONYM "+ acronym +" SIZE "+sizeOfAcronym);
						 */
						if (definitionBefore || definitionAfter) {
							if (definitionBefore) {
								/*
								 * System.out.println("BEFORE CHECK");
								 */String[] wordsBefore = new String[sizeOfAcronym];
								int indexBefore = 0;
								for (int k = j - sizeOfAcronym; k <= j - 1; k++) {
									try {
										/*
										 * System.out.println("checking the list before "+ k+ " "+words[k]);
										 */wordsBefore[indexBefore] = words[k];
										indexBefore++;
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								spelledBefore = checkWords(w, wordsBefore);

								if (spelledBefore)
									currentAcronym.setSpelledOut(wordsBefore);

							}
							if (definitionAfter) {
								/*
								 * System.out.println("AFTER CHECK");
								 */String[] wordsAfter = new String[sizeOfAcronym];
								int indexAfter = 0;
								for (int k = j + 1; k <= j + sizeOfAcronym; k++) {
									try {
										/*
										 * System.out.println("checking the list after "+ k+" "+ words[k]);
										 */wordsAfter[indexAfter] = words[k];
										indexAfter++;
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										break;
									}
								}
								spelledAfter = checkWords(w, wordsAfter);

								if (spelledAfter)
									currentAcronym.setSpelledOut(wordsAfter);

							}
							acronymIsSpelled = acronymIsSpelled || spelledBefore || spelledAfter;
							if (acronymIsSpelled) {
								currentAcronym.setIsSpelledOut(true);
								if (!acronymCheckFirstUse.containsKey(w)) {
									acronymCheckFirstUse.put(w, acronymIsSpelledOnFirstUse);
									currentAcronym.setSpelledOnFirstCheck(acronymIsSpelledOnFirstUse);
								}
							}

						}
						acronymIsSpelledOnFirstUse = false;

					}

					// else System.out.println("didn't find "+w);
				}

			}
			if (!acronymCheck.containsKey(acronym)) {
				acronymCheck.put(acronym, acronymIsSpelled);
				currentAcronym.setIsSpelledOut(acronymIsSpelled);
			}
			if (!acronymCheckFirstUse.containsKey(acronym)) {
				if (!acronymIsSpelled)
					acronymIsSpelledOnFirstUse = false;
				acronymCheckFirstUse.put(acronym, acronymIsSpelledOnFirstUse);
				currentAcronym.setSpelledOnFirstCheck(acronymIsSpelledOnFirstUse);
			}

			checkedAcronyms.add(currentAcronym);
			// System.out.println("DID I FIND THE ACRONYM "+ acronymIsSpelled+ " "+acronymIsSpelledOnFirstUse);
		}

		/*
		 * for (String s: acronymCheck.keySet()) System.out.println(s+" "+acronymCheck.get(s)+" "+acronymCheckFirstUse.get(s));
		 * 
		 * for (Acronym ac : checkedAcronyms) System.out.println(ac.toString());
		 */

		return checkedAcronyms;

	}

	/**
	 * @param w
	 * @param wordsAfter
	 * @return
	 */
	private static boolean checkWords(String w, String[] wordsAfter) {

		boolean okSpelling = true;

		for (int i = 0; i < wordsAfter.length; i++) {
			/*
			 * System.out.println("CHECKING ACRONYM " + w + " word " + wordsAfter[i] + " at position " + i+ " " + w.toCharArray()[i] +"!=" +
			 * wordsAfter[i].toCharArray()[0]);
			 */
			if (w.toCharArray()[i] != wordsAfter[i].toCharArray()[0])
				okSpelling = false;
		}

		return okSpelling;
	}

}
