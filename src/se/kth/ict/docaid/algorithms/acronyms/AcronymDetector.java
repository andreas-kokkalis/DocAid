package se.kth.ict.docaid.algorithms.acronyms;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * The acronym detector class parses a text source and identifies acronyms.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class AcronymDetector {
	private static HashMap<String, Boolean> acronymCheck = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> acronymCheckFirstUse = new HashMap<String, Boolean>();

	/**
	 * Generates a list of acronyms for the text
	 * 
	 * @param text Input text from which acronyms are extracted.
	 * @return The list of acronyms if any exists, or an empty list.
	 */
	public static LinkedList<String> detectAcronyms(String text2) {
		LinkedList<String> acronymList = new LinkedList<String>();

		// System.out.println("text "+ text);
		
		System.out.println("======================================================================");
		
		String text = "";
		
		for (String x: text2.split("\n"))
				if (!isAllUpper(x)) text=text+x+"\n";
		
		//text = text.trim();
		text= text.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t", " ").replaceAll("\b", " ").replaceAll("\f", " ").replaceAll(",", " ").replaceAll(":", " ").replaceAll(";", " ").replaceAll("\\(", " ").replaceAll("\\)", " ").replaceAll("”", " ").replaceAll("\\p{Pd}", " ").replaceAll("[^\\x0A\\x0D\\x20-\\x7E]", "").replaceAll("=", " ");
		text = text.replaceAll("( )+", " ");
	//	text = text.replaceAll("( )+", " ");

//		String[] sentences = text.split("\\.\n");

		//text = text.replaceAll("\n", " ").replaceAll("\r", " ");

		String[] sentences = text.split("\\. ");

		// System.out.println("checking "+sentences.length);
		for (String s : sentences) {
			 //System.out.println(s);
			// s.replaceAll(",", "").replaceAll(":", "").replaceAll(";", "");
			String[] words;
			try {
				words = s.replaceAll(",", "").replaceAll(":", "").replaceAll(";", "").replaceAll("\\(", " ").replaceAll("\\)", " ").replaceAll("-", " ").split(" ");
				for (int i = 0; i<words.length; i++)
				{
				String w = words[i];
				boolean check = true;
				if (i>=1) {
					check = !isAllUpper(words[i-1]);
				}
				if (i<words.length-1) check = check&&(!isAllUpper(words[i+1]));
				
				
				if (check)
					if (possiblyAcronym(w)) {
						
				//		System.out.println(w+" IN "+s);
						
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

	
	private static boolean isAllUpper(String s) {
	    for(char c : s.toCharArray()) {
	       if(Character.isLetter(c) && Character.isLowerCase(c)) {
	    	   return false;
	        }
	       if (Character.isDigit(c)) return false; 
	    }
		
	    return true;
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
		
		if (insideIgnoreList(s)) return false;
		
		
		for (char c : s.toCharArray()) {
			if (Character.isLetter(c) && Character.isLowerCase(c)||(isPunctuation(c))) {
				isAcronym = false;
			}
		}

		boolean containsLetters = false;
		for (char c : s.toCharArray()) {
			if (Character.isLetter(c))
				containsLetters = true;
		}
		
		if (s.length()<2) return false;

		return (isAcronym && containsLetters);
	}

	  private static boolean insideIgnoreList(String s) {

		  return (s.equalsIgnoreCase("abstract")||s.equalsIgnoreCase("introduction")||s.equalsIgnoreCase("figure")||s.equalsIgnoreCase("table")||
					s.equalsIgnoreCase("experiments")||s.equalsIgnoreCase("related")||s.equalsIgnoreCase("works"))
					||s.equalsIgnoreCase("II")
					||s.equalsIgnoreCase("III")
					||s.equalsIgnoreCase("IV"); 
			  
					
	}

	public static boolean isPunctuation(char c) {
	        return c == ','
	            || c == '.'
	            || c == '!'
	            || c == '?'
	            || c == ':'
	            || c == ';'
	            || c =='°'
	            || c == '$'
	            || c =='\''
	            || c=='/'
	            || c=='’'
	            || c=='_'
	            || c=='"'
	            || c=='”'
	            || c=='&'
	            || c==' '
	            ;
	    }
	/**
	 * @param listOfAcronyms
	 * @param text
	 * @return
	 */
	public static LinkedList<Acronym> checkAcronymsOnSight(String text) {

		LinkedList<String> listOfAcronyms = detectAcronyms(text);
		
		LinkedList<Acronym> checkedAcronyms = new LinkedList<Acronym>();

		text = text.replaceAll("\n", " ").replaceAll("\r", " ").replaceAll(",", " ").replaceAll(":", " ").replaceAll(";", " ").replaceAll("\\(", " ").replaceAll("\\)", " ").replaceAll("”", " ").replaceAll("\\p{Pd}", " ").replaceAll("[^\\x0A\\x0D\\x20-\\x7E]", "").replaceAll("&", " ");

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
						boolean print = false; 
						/*if (acronym.equalsIgnoreCase("CIF")){
							print = true;
						}*/
						if (print)
						 System.out.println(" found " + w + " at "+j);
						boolean definitionBefore = true;
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
								int numForward = 0;
								for (int k = j - sizeOfAcronym; k <= j - 1; k++) {
									try {
										if (print)
										  System.out.println("checking the list before "+ k+ " "+words[k]);
										 
										if (k>=0){
											if (print) System.out.println(words[k]+" "+j);
											if (isConnectionWord(words[k])) {
												j++;
												numForward++;
											}
											else{
										wordsBefore[indexBefore] = words[k];
										indexBefore++;}}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										currentAcronym.setSpelledOnFirstCheck(false);
										currentAcronym.setIsSpelledOut(false);
										checkedAcronyms.add(currentAcronym);
										return checkedAcronyms;
									}
								}
								
								j=j-numForward;
								spelledBefore = checkWords(w, wordsBefore);

								if (spelledBefore)
									currentAcronym.setSpelledOut(wordsBefore);

							}
							if (definitionAfter) {
								if (print)
								System.out.println("AFTER CHECK");
								String[] wordsAfter = new String[sizeOfAcronym];
								int indexAfter = 0;
								for (int k = j + 1; k <= j + sizeOfAcronym; k++) {
									try {
									
										/*
										 * System.out.println("checking the list after "+ k+" "+ words[k]);
										 */
										if (print) System.out.println(words[k]);
										if (isConnectionWord(words[k])) j++;
										else {
										wordsAfter[indexAfter] = words[k];
										indexAfter++;}}
									catch (Exception e) {
										//e.printStackTrace();
										currentAcronym.setSpelledOnFirstCheck(false);
										currentAcronym.setIsSpelledOut(false);
										checkedAcronyms.add(currentAcronym);
										return checkedAcronyms;
										// break;
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

		w = w.toLowerCase();
		
		for (int i = 0; i < wordsAfter.length; i++) {
			wordsAfter[i]=wordsAfter[i].toLowerCase();
			/*
			 * System.out.println("CHECKING ACRONYM " + w + " word " + wordsAfter[i] + " at position " + i+ " " + w.toCharArray()[i] +"!=" +
			 * wordsAfter[i].toCharArray()[0]);
			 */
			if (wordsAfter[i].toCharArray().length>0)
			if (w.toCharArray()[i] != wordsAfter[i].toCharArray()[0])
				okSpelling = false;
		}

		return okSpelling;
	}

	public static boolean isConnectionWord(String s){
		return s.equals("to")||
				s.equals("on")||
				s.equals("for")||
				s.equals("from")||
				s.equals("of")||
				s.equals("with")||
				s.equals("the")||
				s.equals("and");
	}
	
}
