package se.kth.ict.docaid.api.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import se.kth.ict.docaid.Recommender.RecommendedCourse;
import se.kth.ict.docaid.Recommender.RecommendedTutor;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.documents.InputDocument;
import se.kth.ict.docaid.documents.UtilClass;
import se.kth.ict.docaid.exceptions.InvalidTextLengthException;
import se.kth.ict.docaid.modules.CourseAdvisorWrp;

/**
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class CourseAdvisorExample {

	public static void main(String[] args) {
		
		int minFrequency = 4;
		int minWordLength = 2;
		boolean filterNonFrequent = true, filterNumbers = true, filterStopwords = true, filterStopPhrases = false;
		int limitCourse = 20;
		int limitTutor = 20;
		
		String preferencesInput = "distributed systems, databases, compilers, mobile development";
		InputDocument registrationDoc = null;
		
		
		ArrayList<String> docInput = new ArrayList<String>();
		
		DatabaseConnection connection = new DatabaseConnection();
		LinkedList<File> files = UtilClass.getTestFiles();
		for (File f : files) {
			if (f.getName().equalsIgnoreCase("test3.pdf") || f.getName().equalsIgnoreCase("test2.pdf") || f.getName().equalsIgnoreCase("test1.pdf")) {
				System.out.println("====================================================");
				System.out.println("TESTING DOCUMENT " + f.getName());
				InputDocument doc = null;
				try {
					doc = UtilClass.getInstance().getInputDocument(f);
					docInput.add(doc.getBody());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			CourseAdvisorWrp wrp = new CourseAdvisorWrp(
					docInput, 
					registrationDoc, 
					preferencesInput, 
					minFrequency, 
					minWordLength, 
					filterNonFrequent, 
					filterNumbers, 
					filterStopwords, 
					filterStopPhrases, 
					connection.getConnection(), 
					limitCourse, 
					limitTutor);

			
			for(RecommendedCourse course: wrp.getCourseReccomedations().keySet()) {
				System.out.println(course.toString());
			}
			System.out.println("\n\n");
			for(RecommendedTutor tutor: wrp.getInstructorRecommendations().keySet()) {
				System.out.println(tutor.toString());
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTextLengthException e) {
			e.printStackTrace();
		}
	}
}
