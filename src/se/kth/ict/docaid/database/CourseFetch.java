package se.kth.ict.docaid.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.pdfbox.util.operator.SetWordSpacing;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.course.Course;

/**
 * Class with static methods for retrieving information for courses from the database.
 * 
 * @author andrew
 *
 */
public class CourseFetch {
	/**
	 * Checks if a keyword exists in the db.
	 * 
	 * @param stem The keyword stem to check if exists
	 * @param connection The sql connection
	 * @return The id of the stem if exists, else 0
	 */
	public static int stemExists(String stem, Connection connection) {
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
	 * Checks if a keyword reference to a course exists in the db
	 * 
	 * @param kid The id of the keyword
	 * @param courseCode The id of the course
	 * @param connection The sql connection
	 * @return The id of the keyword reference if exists else 0.
	 */
	public static int keywordRefExists(int kid, String courseCode, Connection connection) {
		String select = "SELECT id FROM keyword_ref WHERE kid=? AND code = ?";
		int id = 0;
		try {
			PreparedStatement st = connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setInt(1, kid);
			st.setString(2, courseCode);
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
	 * Checks if a phrase exists in the db.
	 * 
	 * @param phrase The phrase to check if exists in the db.
	 * @param connection The sql connection
	 * @return The id of the phrase if exists else 0.
	 */
	public static int phraseExists(String phrase, Connection connection) {
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
	 * Checks if a reference to a keyword and a course exists.
	 * 
	 * @param kid The id of the keyphrase
	 * @param courseCode The id of the course
	 * @param connection The sql connection
	 * @return The id of the keyword reference if exists else 0.
	 */
	public static int keyphraseRefExists(int kid, String courseCode, Connection connection) {
		String select = "SELECT id FROM keyphrase_ref WHERE kid=? AND code = ?";
		int id = 0;
		try {
			PreparedStatement st = connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setInt(1, kid);
			st.setString(2, courseCode);
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
	 * Checks if an acronym exists in the db
	 * 
	 * @param acronym The acronym to check if exists in the db.
	 * @param connection The sql connection
	 * @return The id if the acronym or 0 if not exists.
	 */
	public static int acronymExists(String acronym, Connection connection) {
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

	/**
	 * Checks if a reference to an acronym and a course exists in the db.
	 * 
	 * @param aid The id of the acronym
	 * @param courseCode The id of the course
	 * @param connection The sql connection
	 * @return The id of the reference is exists, else 0.
	 */
	public static int acronymRefExists(int aid, String courseCode, Connection connection) {
		String select = "SELECT id FROM acronym_ref WHERE aid = ? AND code = ?";
		int id = 0;
		try {
			PreparedStatement st = connection.prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setInt(1, aid);
			st.setString(2, courseCode);
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
	 * Checks if there is an entry in the database for the given course code.
	 * 
	 * @param courseCode The id of the course
	 * @param connection The sql connection
	 * @return True of course exists else false.
	 */
	public static boolean courseExists(String courseCode, Connection connection) {
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

	/**
	 * Retrieves the information of a given course from the db.
	 * 
	 * @param courseCode the id of the course
	 * @param connection The sql connection
	 * @return A new course object that contains information about the xml data, keywords, keyphrases and acronyms.
	 */
	public static Course getCourseData(String courseCode, Connection connection) {
		Course course = new Course();
		String query = "Select academicLevelCode, contactName, courseURL, credits, department, departmentCode, educationLevel, gradeScaleCode, isCanceled, recruitmentTextSv, recruitmentTextEn, round_, subject, subjectCode, titleEn, titleSv, lang  from course where code = '"
				+ courseCode + "'";

		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// (query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// st.setString(1, courseCode);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			results.next();

			String academicLevelCode = results.getString("academicLevelCode");
			String contactName = results.getString("contactName");
			String courseURL = results.getString("courseURL");
			int credits = results.getInt("credits");
			String department = results.getString("department");
			String departmentCode = results.getString("departmentCode");
			String educationLevel = results.getString("educationLevel");
			String gradeScaleCode = results.getString("gradeScaleCode");
			boolean isCancelled = results.getBoolean("isCanceled");
			String recruitmentTextSv = results.getString("recruitmentTextSv");
			String recruitmentTextEn = results.getString("recruitmentTextEn");
			int round = results.getInt("round_");
			String subject = results.getString("subject");
			String subjectCode = results.getString("subjectCode");
			String titleEn = results.getString("titleEn");
			String titleSv = results.getString("titleSv");
			String language = results.getString("lang");

			course.setCode(courseCode);
			if (academicLevelCode == null)
				course.setAcademicLevelCode("");
			else
				course.setAcademicLevelCode(academicLevelCode);
			if (contactName == null)
				course.setContactName("");
			else
				course.setContactName(contactName);
			if (courseURL == null)
				course.setCourseURL("");
			else
				course.setCourseURL(courseURL);
			course.setCredits(credits);
			if (department == null)
				course.setDepartment("");
			else
				course.setDepartment(department);
			if (departmentCode == null)
				course.setDepartmentCode("");
			else
				course.setDepartmentCode(departmentCode);
			if (educationLevel == null)
				course.setEducationLevel("");
			else
				course.setEducationLevel(educationLevel);
			if (gradeScaleCode == null)
				course.setGradeScaleCode("");
			else
				course.setGradeScaleCode(gradeScaleCode);
			course.setCancelled(isCancelled);
			if (recruitmentTextSv == null)
				course.setRecruitmentTextSv("");
			else
				course.setRecruitmentTextSv(recruitmentTextSv);
			if (recruitmentTextEn == null)
				course.setRecruitmentTextEn("");
			else
				course.setRecruitmentTextEn(recruitmentTextEn);
			course.setRound(round);
			if (subject == null)
				course.setSubject("");
			else
				course.setSubject(subject);
			if (subjectCode == null)
				course.setSubjectCode("");
			else
				course.setSubjectCode(subjectCode);
			if (titleEn == null)
				course.setTitleEn("");
			else
				course.setTitleEn(titleEn);
			if (titleSv == null)
				course.setTitleSv("");
			else
				course.setTitleSv(titleSv);
			if (language == null)
				course.setLanguage("");
			else
				course.setLanguage(language);

		//	System.out.println(course.toString2());
			
			results.close();
			st.close();
			course.setKeywords(fetchCourseKeywords(course.getCode(), connection));
			course.setKeyphrases(fetchCourseKeyphrases(course.getCode(), connection));
			course.setAcronyms(fetchCourseAcronyms(course.getCode(), connection));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return course;
	}

	/**
	 * Retrieves the keywords associated with a course.
	 * 
	 * @param courseCode Th id of the course
	 * @param connection The sql connection
	 * @return Returns the list of Keywords associated with the given course.
	 */
	private static ArrayList<Keyword> fetchCourseKeywords(String courseCode, Connection connection) {
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		String query = "SELECT k.stem, r.terms, k.frequency FROM keywords k, keyword_ref r WHERE r.code = '" + courseCode + "' and r.kid = k.kid";
		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			while (results.next()) {
				String stem = results.getString("stem");
				HashSet<String> terms = new HashSet<String>(Arrays.asList(results.getString("terms").split(" ")));
				int frequency = results.getInt("frequency");
				Keyword keyword = new Keyword(stem, frequency, terms);
				keywords.add(keyword);
			}
			results.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return keywords;
	}

	/**
	 * Returns the keyphrases associated with a course.
	 * 
	 * @param courseCode The id of the course.
	 * @param connection The sql connection
	 * @return The list of Keyphrase associated with the given course.
	 */
	private static ArrayList<Keyphrase> fetchCourseKeyphrases(String courseCode, Connection connection) {
		ArrayList<Keyphrase> keyphrases = new ArrayList<Keyphrase>();

		String query = "SELECT k.phrase, k.stems, k.factor FROM keyphrases k, keyphrase_ref r WHERE r.code = '" + courseCode + "' and r.kid = k.kid";
		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			while (results.next()) {
				String phrase = results.getString("phrase");
				ArrayList<String> stems = new ArrayList<String>(Arrays.asList(results.getString("stems").split(" ")));
				double factor = results.getDouble("factor");
				Keyphrase keyphrase = new Keyphrase(phrase, factor, stems);
				keyphrases.add(keyphrase);
			}
			results.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return keyphrases;
	}

	/**
	 * Retrieves the acronyms associated with a course.
	 * 
	 * @param courseCode The id of the course.
	 * @param connection The sql connection
	 * @return The list of Acronym asssociated with the given course.
	 */
	private static ArrayList<Acronym> fetchCourseAcronyms(String courseCode, Connection connection) {
		ArrayList<Acronym> acronyms = new ArrayList<Acronym>();
		String query = "SELECT a.acron, a.description FROM acronyms a, acronym_ref r WHERE r.code = '"+courseCode+"' and r.aid = a.aid";
		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			while (results.next()) {
				String acron = results.getString("acron");
				String description = results.getString("description");
				Acronym acronym = new Acronym(acron, description);
				acronyms.add(acronym);
			}
			results.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return acronyms;
	}

	public static HashMap<String, Course> retrieveAllCourses(Connection connection) {
		HashMap<String, Course> courses = new HashMap<String, Course>();
		String query = "SELECT code from course";
		try {
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet results = st.executeQuery(query);
			results.last();
			if (results.getRow() == 0) {
				return null;
			}
			results.beforeFirst();
			while (results.next()) {
				String code = results.getString("code");
				Course course = getCourseData(code, connection);
				//System.out.println(course.toString2());
				courses.put(code, course);
			}
			results.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	
}
