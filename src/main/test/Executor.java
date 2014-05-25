package main.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.tika.exception.TikaException;
import org.jsoup.Jsoup;
import org.xml.sax.SAXException;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.acronyms.AcronymDetector;
import se.kth.ict.docaid.algorithms.keywords.KeywordExtractor;
import se.kth.ict.docaid.reader.WebReader;
import aid.project.recovery.InputDocument;
import aid.project.recovery.WebDocument;
import aid.project.utils.UtilClass;

public class Executor {

	// static Version LUCENE_VERSION = Version.LUCENE_CURRENT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String url = "http://www.kth.se/api/kopps/v1/course/AG2425";

	/*	WebDocument doc = new WebDocument(url);
		WebReader reader = new WebReader(doc);
    	System.out.println(doc.toString());
		System.out.println("\n\n=========================================================\n=========================================================");
		System.out.println(reader.getKeywords());
*/
		
		System.out.println(AcronymDetector.checkAcronymsOnSight(AcronymDetector.detectAcronyms(Jsoup.parse("<p>Geographic Information Systems (GIS) are being used in a wide variety of applications. A key component of any GIS application is the underlying spatial database which must be designed to support efficient data storage, access and analysis operations.This course focuses on the design and development of spatial databases. Particular emphasis will be placed on the use of data modeling techniques to design a GIS database for a specific application. Students will work in small groups to develop a conceptual design for a GIS database and will then work individually to build a spatial database using digital data available through digital library as well as data digitized from existing maps, imagery and field data collected using GPS. The resulting database will be used to perform some basic spatial analysis.</p>").text()), Jsoup.parse("<p>Geographic Information Systems (GIS) are being used in a wide variety of applications. A key component of any GIS application is the underlying spatial database which must be designed to support efficient data storage, access and analysis operations.This course focuses on the design and development of spatial databases. Particular emphasis will be placed on the use of data modeling techniques to design a GIS database for a specific application. Students will work in small groups to develop a conceptual design for a GIS database and will then work individually to build a spatial database using digital data available through digital library as well as data digitized from existing maps, imagery and field data collected using GPS. The resulting database will be used to perform some basic spatial analysis.</p>").text()));
		LinkedList<File> files = UtilClass.getTestFiles();
		for (File f : files) {
			try {
				InputDocument thisDoc = UtilClass.getInstance().getInputDocument(f);
				System.out.println(thisDoc.getTitle() + " " + thisDoc.getNumberOfPages() + " " + thisDoc.getType());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println(e + "\n" + "cobra taka taka");
			}
		}

		System.out.println("\n\n=========================================================\n=========================================================");

	}

}
