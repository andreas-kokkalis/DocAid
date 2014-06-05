package se.kth.ict.docaid.course;

import java.util.ArrayList;
import java.util.LinkedList;

import se.kth.ict.docaid.algorithms.acronyms.Acronym;
import se.kth.ict.docaid.algorithms.keyphrases.Keyphrase;
import se.kth.ict.docaid.algorithms.keywords.Keyword;
import se.kth.ict.docaid.reader.WebReader;

/**
 * The class holds information about a course. It contains all information extracted from the XML web page of the course, along with an instance of the class reader. The
 * reader contains extracted keywords, keyphrases and abbreviations from the hope page of the course.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 * 
 */
public class Course {
	/**
	 * Course code. Used to identify the course and construct unique urls.
	 */
	private String code;
	/**
	 * The course round.
	 */
	private int round; 
	/**
	 * If course is cancelled value is true.
	 */
	private boolean isCancelled;
	/**
	 * The course title in English.
	 */
	private String titleEn;
	/**
	 * The course title in Swedish.
	 */
	private String titleSv;
	/**
	 * The credits of the course multiplied by 10.
	 */
	private int credits;
	/**
	 * Cycle bachelor 1 master2
	 */
	private String educationLevel;
	private String academicLevelCode;
	/**
	 * Identifies the course subject uniquely.
	 */
	private String subjectCode;
	/**
	 * Text representation of the course subject.
	 */
	private String subject;
	/**
	 * A-F or pass fail or numeric.
	 */
	private String gradeScaleCode;
	/**
	 * Identifies the department that offers the course uniquely.
	 */
	private String departmentCode;
	/**
	 * Department name.
	 */
	private String department;
	/**
	 * Course responsible contact information
	 */
	private String contactName;
	/**
	 * Description of the course content in English
	 */
	private String recruitmentTextEn;
	/**
	 * Description of the course content in Swedish.
	 */
	private String recruitmentTextSv;
	/**
	 * Additional url of the course.
	 */
	private String courseURL;
	/**
	 * abbreviations, keywords, keyphrases
	 */
	private WebReader reader;

	/**
	 * Language of course
	 */
	private String language;

	private ArrayList<Keyword> keywords;
	private ArrayList<Keyphrase> keyphrases;
	private ArrayList<Acronym> acronyms;

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getTitleSv() {
		return titleSv;
	}

	public void setTitleSv(String titleSv) {
		this.titleSv = titleSv;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getAcademicLevelCode() {
		return academicLevelCode;
	}

	public void setAcademicLevelCode(String academicLevelCode) {
		this.academicLevelCode = academicLevelCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGradeScaleCode() {
		return gradeScaleCode;
	}

	public void setGradeScaleCode(String gradeScaleCode) {
		this.gradeScaleCode = gradeScaleCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getRecruitmentTextEn() {
		return recruitmentTextEn;
	}

	public void setRecruitmentTextEn(String recruitmentTextEn) {
		this.recruitmentTextEn = recruitmentTextEn;
	}

	public String getRecruitmentTextSv() {
		return recruitmentTextSv;
	}

	public void setRecruitmentTextSv(String recruitmentTextSv) {
		this.recruitmentTextSv = recruitmentTextSv;
	}

	public String getCourseURL() {
		return courseURL;
	}

	public void setCourseURL(String courseURL) {
		this.courseURL = courseURL;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public WebReader getReader() {
		return reader;
	}

	public void setReader(WebReader reader) {
		this.reader = reader;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String termsToString(Keyword keyword) {
		StringBuilder st = new StringBuilder();
		for (String string : keyword.getTerms())
			st.append(string).append(" ");
		return st.toString();
	}

	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}

	public ArrayList<Keyphrase> getKeyphrases() {
		return keyphrases;
	}

	public void setKeyphrases(ArrayList<Keyphrase> keyphrases) {
		this.keyphrases = keyphrases;
	}

	public ArrayList<Acronym> getAcronyms() {
		return acronyms;
	}

	public LinkedList<Acronym> getAcronyms2() {
		if (acronyms != null)
			return new LinkedList<>(acronyms);
		return new LinkedList<Acronym>();
	}

	public void setAcronyms(ArrayList<Acronym> acronyms) {
		this.acronyms = acronyms;
	}

	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		st.append("\n-------------------------------------------------").append("\n");
		st.append("Course:\t" + titleEn + "\tcode:\t" + code + "\tLanguage:\t" + language).append("\n");
		st.append("-------------------------------------------------").append("\n");
		st.append("code:").append("\t\t\t").append(code).append("\n");
		st.append("round:").append("\t\t\t").append(round).append("\n");
		st.append("canceled:").append("\t\t").append(isCancelled).append("\n");
		st.append("Title En:").append("\t\t").append(titleEn).append("\n");
		st.append("Title Sv:").append("\t\t").append(titleSv).append("\n");
		st.append("Credits:").append("\t\t").append(credits).append("\n");
		st.append("Educational level:").append("\t").append(educationLevel).append("\n");
		st.append("Academic level code:").append("\t").append(academicLevelCode).append("\n");
		st.append("Subject code:").append("\t\t").append(subjectCode).append("\n");
		st.append("Subject:").append("\t\t").append(subject).append("\n");
		st.append("Grade scale code:").append("\t").append(gradeScaleCode).append("\n");
		st.append("Department code:").append("\t").append(departmentCode).append("\n");
		st.append("Department:").append("\t\t").append(department).append("\n");
		st.append("Contact Name:").append("\t\t").append(contactName).append("\n");
		st.append("Recruitement text En:").append("\t").append(recruitmentTextEn).append("\n");
		st.append("Recruitement text Sv:").append("\t").append(recruitmentTextSv).append("\n");
		st.append("URL:").append("\t\t\t").append(courseURL).append("\n");
		st.append("Keywords: ").append("\n");
		for (Keyword keyword : reader.getKeywords())
			st.append(keyword.toString()).append("\n");
		st.append("Keyphrases: ").append("\n");
		for (Keyphrase keyphrase : reader.getKeyphrases())
			st.append(keyphrase.toString()).append("\n");
		st.append("Acronyms: ").append("\n");
		for (Acronym acronym : reader.getAcronyms())
			st.append(acronym.toString()).append("\n");
		st.append("-------------------------------------------------").append("\n\n");
		return st.toString();
	}

	public String toString2() {
		StringBuilder st = new StringBuilder();
		st.append("\n-------------------------------------------------").append("\n");
		st.append("Course:\t" + titleEn + "\tcode:\t" + code + "\tLanguage:\t" + language).append("\n");
		st.append("-------------------------------------------------").append("\n");
		st.append("code:").append("\t\t\t").append(code).append("\n");
		st.append("round:").append("\t\t\t").append(round).append("\n");
		st.append("canceled:").append("\t\t").append(isCancelled).append("\n");
		st.append("Title En:").append("\t\t").append(titleEn).append("\n");
		st.append("Title Sv:").append("\t\t").append(titleSv).append("\n");
		st.append("Credits:").append("\t\t").append(credits).append("\n");
		st.append("Educational level:").append("\t").append(educationLevel).append("\n");
		st.append("Academic level code:").append("\t").append(academicLevelCode).append("\n");
		st.append("Subject code:").append("\t\t").append(subjectCode).append("\n");
		st.append("Subject:").append("\t\t").append(subject).append("\n");
		st.append("Grade scale code:").append("\t").append(gradeScaleCode).append("\n");
		st.append("Department code:").append("\t").append(departmentCode).append("\n");
		st.append("Department:").append("\t\t").append(department).append("\n");
		st.append("Contact Name:").append("\t\t").append(contactName).append("\n");
		st.append("Recruitement text En:").append("\t").append(recruitmentTextEn).append("\n");
		st.append("Recruitement text Sv:").append("\t").append(recruitmentTextSv).append("\n");
		st.append("URL:").append("\t\t\t").append(courseURL).append("\n");
		if (getKeywords() != null) {
			st.append("Keywords: ").append("\n");
			for (Keyword keyword : getKeywords())
				st.append(keyword.toString()).append("\n");
		}
		if (getKeyphrases() != null) {
			st.append("Keyphrases: ").append("\n");
			for (Keyphrase keyphrase : getKeyphrases())
				st.append(keyphrase.toString()).append("\n");
		}
		if (getAcronyms() != null) {
			st.append("Acronyms: ").append("\n");
			for (Acronym acronym : getAcronyms())
				st.append(acronym.toString()).append("\n");
		}
		st.append("-------------------------------------------------").append("\n\n");
		return st.toString();
	}

}
