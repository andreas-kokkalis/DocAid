package main.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import maui.main.MauiWrapper;

import org.apache.lucene.util.Version;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import weka.core.Instance;
import aid.project.recovery.InputDocument;
import aid.project.recovery.WebDocument;
import aid.project.utils.UtilClass;

public class main {

	static Version LUCENE_VERSION = Version.LUCENE_CURRENT;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String url = "http://www.kth.se/student/kurser/kurs/ID2216?startterm=20131&l=en";
		
		WebDocument doc = new WebDocument(url);
		
		System.out.println(doc.toString());
		try {
			System.out.println(KeywordsGuesser.guessFromString(doc.getBody()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			LinkedList<File> files = UtilClass.getInstance().getTestFiles();
			for (File f : files)
					{
					try {
						InputDocument thisDoc = UtilClass.getInstance().getInputDocument(f);
						System.out.println(thisDoc.getTitle()+ " " + thisDoc.getNumberOfPages()+ " " +thisDoc.getType());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TikaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}


		MauiWrapper wrap = new MauiWrapper("", "", "keyphrextr");
		try {
			Instance[] st =wrap.extractTopicsFromText(doc.getBody(), 10);
			for (Instance s: st)
				System.out.println("xx "+ s.stringValue(1)+" " +s.value(4));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	
}
