package main.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

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

		String url = "http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en";

		WebDocument doc = new WebDocument(url);
		WebReader reader = new WebReader(doc);
//		System.out.println(doc.toString());
		System.out.println("\n\n=========================================================\n=========================================================");
		System.out.println(reader.getKeywords());

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
