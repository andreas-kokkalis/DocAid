package main.test;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import se.kth.ict.docaid.course.Course;
import se.kth.ict.docaid.filters.StopwordDictionairy;
import se.kth.ict.docaid.parser.CourseHomePageParser;
import se.kth.ict.docaid.parser.CourseXMLPageParser;

public class CourseIndexBuilder {
	private static String courseListURI = "http://www.kth.se/api/kopps/v1/courseRounds/";

	public static void main(String argv[]) {
		
		System.out.println(new String("Target").toLowerCase());
		
		try {
			StopwordDictionairy stopwords = new StopwordDictionairy();
			
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(courseListURI + "2014:2");

			HashMap<String, Course> courses = CourseXMLPageParser.retrieveCourseCodes(doc);
			Document doc1 = dBuilder.parse(courseListURI + "2014:1");
			HashMap<String, Course> courses1 = CourseXMLPageParser.retrieveCourseCodes(doc1);
			courses.putAll(courses1);

			int c = 0;
			for (Course course : courses.values()) {
				if (c == 20)
					break;
				CourseXMLPageParser.updateCourseContent(course);
				CourseHomePageParser.updateCourseInfo(course, stopwords);
//				System.out.println(course.toString());
				c++;
			}

			System.out.println("\n\n============================================================\n");
			Course course = courses.get("ID2203");
			CourseXMLPageParser.updateCourseContent(course);
			CourseHomePageParser.updateCourseInfo(course, stopwords);
			System.out.println(course.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
