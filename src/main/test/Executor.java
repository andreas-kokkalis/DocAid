package main.test;

import java.util.ArrayList;

import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.filters.StopwordDictionairy;
import se.kth.ict.docaid.reader.WebReader;

public class Executor {

	// static Version LUCENE_VERSION = Version.LUCENE_CURRENT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String url = "http://www.kth.se/api/kopps/v1/course/AG2425";

	/*	WebDocument doc = new WebDocument(url);
		WebReader reader = new WebReader(doc);
    	System.out.println(doc.toString());
		System.out.println("\n\n=========================================================\n=========================================================");
		System.out.println(reader.getKeywords());
*/
		
//		System.out.println(AcronymDetector.checkAcronymsOnSight(AcronymDetector.detectAcronyms(Jsoup.parse("<p>Geographic Information Systems (GIS) are being used in a wide variety of applications. A key component of any GIS application is the underlying spatial database which must be designed to support efficient data storage, access and analysis operations.This course focuses on the design and development of spatial databases. Particular emphasis will be placed on the use of data modeling techniques to design a GIS database for a specific application. Students will work in small groups to develop a conceptual design for a GIS database and will then work individually to build a spatial database using digital data available through digital library as well as data digitized from existing maps, imagery and field data collected using GPS. The resulting database will be used to perform some basic spatial analysis.</p>").text()), Jsoup.parse("<p>Geographic Information Systems (GIS) are being used in a wide variety of applications. A key component of any GIS application is the underlying spatial database which must be designed to support efficient data storage, access and analysis operations.This course focuses on the design and development of spatial databases. Particular emphasis will be placed on the use of data modeling techniques to design a GIS database for a specific application. Students will work in small groups to develop a conceptual design for a GIS database and will then work individually to build a spatial database using digital data available through digital library as well as data digitized from existing maps, imagery and field data collected using GPS. The resulting database will be used to perform some basic spatial analysis.</p>").text()));
//		LinkedList<File> files = UtilClass.getTestFiles();
//		for (File f : files) {
//			try {
//				InputDocument thisDoc = UtilClass.getInstance().getInputDocument(f);
//				System.out.println(thisDoc.getTitle() + " " + thisDoc.getNumberOfPages() + " " + thisDoc.getType());
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (SAXException e) {
//				e.printStackTrace();
//			} catch (TikaException e) {
//				e.printStackTrace();
//			} catch (NullPointerException e) {
//				System.out.println(e + "\n" + "cobra taka taka");
//			}
//		}

		System.out.println("\n\n=========================================================\n=========================================================");
		String url = "http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en";
		WebDocument doc = new WebDocument(url);
		StopwordDictionairy stopwords = new StopwordDictionairy();
		WebReader reader = new WebReader(doc);
		@SuppressWarnings("unused")
		ArrayList<Keyword> keywords = reader.getKeywords();
		ArrayList<Keyphrase> keyphrases = reader.getKeyphrases();
		for(Keyphrase keyphrase: keyphrases)
			System.out.println(keyphrase.toString());
	}

}
