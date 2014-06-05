package se.kth.ict.docaid.algorithms.acronyms;

/**
 * The class contains information for an extracted acronym.
 * 
 * @author Andreas Kokkalis <a.kokkalis@kth.se>
 * @author Adrian C. Prelipcean <acpr@kth.se>
 *
 */
public class Acronym {
	private String acronym;
	private String[] spelledOut;
	private boolean isSpelledOut;
	private boolean isSpelledOnFirstCheck;
	private String spelledOutString;

	/**
	 * @param acronym The acronym.
	 * @param spelledOut The acronym spelled out.
	 * @param isSpelledOut True if acronym is spelled out.
	 * @param isSpelledOnFirstCheck True if acronym is spelled out in first use.
	 */
	public Acronym(String acronym, String[] spelledOut, boolean isSpelledOut, boolean isSpelledOnFirstCheck) {
		super();
		this.acronym = acronym;
		this.spelledOut = spelledOut;
		this.isSpelledOut = isSpelledOut;
		this.isSpelledOnFirstCheck = isSpelledOnFirstCheck;
	}

	/**
	 * This constructor is used only in the FetchCourses class, when retrieving from the database.
	 * 
	 * @param acronym The acronym
	 * @param spelledOString The acronym spelled out.
	 */
	public Acronym (String acronym, String spelledOString) {
		this.acronym = acronym;
		this.spelledOutString = spelledOString;
	}
	
	/**
	 * @param acronym The acronym
	 */
	public Acronym(String acronym) {
		super();
		this.acronym = acronym;
		this.isSpelledOnFirstCheck = false;
		this.isSpelledOut = false;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String[] getSpelledOut() {
		return spelledOut;
	}

	public void setSpelledOut(String[] spelledOut) {
		this.spelledOut = spelledOut;
	}

	public boolean isSpelledOut() {
		return isSpelledOut;
	}

	public void setIsSpelledOut(boolean isSpelledOut) {
		this.isSpelledOut = isSpelledOut;
	}

	public String spelloutString() {
		String st = "";

		if (spelledOut != null)
			for (String s : spelledOut)
				st = st + " " + s;
		else
			st = "not spelled out";
		return st;
	}

	@Override
	public String toString() {

		String objs = "";

		if (spelledOut != null)
			for (String s : spelledOut)
				objs = objs + " " + s;
		else
			objs = "not spelled out";
		if(spelledOutString == null)
			return "Acronym [acronym=" + acronym + ", spelledOut=" + objs + ", isSpelledOut=" + isSpelledOut + ", isSpelledOnFirstCheck=" + isSpelledOnFirstCheck + "]";
		else
			return "Acronym [acronym=" + acronym + " spelledOut=" + spelledOutString;
	}

	public boolean isSpelledOnFirstCheck() {
		return isSpelledOnFirstCheck;
	}

	public void setSpelledOnFirstCheck(boolean isSpelledOnFirstCheck) {
		this.isSpelledOnFirstCheck = isSpelledOnFirstCheck;
	}

	public String getSpelledOutString() {
		return spelledOutString;
	}

	public void setSpelledOutString(String spelledOutString) {
		this.spelledOutString = spelledOutString;
	}

	public void setSpelledOut(boolean isSpelledOut) {
		this.isSpelledOut = isSpelledOut;
	}

}
