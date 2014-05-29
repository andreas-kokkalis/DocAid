package main.test;

import java.util.HashMap;

import se.kth.ict.docaid.course.Course;
import se.kth.ict.docaid.database.CourseFetch;
import se.kth.ict.docaid.database.DatabaseConnection;

/**
 * Loads courses from the database given their ids.
 * 
 * @author andrew
 *
 */
public class LoadCourseDB {

	public static void main(String[] args) {
		DatabaseConnection connection = new DatabaseConnection();
//		Course course = CourseFetch.getCourseData("A21AYA", connection.getConnection());
//		System.out.println(course.toString2());

		
		HashMap<String, Course> courses = CourseFetch.retrieveAllCourses(connection.getConnection());
		for(Course c: courses.values())
			c.toString2();
	}

}
