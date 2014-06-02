package se.kth.ict.docaid.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.database.DatabaseConnection;

public class StoreCourses {

	/**
	 * Store all data relevant to a course in the database.
	 * 
	 * @param courses The list of courses for which the data are stored.
	 */
	public static void storeCourseInfo(HashMap<String, Course> courses) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getConnection();
		storeCourseXMLData(courses, connection);
		storeCourseExtractedData(courses, connection);
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Store the data for a list of courses as extracted form their corresponding XML pages using the KTH API.
	 * 
	 * @param courses The list of courses for which the data are stored.
	 * @param connection The sql connection
	 */
	private static void storeCourseXMLData(HashMap<String, Course> courses, Connection connection) {
		String storeCourseXML = "INSERT INTO course (code, " + "academicLevelCode, " + "contactName, " + "courseURL, " + "credits, " + "department, " + "departmentCode, " + "educationLevel, " + "gradeScaleCode, " + "isCanceled, "
				+ "recruitmentTextSv, " + "recruitmentTextEn, " + "round_, " + "subject, " + "subjectCode, " + "titleEn, " + "titleSv, "+ "lang"+")" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement st = connection.prepareCall(storeCourseXML);
			connection.setAutoCommit(false);
			for (Course course : courses.values()) {
				if(FetchCourses.courseExists(course.getCode(), connection))
					continue;
				st.setString(1, course.getCode());
				st.setString(2, course.getAcademicLevelCode());
				st.setString(3, course.getContactName());
				st.setString(4, course.getCourseURL());
				st.setInt(5, course.getCredits());
				st.setString(6, course.getDepartment());
				st.setString(7, course.getDepartmentCode());
				st.setString(8, course.getEducationLevel());
				st.setString(9, course.getGradeScaleCode());
				st.setBoolean(10, course.isCancelled());
				st.setString(11, course.getRecruitmentTextSv());
				st.setString(12, course.getRecruitmentTextEn());
				st.setInt(13, course.getRound());
				st.setString(14, course.getSubject());
				st.setString(15, course.getSubjectCode());
				st.setString(16, course.getTitleEn());
				st.setString(17, course.getTitleSv());
				st.setString(18, course.getLanguage());
				st.addBatch();
			}
			st.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * Stores the keywords, keyphrases, acronyms for a given list of courses.
	 * 
	 * @param courses The list of courses for which keywords, keyphrases, acronyms are stored.
	 * @param connection The sql connection
	 */
	private static void storeCourseExtractedData(HashMap<String, Course> courses, Connection connection) {

		try {
			connection.setAutoCommit(true);
			for (Course course : courses.values()) {
				for (Keyword keyword : course.getReader().getKeywords()) {
					// Check if the stem exists and if it's true get its id
					int id = FetchCourses.stemExists(keyword.getStem(), connection);
					if (id == 0) {
						// Insert the stem and retrieve it's id
						String insert = "INSERT INTO keywords (stem, frequency) VALUES (?, ?)";
						PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
						st.setString(1, keyword.getStem());
						st.setInt(2, keyword.getFrequency());
						st.executeUpdate();
						ResultSet rs = st.getGeneratedKeys();
						if (rs.next()) {
							id = rs.getInt(1);
						}
					}
					if(FetchCourses.keywordRefExists(id, course.getCode(), connection) == 0) {
						// Insert the stem in the database.
						String insert = "INSERT INTO keyword_ref (kid, code, terms) VALUES (?,?,?)";
						PreparedStatement st = connection.prepareStatement(insert);
						st.setInt(1, id);
						st.setString(2, course.getCode());
						st.setString(3, course.termsToString(keyword));
						st.executeUpdate();
						st.close();
					}
				}
				for (Keyphrase keyphrase : course.getReader().getKeyphrases()) {
					// Check if the stem keyphrase and if it's true get its id
					int id = FetchCourses.phraseExists(keyphrase.getPhrase(), connection);
					if (id == 0) {
						// Insert the keyphrase and retrieve it's id
						String insert = "INSERT INTO keyphrases (phrase, stems, factor) VALUES (?, ?, ?)";
						PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
						st.setString(1, keyphrase.getPhrase());
						st.setString(2, keyphrase.phraseStemsToString());
						st.setDouble(3, keyphrase.getFactor());
						st.executeUpdate();
						ResultSet rs = st.getGeneratedKeys();
						if (rs.next()) {
							id = rs.getInt(1);
						}
					}
					if(FetchCourses.keyphraseRefExists(id, course.getCode(), connection) == 0) {
						// Insert the keyphrase reference in the database.
						String insert = "INSERT INTO keyphrase_ref (kid, code) VALUES (?,?)";
						PreparedStatement st = connection.prepareStatement(insert);
						st.setInt(1, id);
						st.setString(2, course.getCode());
						st.executeUpdate();
					}
				}
				for (Acronym acronym : course.getReader().getAcronyms()) {
					// Check if the acronym exists and if it's true get its id
					int id = FetchCourses.acronymExists(acronym.getAcronym(), connection);
					if (id == 0) {
						// Insert the acronym and retrieve it's id
						String insert = "INSERT INTO acronyms (acron, description) VALUES (?, ?)";
						PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
						st.setString(1, acronym.getAcronym());
						st.setString(2, acronym.spelloutString());
						st.executeUpdate();
						ResultSet rs = st.getGeneratedKeys();
						if (rs.next()) {
							id = rs.getInt(1);
						}
					}
					if(FetchCourses.acronymRefExists(id, course.getCode(), connection) == 0) {
						// Insert the acronym reference in the database.
						String insert = "INSERT INTO acronym_ref (aid, code) VALUES (?,?)";
						PreparedStatement st = connection.prepareStatement(insert);
						st.setInt(1, id);
						st.setString(2, course.getCode());
						st.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
