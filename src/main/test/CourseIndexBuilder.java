package main.test;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import se.kth.ict.docaid.course.Course;
import se.kth.ict.docaid.database.CourseFetch;
import se.kth.ict.docaid.database.DatabaseConnection;
import se.kth.ict.docaid.database.logger.course.CourseLogger;
import se.kth.ict.docaid.filters.StopwordDictionairy;
import se.kth.ict.docaid.parser.CourseHomePageParser;
import se.kth.ict.docaid.parser.CourseXMLPageParser;

public class CourseIndexBuilder {
	private static String courseListURI = "http://www.kth.se/api/kopps/v1/courseRounds/";

	public static void main(String argv[]) {
		
		try {
			StopwordDictionairy stopwords = new StopwordDictionairy();
			
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(courseListURI + "2014:2");

			HashMap<String, Course> courses = CourseXMLPageParser.retrieveCourseCodes(doc);
			Document doc1 = dBuilder.parse(courseListURI + "2014:1");
			HashMap<String, Course> courses1 = CourseXMLPageParser.retrieveCourseCodes(doc1);
			courses.putAll(courses1);

		/*	int c = 0;
			HashMap<String, Course> selectedCourses = new HashMap<String, Course>();
			for (Course course : courses.values()) {
				System.out.println(c +" out of " + courses.size());
					{selectedCourses.put(course.getCode(), course);
					CourseXMLPageParser.updateCourseContent(course);
					CourseHomePageParser.updateCourseInfo(course, stopwords);
					System.out.println(course.toString2());}
				c++;
			}*/

//			System.out.println("\n\n============================================================\n");
//			Course course = courses.get("ID2203");
//			CourseXMLPageParser.updateCourseContent(course);
//			CourseHomePageParser.updateCourseInfo(course, stopwords);
//			System.out.println(course.toString());
//			System.out.println(course.getXmlReader().toString());
			
		/*	System.out.println("started");
			
			for (String s: CourseFetch.retrieveAllCourses(new DatabaseConnection().getConnection()).keySet())
				{
				System.out.println(CourseFetch.retrieveAllCourses(new DatabaseConnection().getConnection()).get(s).toString());
				}
			*/
			/*
			CourseLogger.storeCourseInfo(selectedCourses);
			*/

			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
