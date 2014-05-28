package se.kth.ict.docaid.database.logger.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.course.Course;
import se.kth.ict.docaid.database.DatabaseConnection;

public class CourseLogger {

	public static void storeCourseInfo(HashMap<String, Course> courses) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getConnection();
		storeCourseXMLData(courses, connection);
		storeCourseExtractedData(courses, connection);
	}

	/**
	 * @param courses
	 * @param connection
	 */
	private static void storeCourseXMLData(HashMap<String, Course> courses, Connection connection) {
		String storeCourseXML = "INSERT INTO course (code, " + "academicLevelCode, " + "contactName, " + "courseURL, " + "credits, " + "department, " + "departmentCode, " + "educationLevel, " + "gradeScaleCode, " + "isCanceled, "
				+ "recruitmentTextSv, " + "recruitmentTextEn, " + "round_, " + "subject, " + "subjectCode, " + "titleEn, " + "titleSv)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			
			
			PreparedStatement st = connection.prepareCall(storeCourseXML);
			connection.setAutoCommit(false);
			for (Course course : courses.values()) {
				if(courseExists(course.getCode(), connection))
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
	 * @param courses
	 * @param connection
	 */
	private static void storeCourseExtractedData(HashMap<String, Course> courses, Connection connection) {
		try {

			for (Course course : courses.values()) {
				for (Keyword keyword : course.getReader().getKeywords()) {
					// Check if the stem exists and if it's true get its id
					int id = stemExists(keyword.getStem(), connection);
					if (id == 0) {
						// Insert the stem and retrieve it's id
						String insert = "INSERT INTO keywords (stem) VALUES (?)";
						PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
						st.setString(1, keyword.getStem());
						st.executeUpdate();
						ResultSet rs = st.getGeneratedKeys();
						if (rs.next()) {
							id = rs.getInt(1);
						}
					}
					// Insert the stem in the database.
					String insert = "INSERT INTO keyword_ref (kid, code, terms) VALUES (?,?,?)";
					PreparedStatement st = connection.prepareStatement(insert);
					st.setInt(1, id);
					st.setString(2, course.getCode());
					st.setString(3, course.termsToString(keyword));
					st.executeUpdate();
					st.close();
				}
				for (Keyphrase keyphrase : course.getReader().getKeyphrases()) {
					// Check if the stem keyphrase and if it's true get its id
					int id = phraseExists(keyphrase.getPhrase(), connection);
					if (id == 0) {
						// Insert the keyphrase and retrieve it's id
						String insert = "INSERT INTO keyphrases (phrase, phrase_words) VALUES (?, ?)";
						PreparedStatement st = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
						st.setString(1, keyphrase.getPhrase());
						st.setString(2, course.phraseWordsToString(keyphrase));
						st.executeUpdate();
						ResultSet rs = st.getGeneratedKeys();
						if (rs.next()) {
							id = rs.getInt(1);
						}
					}
					// Insert the keyphrase reference in the database.
					String insert = "INSERT INTO keyphrase_ref (kid, code) VALUES (?,?)";
					PreparedStatement st = connection.prepareStatement(insert);
					st.setInt(1, id);
					st.setString(2, course.getCode());
					st.executeUpdate();
				}
				for (Acronym acronym : course.getReader().getAcronyms()) {
					// Check if the acronym exists and if it's true get its id
					int id = acronymExists(acronym.getAcronym(), connection);
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
					// Insert the acronym reference in the database.
					String insert = "INSERT INTO acronym_ref (aid, code) VALUES (?,?)";
					PreparedStatement st = connection.prepareStatement(insert);
					st.setInt(1, id);
					st.setString(2, course.getCode());
					st.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param stem
	 * @param connection
	 * @return
	 */
	private static int stemExists(String stem, Connection connection) {
		String select = "SELECT kid FROM keywords WHERE stem=?";
		int id = 0;
		try {
			PreparedStatement st = connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, stem);
			ResultSet results = st.executeQuery();
			results.last();
			if (results.getRow() == 0) {
				return 0;
			}
			results.beforeFirst();
			results.next();
			id = results.getInt(1);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * @param phrase
	 * @param connection
	 * @return
	 */
	private static int phraseExists(String phrase, Connection connection) {
		String select = "SELECT kid FROM keyphrases WHERE phrase=?";
		int id = 0;
		try {
			PreparedStatement st = connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, phrase);
			ResultSet results = st.executeQuery();
			results.last();
			if (results.getRow() == 0) {
				return 0;
			}
			results.beforeFirst();
			results.next();
			id = results.getInt(1);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * @param acronym
	 * @param connection
	 * @return
	 */
	private static int acronymExists(String acronym, Connection connection) {
		String select = "SELECT aid FROM acronyms WHERE acron=?";
		int id = 0;
		try {
			PreparedStatement st = connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, acronym);
			ResultSet results = st.executeQuery();
			results.last();
			if (results.getRow() == 0) {
				return 0;
			}
			results.beforeFirst();
			results.next();
			id = results.getInt(1);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private static boolean courseExists(String courseCode, Connection connection) {
		String select = "SELECT s.code FROM course s where s.code = ?";
		try {
			PreparedStatement st = connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, courseCode);
			ResultSet results = st.executeQuery();
			results.last();
			if (results.getRow() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
