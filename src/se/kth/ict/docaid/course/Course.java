package se.kth.ict.docaid.course;

public class Course {
	private String code;
	private String round;
	
	private boolean isCancelled; 
	private String titleEn;
	private String titleSv; 
	private int credits; 
	private String educationLevel;	
	private String academicLevelCode;
	private String subjectCode; 
	private String subject; 
	private String gradeScaleCode; 
	private String departmentCode;
	private String department; 
	private String contactName; 
	private String recruitmentTextEn;
	private String recruitmentTextSv; 
	private String courseURL;
	
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

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

}
