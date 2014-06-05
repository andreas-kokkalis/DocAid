package se.kth.ict.docaid.api.examples;

import se.kth.ict.docaid.modules.CourseIndexBuilderWrp;

/**
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class CourseIndexBuilderExample {
	public static void main(String argv[]) {
		CourseIndexBuilderWrp.storeCourseIndexInDB();
		CourseIndexBuilderWrp.loadAllCoursesFromDB();
	}
}
