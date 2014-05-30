package main.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import se.kth.ict.docaid.Recommender.Recommender;
import se.kth.ict.docaid.database.CourseFetch;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.documents.WebDocument;
import se.kth.ict.docaid.reader.DocumentReader;

import aid.project.utils.UtilClass;

import com.google.common.collect.ImmutableSortedMap;

public class Executor {

	// static Version LUCENE_VERSION = Version.LUCENE_CURRENT;

	/**
	 * @param args
	 * @throws TikaException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, SAXException, TikaException {
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
/*		String url = "http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en";
		WebDocument doc = new WebDocument(url);
		
		String url2 = "http://www.kth.se/student/kurser/kurs/AG2417?l=en";
		WebDocument doc2 = new WebDocument(url2);
		
		System.out.println(Recommender.getWeight(doc, doc2));
		*/
		InputDocument thisDoc = UtilClass.getInstance().getInputDocument(new File("testdata/Exercise 1.doc"));
		 HashMap<String, Float> hM = Recommender.getCourseRecommendation(thisDoc, CourseFetch.retrieveAllCourses(new DatabaseConnection().getConnection()));
		for (String s : hM.keySet())
			System.out.println(s+" "+hM.get(s));
		
		/*		StopwordDictionairy stopwords = new StopwordDictionairy();
		WebReader reader = new WebReader(doc);
		@SuppressWarnings("unused")
		ArrayList<Keyword> keywords = reader.getKeywords();
		ArrayList<Keyphrase> keyphrases = reader.getKeyphrases();
		for(Keyphrase keyphrase: keyphrases)
			System.out.println(keyphrase.toString());*/
	}

}
