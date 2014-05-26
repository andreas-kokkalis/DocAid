package se.kth.ict.docaid.course;

public class Course {
	private String code;
	private int round;
	
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

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	@Override
	public String toString() {
		StringBuilder st = new StringBuilder();
		st.append("\n-------------------------------------------------").append("\n");
		st.append("Course:\t" + titleEn + "\tcode:\t" + code).append("\n");
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
		st.append("-------------------------------------------------").append("\n\n");
		return st.toString();
	}
	
}
